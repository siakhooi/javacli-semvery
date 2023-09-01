package sing.app.semvery.processor;

import java.util.List;
import org.semver4j.Semver;
import sing.app.semvery.Console;

public class IsValidProcessor implements OperationProcessorInterface {

  @Override
  public int process(List<String> versions) {
    int returnValue = 0;
    for (String value : versions) {
      Semver version = Semver.parse(value);
      if (version == null) {
        Console.println("invalid");
        returnValue = 1;
      } else {
        Console.println("valid");
      }
    }
    return returnValue;
  }

}
