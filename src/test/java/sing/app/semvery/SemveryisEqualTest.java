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
class SemveryisEqualTest {

    private Expect expect;

    @ParameterizedTest
    @MethodSource
    void testIsEqual(String scenario, ReturnValue returnValue, String[] arguments)
            throws Exception {
        Semvery app = new Semvery();
        String text = tapSystemOut(() -> assertEquals(returnValue, app.run(arguments)));
        assertDoesNotThrow(() -> expect.scenario(scenario).toMatchSnapshot(text));
    }

    static Stream<Arguments> testIsEqual() {
        ArrayList<Arguments> a = new ArrayList<>();

        final ReturnValue ok = ReturnValue.OK;
        final ReturnValue nok = ReturnValue.NOT_OK;
        final String OPERATOR = "isEqual";
        final String[] defaultArgs = new String[] {"-r", "2.0.0"};
        final String[] anyArgs = new String[] {"-r", "2.0.0", "--any"};

        for (String o : TestData.OPERATIONS) {
            a.addAll(createArguments("all-equal-2.0.0", ok, o, OPERATOR, defaultArgs,
                    TestData.ALL_EQUAL_2_0_0));
            a.addAll(createArguments("all-equal-2.0.0", ok, o, OPERATOR, anyArgs,
                    TestData.ALL_EQUAL_2_0_0));

            a.addAll(createArguments("all-not-equal-2.0.0", nok, o, OPERATOR, defaultArgs,
                    TestData.ALL_NOT_EQUAL_2_0_0));
            a.addAll(createArguments("all-not-equal-2.0.0", nok, o, OPERATOR, anyArgs,
                    TestData.ALL_NOT_EQUAL_2_0_0));

            a.addAll(createArguments("all-not-valid", nok, o, OPERATOR, defaultArgs,
                    TestData.ALL_NOT_VALID));
            a.addAll(createArguments("all-not-valid", nok, o, OPERATOR, anyArgs, TestData.ALL_NOT_VALID));

            a.addAll(createArguments("mixed-equal-not-equal-2.0.0", nok, o, OPERATOR, defaultArgs,
                    TestData.EQUAL_NOT_EQUAL_2_0_0));
            a.addAll(createArguments("mixed-equal-not-equal-2.0.0", ok, o, OPERATOR, anyArgs,
                    TestData.EQUAL_NOT_EQUAL_2_0_0));

            a.addAll(createArguments("mixed-equal-2.0.0-not-valid", nok, o, OPERATOR, defaultArgs,
                    TestData.EQUAL_2_0_0_NOT_VALID));
            a.addAll(createArguments("mixed-equal-2.0.0-not-valid", ok, o, OPERATOR, anyArgs,
                    TestData.EQUAL_2_0_0_NOT_VALID));

            a.addAll(createArguments("mixed-not-equal-2.0.0-not-valid", nok, o, OPERATOR, defaultArgs,
                    TestData.NOT_EQUAL_2_0_0_NOT_VALID));
            a.addAll(createArguments("mixed-not-equal-2.0.0-not-valid", nok, o, OPERATOR, anyArgs,
                    TestData.NOT_EQUAL_2_0_0_NOT_VALID));

            a.addAll(createArguments("mixed-equal-not-equal-2.0.0-not-valid", nok, o, OPERATOR, defaultArgs,
                    TestData.EQUAL_NOT_EQUAL_2_0_0_NOT_VALID));
            a.addAll(createArguments("mixed-equal-not-equal-2.0.0-not-valid", ok, o, OPERATOR, anyArgs,
                    TestData.EQUAL_NOT_EQUAL_2_0_0_NOT_VALID));
        }

        return a.stream();
    }
}
