package sing.app.semvery.processor;

import java.util.List;
import sing.app.semvery.OperationResult;

public interface OperationProcessorInterface {

  OperationResult process(List<String> versions);

}
