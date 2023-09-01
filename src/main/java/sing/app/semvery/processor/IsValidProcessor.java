package sing.app.semvery.processor;

import java.util.List;
import org.semver4j.Semver;
import sing.app.semvery.Console;
import sing.app.semvery.ReturnValue;

public class IsValidProcessor implements OperationProcessorInterface {

  @Override
  public ReturnValue process(List<String> versions) {
    ReturnValue returnValue = ReturnValue.OK;
    for (String value : versions) {
      Semver version = Semver.parse(value);
      if (version == null) {
        Console.println("invalid");
        returnValue = ReturnValue.NOT_OK;
      } else {
        Console.println("valid");
      }
    }
    return returnValue;
  }

}
