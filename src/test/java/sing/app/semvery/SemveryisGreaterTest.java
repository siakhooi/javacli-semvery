package sing.app.semvery;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static sing.app.semvery.TestData.createArguments;
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
class SemveryisGreaterTest {

    private Semvery app;
    private Expect expect;

    @BeforeEach
    void setup() {
        this.app = new Semvery();
    }

    @ParameterizedTest
    @MethodSource
    void testIsGreater(String scenario, ReturnValue returnValue, String[] arguments)
            throws Exception {
        String text = tapSystemOut(() -> assertEquals(returnValue, app.run(arguments)));
        assertDoesNotThrow(() -> expect.scenario(scenario).toMatchSnapshot(text));
    }

    private static Stream<Arguments> testIsGreater() {
        ArrayList<Arguments> a = new ArrayList<>();

        final ReturnValue ok = ReturnValue.OK;
        final ReturnValue nok = ReturnValue.NOT_OK;
        final String OPERATOR = "isGreater";
        final String[] DEFAULT = new String[] {"-r", "2.0.0"};
        final String[] ANY = new String[] {"-r", "2.0.0", "--any"};

        for (String o : TestData.OPERATIONS) {
            a.addAll(createArguments("all-greater-2.0.0", ok, o, OPERATOR, DEFAULT,
                    TestData.ALL_GREATER_2_0_0));
            a.addAll(createArguments("all-greater-2.0.0", ok, o, OPERATOR, ANY,
                    TestData.ALL_GREATER_2_0_0));

            a.addAll(createArguments("all-not-greater-2.0.0", nok, o, OPERATOR, DEFAULT,
                    TestData.ALL_NOT_GREATER_2_0_0));
            a.addAll(createArguments("all-not-greater-2.0.0", nok, o, OPERATOR, ANY,
                    TestData.ALL_NOT_GREATER_2_0_0));

            a.addAll(createArguments("all-not-valid", nok, o, OPERATOR, DEFAULT,
                    TestData.ALL_NOT_VALID));
            a.addAll(createArguments("all-not-valid", nok, o, OPERATOR, ANY, TestData.ALL_NOT_VALID));

            a.addAll(createArguments("mixed-greater-not-greater-2.0.0", nok, o, OPERATOR, DEFAULT,
                    TestData.GREATER_NOT_GREATER_2_0_0));
            a.addAll(createArguments("mixed-greater-not-greater-2.0.0", ok, o, OPERATOR, ANY,
                    TestData.GREATER_NOT_GREATER_2_0_0));

            a.addAll(createArguments("mixed-greater-2.0.0-not-valid", nok, o, OPERATOR, DEFAULT,
                    TestData.GREATER_2_0_0_NOT_VALID));
            a.addAll(createArguments("mixed-greater-2.0.0-not-valid", ok, o, OPERATOR, ANY,
                    TestData.GREATER_2_0_0_NOT_VALID));

            a.addAll(createArguments("mixed-not-greater-2.0.0-not-valid", nok, o, OPERATOR, DEFAULT,
                    TestData.NOT_GREATER_2_0_0_NOT_VALID));
            a.addAll(createArguments("mixed-not-greater-2.0.0-not-valid", nok, o, OPERATOR, ANY,
                    TestData.NOT_GREATER_2_0_0_NOT_VALID));

            a.addAll(createArguments("mixed-greater-not-greater-2.0.0-not-valid", nok, o, OPERATOR, DEFAULT,
                    TestData.GREATER_NOT_GREATER_2_0_0_NOT_VALID));
            a.addAll(createArguments("mixed-greater-not-greater-2.0.0-not-valid", ok, o, OPERATOR, ANY,
                    TestData.GREATER_NOT_GREATER_2_0_0_NOT_VALID));
        }

        return a.stream();
    }
}
