package pt.ul.fc.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CliTest {

  private final ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errBytes = new ByteArrayOutputStream();
  private PrintStream out;
  private PrintStream err;

  @BeforeEach
  void setUp() {
    out = new PrintStream(outBytes, true, StandardCharsets.UTF_8);
    err = new PrintStream(errBytes, true, StandardCharsets.UTF_8);
  }

  private String out() {
    return outBytes.toString(StandardCharsets.UTF_8);
  }

  private String err() {
    return errBytes.toString(StandardCharsets.UTF_8);
  }

  /** Comando falso que regista os argumentos recebidos. */
  private static final class FakeCommand implements Contract {
    private final List<String> received = new ArrayList<>();
    private final int exitCode;

    FakeCommand(int exitCode) {
      this.exitCode = exitCode;
    }

    @Override
    public String name() {
      return "fake";
    }

    @Override
    public String description() {
      return "Comando de teste";
    }

    @Override
    public int run(List<String> args, PrintStream out, PrintStream err) {
      received.addAll(args);
      return exitCode;
    }
  }

  @Test
  void noArgumentsPrintsUsageToErrAndReturnsUsage() {
    int code = new Cli(List.of()).run(List.of(), out, err);

    assertEquals(ExitCode.USAGE, code);
    assertTrue(err().contains("Utilização: harness"));
    assertEquals("", out());
  }

  @ParameterizedTest
  @ValueSource(strings = {"help", "-h", "--help"})
  void helpAliasesPrintUsageToOut(String alias) {
    int code = new Cli(List.of(new FakeCommand(ExitCode.OK))).run(List.of(alias), out, err);

    assertEquals(ExitCode.OK, code);
    assertTrue(out().contains("fake"));
    assertTrue(out().contains("Comando de teste"));
  }

  @Test
  void unknownCommandReturnsUsage() {
    int code = new Cli(List.of()).run(List.of("xpto"), out, err);

    assertEquals(ExitCode.USAGE, code);
    assertTrue(err().contains("Comando desconhecido: xpto"));
  }

  @Test
  void dispatchesToCommandWithRemainingArguments() {
    FakeCommand fake = new FakeCommand(ExitCode.FAILURE);

    int code = new Cli(List.of(fake)).run(List.of("fake", "a", "--b"), out, err);

    assertEquals(ExitCode.FAILURE, code);
    assertEquals(List.of("a", "--b"), fake.received);
  }

  @Test
  void duplicateCommandNamesAreRejected() {
    List<Contract> commands = List.of(new FakeCommand(0), new FakeCommand(0));

    assertThrows(IllegalArgumentException.class, () -> new Cli(commands));
  }
}