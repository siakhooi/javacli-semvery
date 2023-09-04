package sing.app.semvery;

import sing.app.semvery.processor.IsGreaterProcessor;
import sing.app.semvery.processor.IsStableProcessor;
import sing.app.semvery.processor.IsValidProcessor;
import sing.app.semvery.processor.OperationProcessorInterface;

@SuppressWarnings({"java:S115"})
public enum Operation {
  isValid(new IsValidProcessor()), isStable(new IsStableProcessor()), isGreater(
      new IsGreaterProcessor());

  private OperationProcessorInterface processor;

  Operation(OperationProcessorInterface processor) {
    this.processor = processor;
  }

  public OperationProcessorInterface getProcessor() {
    return this.processor;
  }
}
