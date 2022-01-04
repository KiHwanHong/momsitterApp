package momsitter.support.exception;

import momsitter.support.code.ErrorCode;

public class InvalidPasswordException extends Exception {

  public InvalidPasswordException(ErrorCode errorCode) {
    super(errorCode);
  }

  public InvalidPasswordException(String message) {
    super(message);
  }
}
