package sing.app.semvery.parameters;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.beust.jcommander.ParameterException;

class OperationParameterValidatorTest {
  OperationParameterValidator testObject;

  @BeforeEach
  void setup() {
    testObject = new OperationParameterValidator();
  }


  @Test
  void testValidate_Good() {

    assertDoesNotThrow(() -> testObject.validate("", "isValid"));
  }

  @Test
  void testValidate_Bad() {

    assertThrows(ParameterException.class, () -> testObject.validate("", "isValid123"));
  }
}
