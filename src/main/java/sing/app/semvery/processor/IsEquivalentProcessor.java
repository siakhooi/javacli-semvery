package sing.app.semvery.processor;

import org.semver4j.Semver;

public class IsEquivalentProcessor extends AbstractComparisonProcessor {

    public IsEquivalentProcessor() {
        super(Semver::isEquivalentTo, "equivalent", "not equivalent");
    }

}
