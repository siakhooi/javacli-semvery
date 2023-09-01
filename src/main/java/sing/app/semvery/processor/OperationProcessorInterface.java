package sing.app.semvery.processor;

import java.util.List;
import sing.app.semvery.ReturnValue;

public interface OperationProcessorInterface {

  ReturnValue process(List<String> versions);

}
