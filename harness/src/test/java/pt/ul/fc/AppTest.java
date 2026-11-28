package pt.ul.fc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import pt.ul.fc.cli.ExitCode;

class AppTest {

  @Test
  void helpPrintsUsageAndSucceeds() {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream err = new PrintStream(new ByteArrayOutputStream(), true, StandardCharsets.UTF_8);

    int code = App.run(new String[] {"help"},
        new PrintStream(out, true, StandardCharsets.UTF_8), err);

    assertEquals(ExitCode.OK, code);
    assertTrue(out.toString(StandardCharsets.UTF_8).contains("Utilização: harness"));
  }

  @Test
  void mainWithHelpDoesNotExit() {
    PrintStream original = System.out;
    try {
      System.setOut(new PrintStream(new ByteArrayOutputStream(), true, StandardCharsets.UTF_8));
      App.main(new String[] {"help"});
    } finally {
      System.setOut(original);
    }
  }
}