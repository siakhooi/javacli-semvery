package sing.app.semvery;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErr;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;

@ExtendWith({SnapshotExtension.class})
class SemveryisValidTest {
    final static String[] OPERATION = {"-o", "--operation"};
    final static String IS_VALID = "isValid";

    private Semvery app;
    private Expect expect;

    @BeforeEach
    void setup() {
        this.app = new Semvery();
    }

    @ParameterizedTest
    @ValueSource(strings = {"-o", "--operation"})
    void testIsValid_no_value(String operation) throws Exception {
        String error = tapSystemErr(() -> {
            assertEquals(ReturnValue.WRONG_PARAMETER, app.run(new String[] {operation, IS_VALID}));
        });
        assertDoesNotThrow(() -> expect.toMatchSnapshot(error));
    }
    @ParameterizedTest
    @ValueSource(strings = {"-o", "--operation"})
    void testIsValid_no_value_any(String operation) throws Exception {
        String error = tapSystemErr(() -> {
            assertEquals(ReturnValue.WRONG_PARAMETER, app.run(new String[] {operation, IS_VALID, "--any"}));
        });
        assertDoesNotThrow(() -> expect.toMatchSnapshot(error));
    }

    @ParameterizedTest
    @MethodSource
    void testIsValid_good(String operation, String value) throws Exception {
        String text = tapSystemOut(() -> {
            assertEquals(ReturnValue.OK, app.run(new String[] {operation, IS_VALID, value}));
        });
        assertDoesNotThrow(() -> expect.scenario(value).toMatchSnapshot(text));
    }

    private static Stream<Arguments> testIsValid_good() {
        String[] value = {"1.0.0", "243434.2342343.23423423", "33.5454.54353-pre"};
        ArrayList<Arguments> a = new ArrayList<>();

        for (String o : OPERATION)
            for (String v : value)
                a.add(Arguments.of(o, v));

        return a.stream();
    }


    @ParameterizedTest
    @MethodSource
    void testIsValid_bad(String operation, String value) throws Exception {
        String text = tapSystemOut(() -> {
            assertEquals(ReturnValue.NOT_OK, app.run(new String[] {operation, IS_VALID, value}));
        });
        assertDoesNotThrow(() -> expect.scenario(value).toMatchSnapshot(text));
    }

    private static Stream<Arguments> testIsValid_bad() {
        String[] value = {"1.0.A", "adfadfasjdfsd", "23423", "2423.2342342", "24234.234234."};
        ArrayList<Arguments> a = new ArrayList<>();

        for (String o : OPERATION)
            for (String v : value)
                a.add(Arguments.of(o, v));

        return a.stream();
    }

    @ParameterizedTest
    @MethodSource
    void testIsValid_MultiValues(String scenario, String operation, ReturnValue status,
            String[] values) throws Exception {
        String text = tapSystemOut(() -> {
            String arguments[] = new String[values.length + 2];
            arguments[0] = operation;
            arguments[1] = IS_VALID;
            for (int i = 0; i < values.length; i++)
                arguments[i + 2] = values[i];
            assertEquals(status, app.run(arguments));
        });
        assertDoesNotThrow(() -> expect.scenario(scenario).toMatchSnapshot(text));
    }

