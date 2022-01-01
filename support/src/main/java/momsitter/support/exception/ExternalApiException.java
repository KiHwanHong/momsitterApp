package momsitter.support.exception;

public class ExternalApiException extends BusinessException {

  public ExternalApiException(String message) {
    super(message);
  }

  public ExternalApiException(String message, Throwable cause) {
    super(message, cause);
  }
}
