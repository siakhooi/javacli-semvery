package sing.app.semvery;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

class ResultPrinterTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String NL = System.lineSeparator();

    private static String tableLine(String value, String result, int valueWidth) {
        return String.format("%-" + valueWidth + "s %-20s", value, result);
    }

    private static String tableBlock(String... lines) {
        return String.join(NL, lines) + NL;
    }

    @Test
    void table_emptyEntries_printsHeaderAndSeparatorOnly() throws Exception {
        OperationResult result = new OperationResult();
        int w = 5;
        String expected = tableBlock(tableLine("Value", "Result", w), tableLine("-".repeat(w), "-".repeat(20), w));
        String out = tapSystemOut(() -> ResultPrinter.output(result, OutputFormat.table));
        assertEquals(expected, out);
    }

    @Test
    void table_singleRow_usesMinValueColumnWidth() throws Exception {
        OperationResult result = new OperationResult();
        result.addEntry("1.0.0", "valid", true);
        int w = 5;
        String expected = tableBlock(tableLine("Value", "Result", w), tableLine("-".repeat(w), "-".repeat(20), w),
                tableLine("1.0.0", "valid", w));
        String out = tapSystemOut(() -> ResultPrinter.output(result, OutputFormat.table));
        assertEquals(expected, out);
    }

    @Test
    void table_widensValueColumnForLongerValues() throws Exception {
        OperationResult result = new OperationResult();
        result.addEntry("1.0.0", "ok", true);
        result.addEntry("long-value-name", "ok", true);
        int w = "long-value-name".length();
        String expected = tableBlock(tableLine("Value", "Result", w), tableLine("-".repeat(w), "-".repeat(20), w),
                tableLine("1.0.0", "ok", w), tableLine("long-value-name", "ok", w));
        String out = tapSystemOut(() -> ResultPrinter.output(result, OutputFormat.table));
        assertEquals(expected, out);
    }

    @Test
    void table_valueColumnWidthClampedAtTwenty() throws Exception {
        String longValue = "v".repeat(25);
        OperationResult result = new OperationResult();
        result.addEntry(longValue, "x", true);
        int w = 20;
        String expected = tableBlock(tableLine("Value", "Result", w), tableLine("-".repeat(w), "-".repeat(20), w),
                tableLine(longValue, "x", w));
        String out = tapSystemOut(() -> ResultPrinter.output(result, OutputFormat.table));
        assertEquals(expected, out);
    }

    @Test
    void json_empty_printsEmptyArray() throws Exception {
        OperationResult result = new OperationResult();
        String out = tapSystemOut(() -> ResultPrinter.output(result, OutputFormat.json));
        assertEquals("[]" + NL, out);
    }

    @Test
    void json_roundTripMatchesEntries() throws Exception {
        OperationResult result = new OperationResult();
        result.addEntry("1.0.0", "valid", true);
        result.addEntry("2.0.0", "not valid", false);
        String out = tapSystemOut(() -> ResultPrinter.output(result, OutputFormat.json)).trim();
        JsonNode tree = MAPPER.readTree(out);
        assertEquals(2, tree.size());
        assertEquals("1.0.0", tree.get(0).get("value").asText());
        assertEquals("valid", tree.get(0).get("result").asText());
        assertEquals("2.0.0", tree.get(1).get("value").asText());
        assertEquals("not valid", tree.get(1).get("result").asText());
    }

    @Test
    void json_escapesControlCharactersAndQuotes() throws Exception {
        OperationResult result = new OperationResult();
        result.addEntry("a\"b\nc", "x\\y", true);
        String out = tapSystemOut(() -> ResultPrinter.output(result, OutputFormat.json)).trim();
        JsonNode tree = MAPPER.readTree(out);
        assertEquals(1, tree.size());
        assertEquals("a\"b\nc", tree.get(0).get("value").asText());
        assertEquals("x\\y", tree.get(0).get("result").asText());
    }
}
