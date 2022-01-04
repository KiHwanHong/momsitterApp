package momsitter.support.exception;

import momsitter.support.code.ErrorCode;

public class AuthenticationException extends Exception{

  ErrorCode errorCode;

  public AuthenticationException(String message) {
    super(message);
  }
}
