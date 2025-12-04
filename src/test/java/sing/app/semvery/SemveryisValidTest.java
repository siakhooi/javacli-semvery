package sing.app.semvery;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static sing.app.semvery.TestData.createArguments;
import java.util.ArrayList;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;

@ExtendWith({SnapshotExtension.class})
class SemveryisValidTest {

    private Expect expect;

    @ParameterizedTest
    @MethodSource
    void testIsValid(String scenario, ReturnValue returnValue, String[] arguments)
            throws Exception {
        Semvery app = new Semvery();
        String text = tapSystemOut(() -> assertEquals(returnValue, app.run(arguments)));
        assertDoesNotThrow(() -> expect.scenario(scenario).toMatchSnapshot(text));
    }

    static Stream<Arguments> testIsValid() {
        ArrayList<Arguments> a = new ArrayList<>();

        final ReturnValue ok = ReturnValue.OK;
        final ReturnValue nok = ReturnValue.NOT_OK;
        final String OPERATOR = "isValid";
        final String[] anyArgs = new String[] {"--any"};
        final String[] emptyArgs = new String[] {};

        for (String o : TestData.OPERATIONS) {
            a.addAll(createArguments("all-valid", ok, o, OPERATOR, emptyArgs, TestData.ALL_VALID));
            a.addAll(createArguments("all-valid", ok, o, OPERATOR, anyArgs, TestData.ALL_VALID));

            a.addAll(createArguments("all-not-valid", nok, o, OPERATOR, emptyArgs,
                    TestData.ALL_NOT_VALID));
            a.addAll(createArguments("all-not-valid", nok, o, OPERATOR, anyArgs, TestData.ALL_NOT_VALID));

            a.addAll(createArguments("mixed-valid-not-valid", nok, o, OPERATOR, emptyArgs,
                    TestData.VALID_NOT_VALID));
            a.addAll(createArguments("mixed-valid-not-valid", ok, o, OPERATOR, anyArgs,
                    TestData.VALID_NOT_VALID));
        }

        return a.stream();
    }
}
