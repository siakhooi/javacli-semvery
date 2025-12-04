package sing.app.semvery.processor;

import org.semver4j.Semver;

public class IsGreaterProcessor extends AbstractComparisonProcessor {

    public IsGreaterProcessor() {
        super(Semver::isGreaterThan, "greater", "not greater");
    }

}
