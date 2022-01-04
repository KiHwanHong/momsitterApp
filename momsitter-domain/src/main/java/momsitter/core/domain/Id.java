package momsitter.core.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import momsitter.support.code.ErrorCode;
import momsitter.support.exception.InvalidIdException;

@Embeddable
public class Id {

  private static final int MIN_LENGTH = 3;
  private static final int MAX_LENGTH = 10;

  @NotNull
  @Column(name="id")
  private String value;

  protected Id() {

  }
  public Id(String value) {
    this.value = value;
    validateNull(this.value);
    validateBlank(this.value);
    validateLength(this.value);
  }

  private void validateNull(String value) {
    if(Objects.isNull(value)) {
      throw new InvalidIdException(ErrorCode.VALIDATION_ID_IS_NULL);
    }
  }
  private void validateBlank(String value) {
    if (value.isBlank()) {
      throw new InvalidIdException("회원의 ID는 공백으로 이루어질 수 없습니다.");
    }
  }

  private void validateLength(String value) {
    if (value.length() < MIN_LENGTH || MAX_LENGTH < value.length()) {
      throw new InvalidIdException(
          String.format("회원의 ID %s는 %d 글자 이상 %d 글자 이하에 포함되지 않습니다.", value, MIN_LENGTH, MAX_LENGTH)
      );
    }
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Id id = (Id) o;
    return Objects.equals(value, id.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

}
