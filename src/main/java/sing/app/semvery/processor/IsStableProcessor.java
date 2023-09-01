package sing.app.semvery.processor;

import java.util.List;
import org.semver4j.Semver;
import sing.app.semvery.Console;
import sing.app.semvery.ReturnValue;

public class IsStableProcessor implements OperationProcessorInterface {

  @Override
  public ReturnValue process(List<String> versions) {
    ReturnValue returnValue = ReturnValue.OK;
    for (String value : versions) {
      Semver version = Semver.parse(value);
      if (version == null) {
        Console.println("invalid");
        returnValue = ReturnValue.NOT_OK;
      } else {
        if (version.isStable()) {
          Console.println("stable");
        } else {
          Console.println("not stable");
          returnValue = ReturnValue.NOT_OK;
        }
      }
    }
    return returnValue;
  }

}