    private static Stream<Arguments> testIsValid_MultiValues() {
        String[][] goodValues = {{"1.0.0", "3.0.0", "1.0.2"}};
        String[][] invalidValues =
                {{"1.0.A", "adfadfasjdfsd", "23423", "2423.2342342", "24234.234234."}};
        String[][] notStableValues = {{"0.1.0", "1.2.3-4", "1.2.3-BETA", "33.5454.54353-pre"}};
        String[][] mixInvalidValues = {{"1.0.A", "1.1.0", "3.0.0"}, {"1.0.0", "1.1.A", "3.0.0"},
                {"1.0.0", "1.1.0", "3.0.A"}};
        String[][] mixNotStableValues = {{"0.1.0", "1.1.0", "3.0.0"}, {"1.1.0", "0.1.0", "3.0.0"},
                {"1.1.0", "3.0.0", "0.1.0"}};
        String[][] mixInvalidAndNotStableValues = {{"0.1.0", "1.1.A", "3.0.0"},
                {"1.1.A", "0.1.0", "3.0.0"}, {"1.1.0", "3.0.A", "0.1.0"}};

        ArrayList<Arguments> a = new ArrayList<>();

        for (String o : OPERATION) {
            for (String[] v : goodValues)
                a.add(Arguments.of("Good", o, ReturnValue.OK, v));
            for (String[] v : invalidValues)
                a.add(Arguments.of("Invalid", o, ReturnValue.NOT_OK, v));
            for (String[] v : notStableValues)
                a.add(Arguments.of("NotStable", o, ReturnValue.OK, v));
            int i = 0;
            for (String[] v : mixInvalidValues)
                a.add(Arguments.of("MixInvalid:" + (++i), o, ReturnValue.NOT_OK, v));
            for (String[] v : mixNotStableValues)
                a.add(Arguments.of("MixNotStable:" + (++i), o, ReturnValue.OK, v));
            for (String[] v : mixInvalidAndNotStableValues)
                a.add(Arguments.of("MixInvalidAndNotStable:" + (++i), o, ReturnValue.NOT_OK, v));
        }
        return a.stream();
    }

    @ParameterizedTest
    @MethodSource
    void testIsValid_MultiValues_any(String scenario, String operation, ReturnValue status,
            String[] values) throws Exception {
        String text = tapSystemOut(() -> {
            String arguments[] = new String[values.length + 3];
            arguments[0] = operation;
            arguments[1] = IS_VALID;
            arguments[2] = "--any";

            for (int i = 0; i < values.length; i++)
                arguments[i + 3] = values[i];
            assertEquals(status, app.run(arguments));
        });
        assertDoesNotThrow(() -> expect.scenario(scenario).toMatchSnapshot(text));
    }

    private static Stream<Arguments> testIsValid_MultiValues_any() {
        String[][] goodValues = {{"1.0.0", "3.0.0", "1.0.2"}};
        String[][] invalidValues =
                {{"1.0.A", "adfadfasjdfsd", "23423", "2423.2342342", "24234.234234."}};
        String[][] notStableValues = {{"0.1.0", "1.2.3-4", "1.2.3-BETA", "33.5454.54353-pre"}};
        String[][] mixInvalidValues = {{"1.0.A", "1.1.0", "3.0.0"}, {"1.0.0", "1.1.A", "3.0.0"},
                {"1.0.0", "1.1.0", "3.0.A"}};
        String[][] mixNotStableValues = {{"0.1.0", "1.1.0", "3.0.0"}, {"1.1.0", "0.1.0", "3.0.0"},
                {"1.1.0", "3.0.0", "0.1.0"}};
        String[][] mixInvalidAndNotStableValues = {{"0.1.0", "1.1.A", "3.0.0"},
                {"1.1.A", "0.1.0", "3.0.0"}, {"1.1.0", "3.0.A", "0.1.0"}};

        ArrayList<Arguments> a = new ArrayList<>();

        for (String o : OPERATION) {
            for (String[] v : goodValues)
                a.add(Arguments.of("Good", o, ReturnValue.OK, v));
            for (String[] v : invalidValues)
                a.add(Arguments.of("Invalid", o, ReturnValue.NOT_OK, v));
            for (String[] v : notStableValues)
                a.add(Arguments.of("NotStable", o, ReturnValue.OK, v));
            int i = 0;
            for (String[] v : mixInvalidValues)
                a.add(Arguments.of("MixInvalid:" + (++i), o, ReturnValue.OK, v));
            for (String[] v : mixNotStableValues)
                a.add(Arguments.of("MixNotStable:" + (++i), o, ReturnValue.OK, v));
            for (String[] v : mixInvalidAndNotStableValues)
                a.add(Arguments.of("MixInvalidAndNotStable:" + (++i), o, ReturnValue.OK, v));
        }
        return a.stream();
    }
}
