package sing.app.semvery;

import sing.app.semvery.processor.IsStableProcessor;
import sing.app.semvery.processor.IsValidProcessor;
import sing.app.semvery.processor.OperationProcessorInterface;

public enum Operation {
  isValid(new IsValidProcessor()), isStable(new IsStableProcessor());

  private OperationProcessorInterface processor;

  Operation(OperationProcessorInterface processor) {
    this.processor = processor;
  }

  public OperationProcessorInterface getProcessor() {
    return this.processor;
  }
}
