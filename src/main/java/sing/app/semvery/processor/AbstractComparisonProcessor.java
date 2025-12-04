package sing.app.semvery.processor;

import java.util.List;
import java.util.function.BiPredicate;
import org.semver4j.Semver;
import sing.app.semvery.OperationResult;

public abstract class AbstractComparisonProcessor implements OperationProcessorInterface {

    private final BiPredicate<Semver, String> comparisonFunction;
    private final String positiveMessage;
    private final String negativeMessage;

    protected AbstractComparisonProcessor(
            BiPredicate<Semver, String> comparisonFunction,
            String positiveMessage,
            String negativeMessage) {
        this.comparisonFunction = comparisonFunction;
        this.positiveMessage = positiveMessage;
        this.negativeMessage = negativeMessage;
    }

    @Override
    public OperationResult process(List<String> versions, String refVersion) {
        OperationResult result = new OperationResult();

        for (String value : versions) {
            Semver version = Semver.parse(value);
            if (version == null)
                result.addEntry(value, "not valid", false);
            else {
                if (comparisonFunction.test(version, refVersion))
                    result.addEntry(value, positiveMessage, true);
                else
                    result.addEntry(value, negativeMessage, false);
            }
        }
        return result;
    }

}
