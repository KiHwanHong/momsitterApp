package momsitter.support.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.FieldError;

@Getter
@Setter
public class InvalidArguments implements Serializable {

  private static final long serialVersionUID = 1L;

  @JsonInclude(Include.NON_NULL)
  private String userNumber;

  private final String field;
  private final Object value;
  private final String message;

  public InvalidArguments(final FieldError fieldError) {
    this.field = fieldError.getField();
    this.value = fieldError.getRejectedValue();
    this.message = fieldError.getDefaultMessage();
  }

  public InvalidArguments(String userNumber, final FieldError fieldError) {
    this.userNumber = userNumber;
    this.field = fieldError.getField();
    this.value = fieldError.getRejectedValue();
    this.message = fieldError.getDefaultMessage();
  }
}
