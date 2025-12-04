package sing.app.semvery.processor;

import org.semver4j.Semver;

public class IsLowerProcessor extends AbstractComparisonProcessor {

    public IsLowerProcessor() {
        super(Semver::isLowerThan, "lower", "not lower");
    }

}
