package momsitter.support.exception;

import momsitter.support.code.ErrorCode;

public class InvalidStateException extends BusinessException {

  public InvalidStateException(ErrorCode errorCode) {
    super(errorCode);
  }

  public InvalidStateException(String message) {
    super(message);
  }

  public InvalidStateException(String message, Throwable cause) {
    super(message, cause);
  }
}
