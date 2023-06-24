package sing.app.semvery.parameters;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import com.beust.jcommander.ParameterException;

class OperationParameterValidatorTest {
  OperationParameterValidator testObject;

  @BeforeEach
  void setup() {
    testObject = new OperationParameterValidator();
  }


  @ParameterizedTest
  @ValueSource(strings = {"isValid", "isStable"})
  void testValidate_Good(String operator) {

    assertDoesNotThrow(() -> testObject.validate("", operator));
  }

  @ParameterizedTest
  @ValueSource(strings = {"isvalid", "isstable", "isValid123", "isStable123"})
  void testValidate_Bad(String operator) {

    assertThrows(ParameterException.class, () -> testObject.validate("", operator));
  }
}
