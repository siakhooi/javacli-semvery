package sing.app.semvery.processor;

import java.util.List;
import org.semver4j.Semver;
import sing.app.semvery.OperationResult;

public class IsLowerProcessor implements OperationProcessorInterface {

    @Override
    public OperationResult process(List<String> versions, String refVersion) {
        OperationResult result = new OperationResult();

        for (String value : versions) {
            Semver version = Semver.parse(value);
            if (version == null)
                result.addEntry(value, "not valid", false);
            else {
                if (version.isLowerThan(refVersion))
                    result.addEntry(value, "lower", true);
                else
                    result.addEntry(value, "not lower", false);
            }
        }
        return result;
    }

}
