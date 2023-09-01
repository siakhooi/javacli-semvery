package sing.app.semvery;

public enum ReturnValue {
  OK(0), NOT_OK(1), WRONG_PARAMETER(2);

  int code;

  ReturnValue(int code) {
    this.code = code;
  }

  int getCode() {
    return this.code;
  }

}
