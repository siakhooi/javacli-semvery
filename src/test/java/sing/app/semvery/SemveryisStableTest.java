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
class SemveryisStableTest {

    private Semvery app;
    private Expect expect;

    @BeforeEach
    void setup() {
        this.app = new Semvery();
    }

    @ParameterizedTest
    @MethodSource
    void testIsStable(String scenario, ReturnValue returnValue, String[] arguments)
            throws Exception {
        String text = tapSystemOut(() -> assertEquals(returnValue, app.run(arguments)));
        assertDoesNotThrow(() -> expect.scenario(scenario).toMatchSnapshot(text));
    }

    private static Stream<Arguments> testIsStable() {
        ArrayList<Arguments> a = new ArrayList<>();

        final ReturnValue ok = ReturnValue.OK;
        final ReturnValue nok = ReturnValue.NOT_OK;
        final String OPERATOR = "isStable";
        final String[] ANY = new String[] {"--any"};
        final String[] EMPTY = new String[] {};

        for (String o : TestData.OPERATIONS) {
            a.addAll(createArguments("all-stable", ok, o, OPERATOR, EMPTY, TestData.ALL_STABLE));
            a.addAll(createArguments("all-stable", ok, o, OPERATOR, ANY, TestData.ALL_STABLE));

            a.addAll(createArguments("all-not-valid", nok, o, OPERATOR, EMPTY,
                    TestData.ALL_INVALID));
            a.addAll(createArguments("all-not-valid", nok, o, OPERATOR, ANY, TestData.ALL_INVALID));

            a.addAll(createArguments("all-not-stable", nok, o, OPERATOR, EMPTY,
                    TestData.ALL_NOT_STABLE));
            a.addAll(createArguments("all-not-stable", nok, o, OPERATOR, ANY,
                    TestData.ALL_NOT_STABLE));


            a.addAll(createArguments("mixed-stable-not-valid", nok, o, OPERATOR, EMPTY,
                    TestData.STABLE_INVALID));
            a.addAll(createArguments("mixed-stable-not-valid", ok, o, OPERATOR, ANY,
                    TestData.STABLE_INVALID));

            a.addAll(createArguments("mixed-stable-not-stable", nok, o, OPERATOR, EMPTY,
                    TestData.STABLE_NOT_STABLE));
            a.addAll(createArguments("mixed-stable-not-stable", ok, o, OPERATOR, ANY,
                    TestData.STABLE_NOT_STABLE));

            a.addAll(createArguments("mixed-stable-not-stable-not-valid", nok, o, OPERATOR, EMPTY,
                    TestData.STABLE_NOT_STABLE_NOT_VALID));
            a.addAll(createArguments("mixed-stable-not-stable-not-valid", ok, o, OPERATOR, ANY,
                    TestData.STABLE_NOT_STABLE_NOT_VALID));
        }

        return a.stream();
    }
}
