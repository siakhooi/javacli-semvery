package sing.app.semvery;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;

@ExtendWith({SnapshotExtension.class})
class SemveryTest {
    private Expect expect;

    @Test
    void callMainWithNoArguments() throws Exception {
        Semvery app = new Semvery();
        String text = tapSystemOut(() -> assertEquals(ReturnValue.OK, app.run(new String[] {})));
        assertDoesNotThrow(() -> expect.toMatchSnapshot(text));
    }

    @Test
    void callMainWithArguments() throws Exception {
        Semvery app = new Semvery();
        String text = tapSystemOut(
                () -> assertEquals(ReturnValue.OK, app.run(new String[] {"ABC", "CDE"})));
        assertDoesNotThrow(() -> expect.toMatchSnapshot(text));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-h", "--help"})
    void callMainWithHelp(String argument1) throws Exception {
        assertCommandWithSnapshot(argument1);
    }

    @ParameterizedTest
    @ValueSource(strings = {"-v", "--version"})
    void callMainWithVersion(String argument1) throws Exception {
        assertCommandWithSnapshot(argument1);
    }

    private void assertCommandWithSnapshot(String argument) throws Exception {
        Semvery app = new Semvery();
        String text =
                tapSystemOut(() -> assertEquals(ReturnValue.OK, app.run(new String[] {argument})));
        assertDoesNotThrow(() -> expect.toMatchSnapshot(text));
    }
}
