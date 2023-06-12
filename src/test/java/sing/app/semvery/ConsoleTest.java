package sing.app.semvery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConsoleTest {
  PrintStream stdout;
  ByteArrayOutputStream baos;

  @BeforeEach
  void setup() {
    stdout = System.out;
    baos = new ByteArrayOutputStream();
    System.setOut(new PrintStream(baos));
  }

  @AfterEach
  void teardown() throws IOException {
    System.setOut(stdout);
    baos.close();
  }

  @Test
  void testPrintf() {
    Console.printf("%s", "ABC");
    assertEquals("ABC", baos.toString());
  }

  @Test
  void testPrintln() {
    Console.println("ABC");
    assertEquals("ABC" + System.lineSeparator(), baos.toString());
  }
}
