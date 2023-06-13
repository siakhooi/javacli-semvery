package sing.app.semvery;

import static com.github.stefanbirkner.systemlambda.SystemLambda.catchSystemExit;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class SemveryisValidTest {
    final static String[] OPERATION = {"-o", "--operation"};
    final static String IS_VALID = "isValid";

    @ParameterizedTest
    @ValueSource(strings = {"-o", "--operation"})
    void testIsValid_no_value(String operation) throws Exception {
        int statusCode = catchSystemExit(() -> {
            Semvery.main(new String[] {operation, IS_VALID});
        });
        assertEquals(2, statusCode);
    }

    @ParameterizedTest
    @MethodSource
    void testIsValid_good(String operation, String value) throws Exception {
        int statusCode = catchSystemExit(() -> {
            String text = tapSystemOut(() -> {
                Semvery.main(new String[] {operation, IS_VALID, value});
            });
            assertEquals("valid", text);
        });
        assertEquals(0, statusCode);
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
        int statusCode = catchSystemExit(() -> {
            String text = tapSystemOut(() -> {
                Semvery.main(new String[] {operation, IS_VALID, value});
            });
            assertEquals("invalid", text);
        });
        assertEquals(1, statusCode);
    }

    private static Stream<Arguments> testIsValid_bad() {
        String[] value = {"1.0.A", "adfadfasjdfsd", "23423", "2423.2342342", "24234.234234."};
        ArrayList<Arguments> a = new ArrayList<>();

        for (String o : OPERATION)
            for (String v : value)
                a.add(Arguments.of(o, v));

        return a.stream();
    }
}
