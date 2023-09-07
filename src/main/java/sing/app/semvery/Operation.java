package sing.app.semvery;

import sing.app.semvery.processor.IsEqualProcessor;
import sing.app.semvery.processor.IsEquivalentProcessor;
import sing.app.semvery.processor.IsGreaterProcessor;
import sing.app.semvery.processor.IsLowerProcessor;
import sing.app.semvery.processor.IsStableProcessor;
import sing.app.semvery.processor.IsValidProcessor;
import sing.app.semvery.processor.OperationProcessorInterface;

@SuppressWarnings({"java:S115"})
public enum Operation {
    isValid(new IsValidProcessor(), false), isStable(new IsStableProcessor(), false), 
    isGreater(new IsGreaterProcessor(), true), isLower(new IsLowerProcessor(), true), 
    isEqual(new IsEqualProcessor(), true), isEquivalent(new IsEquivalentProcessor(), true);

    private OperationProcessorInterface processor;

    private boolean requireRefVersion;

    Operation(OperationProcessorInterface processor, boolean requireRefVersion) {
        this.processor = processor;
        this.requireRefVersion = requireRefVersion;
    }

    public OperationProcessorInterface getProcessor() {
        return this.processor;
    }

    public boolean requireRefVersion() {
        return this.requireRefVersion;
    }
}
