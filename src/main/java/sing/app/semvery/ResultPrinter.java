package sing.app.semvery;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ResultPrinter {

    private static final ObjectMapper JSON = new ObjectMapper();

    private ResultPrinter() {}

    public static void output(OperationResult operationResult, OutputFormat format) {
        if (format == OutputFormat.json)
            printJson(operationResult);
        else
            printTable(operationResult);
    }

    private static void printTable(OperationResult operationResult) {
        printResult("Value", "Result");
        printResult("-----", "-----");

        for (var r : operationResult.getResultEntries())
            printResult(r.value(), r.result());
    }
    private static void printResult(String value, String result) {
        Console.printf("%-20s %-20s%n", value, result);
    }

    private static void printJson(OperationResult operationResult) {
        try {
            Console.println(JSON.writeValueAsString(operationResult.getResultEntries()));
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize result to JSON", e);
        }
    }
}
