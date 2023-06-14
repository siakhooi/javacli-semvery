package sing.app.semvery;

import static com.github.stefanbirkner.systemlambda.SystemLambda.catchSystemExit;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErr;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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

  @ParameterizedTest
  @ValueSource(ints = {0, 1, 2})
  void testExit(int value) throws Exception {
    int statusCode = catchSystemExit(() -> Console.exit(value));
    assertEquals(value, statusCode);
  }

  @Test
  void testError() throws Exception {
    String text = tapSystemErr(() -> Console.error("ABC"));
    assertEquals("ABC" + System.lineSeparator(), text);
  }
}
