package sing.app.semvery;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErr;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class ConsoleTest {
  @Test
  void testPrintf() throws Exception {
    String text = tapSystemOut(() -> Console.printf("%s", "ABC"));
    assertEquals("ABC", text);
  }

  @Test
  void testPrintln() throws Exception {
    String text = tapSystemOut(() -> Console.println("ABC"));
    assertEquals("ABC" + System.lineSeparator(), text);
  }

  @Test
  void testError() throws Exception {
    String text = tapSystemErr(() -> Console.error("ABC"));
    assertEquals("ABC" + System.lineSeparator(), text);
  }
}
