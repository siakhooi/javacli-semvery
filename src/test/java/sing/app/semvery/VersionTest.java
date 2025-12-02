package sing.app.semvery;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;

@ExtendWith({SnapshotExtension.class})
class VersionTest {
    private static final String CURRENT_VERSION = "1.0.4";

    private Expect expect;

  @Test
  void testGetAPPLICATION_NAME() {
    assertEquals("semvery", Version.getAPPLICATION_NAME());
  }

  @Test
  void testGetAPPLICATION_VERSION() {
    assertEquals(CURRENT_VERSION, Version.getAPPLICATION_VERSION());
  }

  @Test
  void testPrintApplicationVersion() throws Exception {
    String text = tapSystemOut(Version::printApplicationVersion);
    assertDoesNotThrow(() -> expect.toMatchSnapshot(text));
  }

  @Test
  void testPrintVersion() throws Exception {
    String text = tapSystemOut(Version::printVersion);
    assertDoesNotThrow(() -> expect.toMatchSnapshot(text));
  }
}
