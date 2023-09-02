package sing.app.semvery;

public class ResultPrinter {
  public static void output(OperationResult operationResult) {
    Console.printResult("Value", "Result");
    Console.printResult("-----", "-----");

    for (var r : operationResult.getResultEntries())
      Console.printResult(r.value(), r.result());
  }
}
