package sing.app.semvery.processor;

import java.util.List;
import org.semver4j.Semver;
import sing.app.semvery.Console;
import sing.app.semvery.OperationResult;
import sing.app.semvery.ReturnValue;

public class IsValidProcessor implements OperationProcessorInterface {

  @Override
  public OperationResult process(List<String> versions) {
    ReturnValue returnValue = ReturnValue.OK;
    Console.printResult("Value", "Result");
    Console.printResult("-----", "-----");

    for (String value : versions) {
      Semver version = Semver.parse(value);
      if (version == null) {
        Console.printResult(value, "invalid");
        returnValue = ReturnValue.NOT_OK;
      } else {
        Console.printResult(value, "valid");
      }
    }
    OperationResult result = new OperationResult();
    result.setReturnValue(returnValue);
    return result;
  }

}
