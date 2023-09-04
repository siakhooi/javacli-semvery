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
class SemveryisValidTest {

    private Semvery app;
    private Expect expect;

    @BeforeEach
    void setup() {
        this.app = new Semvery();
    }

    @ParameterizedTest
    @MethodSource
    void testIsValid(String scenario, ReturnValue returnValue, String[] arguments)
            throws Exception {
        String text = tapSystemOut(() -> assertEquals(returnValue, app.run(arguments)));
        assertDoesNotThrow(() -> expect.scenario(scenario).toMatchSnapshot(text));
    }

    private static Stream<Arguments> testIsValid() {
        ArrayList<Arguments> a = new ArrayList<>();

        final ReturnValue ok = ReturnValue.OK;
        final ReturnValue nok = ReturnValue.NOT_OK;
        final String OPERATOR = "isValid";
        final String[] ANY = new String[] {"--any"};
        final String[] EMPTY = new String[] {};

        for (String o : TestData.OPERATIONS) {
            a.addAll(createArguments("all-valid", ok, o, OPERATOR, EMPTY, TestData.ALL_VALID));
            a.addAll(createArguments("all-valid", ok, o, OPERATOR, ANY, TestData.ALL_VALID));

            a.addAll(createArguments("all-not-valid", nok, o, OPERATOR, EMPTY,
                    TestData.ALL_INVALID));
            a.addAll(createArguments("all-not-valid", nok, o, OPERATOR, ANY, TestData.ALL_INVALID));

            a.addAll(createArguments("mixed-valid-not-valid", nok, o, OPERATOR, EMPTY,
                    TestData.VALID_INVALID));
            a.addAll(createArguments("mixed-valid-not-valid", ok, o, OPERATOR, ANY,
                    TestData.VALID_INVALID));
        }

        return a.stream();
    }
}
