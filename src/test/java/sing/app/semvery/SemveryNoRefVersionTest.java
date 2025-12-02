package sing.app.semvery;


import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErr;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;

@ExtendWith({SnapshotExtension.class})
class SemveryNoRefVersionTest {
  private Semvery app;
  private Expect expect;

  @BeforeEach
  void setup() {
    this.app = new Semvery();
  }

  @ParameterizedTest
  @MethodSource
  void test_no_refVersion(ReturnValue returnValue, String[] arguments) throws Exception {
    String error = tapSystemErr(() -> assertEquals(returnValue, app.run(arguments)));
    assertDoesNotThrow(() -> expect.toMatchSnapshot(error));
  }

  static Stream<Arguments> test_no_refVersion() {
    ArrayList<Arguments> a = new ArrayList<>();
    final String DUMMY_VERSION = "1.0.0";

    for (String o : TestData.OPERATIONS) {
      for (String r : TestData.OPERATORS_NEED_REFVERSION) {
        a.add(Arguments.of(ReturnValue.WRONG_PARAMETER, new String[] {o, r, DUMMY_VERSION}));
        a.add(Arguments.of(ReturnValue.WRONG_PARAMETER, new String[] {o, r, DUMMY_VERSION, "--any"}));
      }
    }
    return a.stream();
  }
}
