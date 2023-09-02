package sing.app.semvery.processor;

import java.util.List;
import org.semver4j.Semver;
import sing.app.semvery.OperationResult;
import sing.app.semvery.ReturnValue;

public class IsStableProcessor implements OperationProcessorInterface {

  @Override
  public OperationResult process(List<String> versions) {
    OperationResult result = new OperationResult();

    ReturnValue returnValue = ReturnValue.OK;

    for (String value : versions) {
      Semver version = Semver.parse(value);
      if (version == null) {
        result.addEntry(value, "invalid");
        returnValue = ReturnValue.NOT_OK;
      } else {
        if (version.isStable()) {
          result.addEntry(value, "stable");
        } else {
          result.addEntry(value, "not stable");
          returnValue = ReturnValue.NOT_OK;
        }
      }
    }
    result.setReturnValue(returnValue);
    return result;
  }

}
