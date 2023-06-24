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
class SemveryisStableTest {
    final static String[] OPERATION = {"-o", "--operation"};
    final static String IS_STABLE = "isStable";

    private Semvery app;
    private Expect expect;

    @BeforeEach
    void setup() {
        this.app = new Semvery();
    }

    @ParameterizedTest
    @ValueSource(strings = {"-o", "--operation"})
    void testIsStable_no_value(String operation) throws Exception {
        String error = tapSystemErr(() -> {
            assertEquals(2, app.run(new String[] {operation, IS_STABLE}));
        });
        assertDoesNotThrow(() -> expect.toMatchSnapshot(error));
    }

    @ParameterizedTest
    @MethodSource
    void testIsStable_good(String operation, String value) throws Exception {
        String text = tapSystemOut(() -> {
            assertEquals(0, app.run(new String[] {operation, IS_STABLE, value}));
        });
        assertDoesNotThrow(() -> expect.toMatchSnapshot(text));
    }

    private static Stream<Arguments> testIsStable_good() {
        String[] value = {"1.0.0", "243434.2342343.23423423", "1.2.3+sHa.0nSFGKjkjsdf"};
        ArrayList<Arguments> a = new ArrayList<>();

        for (String o : OPERATION)
            for (String v : value)
                a.add(Arguments.of(o, v));

        return a.stream();
    }

    @ParameterizedTest
    @MethodSource
    void testIsStable_invalid(String operation, String value) throws Exception {
        String text = tapSystemOut(() -> {
            assertEquals(1, app.run(new String[] {operation, IS_STABLE, value}));
        });
        assertDoesNotThrow(() -> expect.toMatchSnapshot(text));
    }

    private static Stream<Arguments> testIsStable_invalid() {
        String[] value = {"1.0.A", "adfadfasjdfsd", "23423", "2423.2342342", "24234.234234."};
        ArrayList<Arguments> a = new ArrayList<>();

        for (String o : OPERATION)
            for (String v : value)
                a.add(Arguments.of(o, v));

        return a.stream();
    }
    @ParameterizedTest
    @MethodSource
    void testIsStable_bad(String operation, String value) throws Exception {
        String text = tapSystemOut(() -> {
            assertEquals(1, app.run(new String[] {operation, IS_STABLE, value}));
        });
        assertDoesNotThrow(() -> expect.toMatchSnapshot(text));
    }

    private static Stream<Arguments> testIsStable_bad() {
        String[] value = {"0.1.0", "1.2.3-4", "1.2.3-BETA", "33.5454.54353-pre"};
        ArrayList<Arguments> a = new ArrayList<>();

        for (String o : OPERATION)
            for (String v : value)
                a.add(Arguments.of(o, v));

        return a.stream();
    }
}
