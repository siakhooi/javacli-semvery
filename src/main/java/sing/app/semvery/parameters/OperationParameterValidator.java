package sing.app.semvery.parameters;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class OperationParameterValidator implements IParameterValidator {

    @Override
    public void validate(String name, String value) throws ParameterException {
        if (!"isValid".equals(value)) {
            throw new ParameterException(String.format("Invalid value for operation, accept 'isValid'. (Found %s)", value));
        }
    }
}
