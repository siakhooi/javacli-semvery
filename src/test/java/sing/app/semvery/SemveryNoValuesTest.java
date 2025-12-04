package sing.app.semvery;


import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErr;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;

@ExtendWith({SnapshotExtension.class})
class SemveryNoValuesTest {
  private Expect expect;

  @ParameterizedTest
  @MethodSource
  void testNoValue(ReturnValue returnValue, String[] arguments) throws Exception {
    Semvery app = new Semvery();

    String error = tapSystemErr(() -> assertEquals(returnValue, app.run(arguments)));
    assertDoesNotThrow(() -> expect.toMatchSnapshot(error));
  }

  static Stream<Arguments> testNoValue() {
    ArrayList<Arguments> a = new ArrayList<>();

    for (String o : TestData.OPERATIONS) {
      for (String r : TestData.ALL_OPERATORS) {
        a.add(Arguments.of(ReturnValue.WRONG_PARAMETER, new String[] {o, r}));
        a.add(Arguments.of(ReturnValue.WRONG_PARAMETER, new String[] {o, r, "--any"}));
      }
    }
    return a.stream();
  }
}
