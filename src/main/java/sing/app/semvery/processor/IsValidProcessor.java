package sing.app.semvery.processor;

import java.util.List;
import org.semver4j.Semver;
import sing.app.semvery.OperationResult;

public class IsValidProcessor implements OperationProcessorInterface {

  @Override
  public OperationResult process(List<String> versions, String refVersion) {
    OperationResult result = new OperationResult();

    for (String value : versions) {
      Semver version = Semver.parse(value);
      if (version == null)
        result.addEntry(value, "invalid", false);
      else
        result.addEntry(value, "valid", true);
    }
    return result;
  }

}
