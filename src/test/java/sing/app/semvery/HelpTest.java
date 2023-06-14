package sing.app.semvery;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;


@ExtendWith({SnapshotExtension.class})
public class HelpTest {
  private Expect expect;

  @Test
  void testPrintHelp() throws Exception {
    Parameters parameters = new Parameters();
    parameters.process(new String[] {"-h"});
    String text = tapSystemOut(() -> Help.printHelp(parameters));
    assertDoesNotThrow(() -> expect.toMatchSnapshot(text));
  }
}
