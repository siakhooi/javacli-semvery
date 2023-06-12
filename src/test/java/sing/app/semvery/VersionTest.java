package sing.app.semvery;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;

@ExtendWith({SnapshotExtension.class})
class VersionTest {
  PrintStream stdout;
  ByteArrayOutputStream baos;
  private Expect expect;

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
  void testGetAPPLICATION_NAME() {
    assertEquals("semvery", Version.getAPPLICATION_NAME());
  }

  @Test
  void testGetAPPLICATION_VERSION() {
    assertEquals("0.0.2", Version.getAPPLICATION_VERSION());
  }

  @Test
  void testPrintApplicationVersion() {
    Version.printApplicationVersion();
    assertDoesNotThrow(() -> expect.toMatchSnapshot(baos.toString()));
  }

  @Test
  void testPrintHelp() {
    Parameters parameters = new Parameters();
    parameters.process(new String[] {"-h"});
    Version.printHelp(parameters);
    assertDoesNotThrow(() -> expect.toMatchSnapshot(baos.toString()));

  }

  @Test
  void testPrintVersion() {
    Version.printVersion();
    assertDoesNotThrow(() -> expect.toMatchSnapshot(baos.toString()));
  }

}
