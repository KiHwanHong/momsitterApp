package momsitter.support.exception;

import momsitter.support.code.ErrorCode;

public class InvalidIdException extends Exception {

  public InvalidIdException(ErrorCode errorCode) {
    super(errorCode);
  }

  public InvalidIdException(String message) {
    super(message);
  }
}
