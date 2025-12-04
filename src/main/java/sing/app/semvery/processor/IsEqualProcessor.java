package sing.app.semvery.processor;

import org.semver4j.Semver;

public class IsEqualProcessor extends AbstractComparisonProcessor {

    public IsEqualProcessor() {
        super(Semver::isEqualTo, "equal", "not equal");
    }

}
