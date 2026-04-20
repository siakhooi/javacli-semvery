package sing.app.semvery;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErr;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    @ParameterizedTest
    @ValueSource(strings = {"-s", "--silent"})
    void silent_validOperation_emitsNothingToStdout(String silentFlag) throws Exception {
        Semvery app = new Semvery();
        String out = tapSystemOut(() -> assertEquals(ReturnValue.OK,
                app.run(new String[] {silentFlag, "-o", "isValid", "1.0.0"})));
        assertEquals("", out);
    }

    @ParameterizedTest
    @ValueSource(strings = {"-s", "--silent"})
    void silent_validOperation_emitsNothingToStderr(String silentFlag) throws Exception {
        Semvery app = new Semvery();
        String err = tapSystemErr(() -> assertEquals(ReturnValue.OK,
                app.run(new String[] {silentFlag, "-o", "isValid", "1.0.0"})));
        assertEquals("", err);
    }

    @ParameterizedTest
    @ValueSource(strings = {"-s", "--silent"})
    void silent_help_emitsNothingToStdout(String silentFlag) throws Exception {
        Semvery app = new Semvery();
        String out = tapSystemOut(
                () -> assertEquals(ReturnValue.OK, app.run(new String[] {silentFlag, "-h"})));
        assertEquals("", out);
    }

    @ParameterizedTest
    @ValueSource(strings = {"-s", "--silent"})
    void silent_version_emitsNothingToStdout(String silentFlag) throws Exception {
        Semvery app = new Semvery();
        String out = tapSystemOut(
                () -> assertEquals(ReturnValue.OK, app.run(new String[] {silentFlag, "-v"})));
        assertEquals("", out);
    }

    @ParameterizedTest
    @ValueSource(strings = {"-s", "--silent"})
    void silent_noSubcommand_emitsNothingToStdout(String silentFlag) throws Exception {
        Semvery app = new Semvery();
        String out = tapSystemOut(
                () -> assertEquals(ReturnValue.OK, app.run(new String[] {silentFlag})));
        assertEquals("", out);
    }

    @ParameterizedTest
    @ValueSource(strings = {"-s", "--silent"})
    void silent_missingVersions_wrongParameterAndNoStderr(String silentFlag) throws Exception {
        Semvery app = new Semvery();
        String err = tapSystemErr(() -> assertEquals(ReturnValue.WRONG_PARAMETER,
                app.run(new String[] {silentFlag, "-o", "isValid"})));
        assertEquals("", err);
    }

    @ParameterizedTest
    @ValueSource(strings = {"-s", "--silent"})
    void silent_missingRefVersion_wrongParameterAndNoStderr(String silentFlag) throws Exception {
        Semvery app = new Semvery();
        String err = tapSystemErr(() -> assertEquals(ReturnValue.WRONG_PARAMETER, app.run(
                new String[] {silentFlag, "-o", "isGreater", "1.0.0"})));
        assertEquals("", err);
    }

    @ParameterizedTest
    @ValueSource(strings = {"-s", "--silent"})
    void silent_failedCheck_notOkAndNoStdout(String silentFlag) throws Exception {
        Semvery app = new Semvery();
        String out = tapSystemOut(() -> assertEquals(ReturnValue.NOT_OK,
                app.run(new String[] {silentFlag, "-o", "isValid", "not-a-version"})));
        assertEquals("", out);
    }

    @Test
    void run_exposesParsedFlagsOnParameters() throws Exception {
        Semvery app = new Semvery();
        app.run(new String[] {"--silent", "-O", "json", "-o", "isValid", "2.0.0"});
        assertTrue(app.parameters.silent);
        assertEquals(OutputFormat.json, app.parameters.outputFormat);
        assertEquals(Operation.isValid, app.parameters.operation);
        assertEquals("2.0.0", app.parameters.mainParameters.get(0));
    }
}
