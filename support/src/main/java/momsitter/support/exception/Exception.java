package momsitter.support.exception;

import momsitter.support.code.ErrorCode;

public class Exception extends RuntimeException{

  private ErrorCode errorCode;

  public Exception(ErrorCode errorCode) {
    super(errorCode.name());
    this.errorCode = errorCode;
  }

  public Exception(String message) {
    super(message);
  }

}
