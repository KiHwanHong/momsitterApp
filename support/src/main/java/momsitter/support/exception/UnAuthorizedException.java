package momsitter.support.exception;

import momsitter.support.code.ErrorCode;

public class UnAuthorizedException extends BusinessException {

  public UnAuthorizedException() {
    super(ErrorCode.UNAUTHORIZED_ERROR);
  }

  public UnAuthorizedException(ErrorCode errorCode) {
    super(errorCode);
  }

  public UnAuthorizedException(String message) {
    super(message);
  }

  public UnAuthorizedException(String message, Throwable cause) {
    super(message, cause);
  }
}
