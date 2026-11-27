package pt.ul.fc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class AppTest {

  @Test
  void greetingReturnsHelloWorld() {
    assertEquals("Hello World!", App.greeting());
  }

  @Test
  void mainPrintsGreeting() {
    PrintStream original = System.out;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      System.setOut(new PrintStream(out, true, StandardCharsets.UTF_8));
      App.main(new String[0]);
    } finally {
      System.setOut(original);
    }
    assertEquals("Hello World!" + System.lineSeparator(),
        out.toString(StandardCharsets.UTF_8));
  }
}