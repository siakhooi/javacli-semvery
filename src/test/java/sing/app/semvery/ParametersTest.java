package sing.app.semvery;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;

@ExtendWith({SnapshotExtension.class})
class ParametersTest {

  private Expect expect;

  @Test
  void testPrintUsage() throws Exception {
    Parameters p = new Parameters();
    p.process(new String[] {"-h"});
    String text = tapSystemOut(() -> p.printUsage());
    assertDoesNotThrow(() -> expect.toMatchSnapshot(text));
  }

  @Test
  void testProcess1() {
    Parameters p = new Parameters();
    p.process(new String[] {"-h"});
    assertEquals(true, p.help);
    assertEquals(false, p.version);
    assertEquals(0, p.mainParameters.size());
  }

  @Test
  void testProcess2() {
    Parameters p = new Parameters();
    p.process(new String[] {"-v"});
    assertEquals(false, p.help);
    assertEquals(true, p.version);
    assertEquals(0, p.mainParameters.size());
  }

  @Test
  void testProcess3() {
    Parameters p = new Parameters();
    p.process(new String[] {"abc", "cde"});
    assertEquals(false, p.help);
    assertEquals(false, p.version);
    assertEquals(2, p.mainParameters.size());
  }
}
