package sing.app.semvery;

public class ResultPrinter {

    private ResultPrinter() {}

    public static void output(OperationResult operationResult, OutputFormat format) {
        if (format == OutputFormat.json)
            printJson(operationResult);
        else
            printTable(operationResult);
    }

    private static void printTable(OperationResult operationResult) {
        Console.printResult("Value", "Result");
        Console.printResult("-----", "-----");

        for (var r : operationResult.getResultEntries())
            Console.printResult(r.value(), r.result());
    }

    private static void printJson(OperationResult operationResult) {
        var entries = operationResult.getResultEntries();
        var sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < entries.size(); i++) {
            if (i > 0)
                sb.append(',');
            var e = entries.get(i);
            sb.append("{\"value\":\"").append(jsonEscape(e.value())).append("\",\"result\":\"")
                    .append(jsonEscape(e.result())).append("\"}");
        }
        sb.append(']');
        Console.println(sb.toString());
    }

    private static String jsonEscape(String s) {
        StringBuilder out = new StringBuilder(s.length() + 8);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '\\' -> out.append("\\\\");
                case '"' -> out.append("\\\"");
                case '\n' -> out.append("\\n");
                case '\r' -> out.append("\\r");
                case '\t' -> out.append("\\t");
                default -> {
                    if (c < 0x20)
                        out.append(String.format("\\u%04x", (int) c));
                    else
                        out.append(c);
                }
            }
        }
        return out.toString();
    }
}
