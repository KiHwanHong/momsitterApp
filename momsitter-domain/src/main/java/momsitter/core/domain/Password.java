package momsitter.core.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import momsitter.support.code.ErrorCode;
import momsitter.support.exception.InvalidPasswordException;

@Embeddable
public class Password {

  private static final String PATTERN = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^\\w\\s]).*$";
  private static final int MIN_LENGTH = 4;
  private static final int MAX_LENGTH = 20;

  @NotNull
  @Column(name = "password")
  private String value;

  protected Password() {}

  public Password(String value) {
    this.value = value;
    validateNull(this.value);
    validateBlank(this.value);
    validateLength(this.value);
    validatePattern(this.value);
  }
  private void validateNull(String value) {
    if(Objects.isNull(value)) {
      throw new InvalidPasswordException(ErrorCode.VALIDATION_PASSWORD_IS_NULL);
    }
  }

  private void validateBlank(String value) {
    if (value.isBlank()) {
      throw new InvalidPasswordException("회원의 비밀번호는 공백으로 이루어질 수 없습니다.");
    }
  }

  private void validateLength(String value) {
    if (value.length() < MIN_LENGTH || MAX_LENGTH < value.length()) {
      throw new InvalidPasswordException(
          String.format("회원의 비밀번호는 %d 글자 이상 %d 글자 이하여야 합니다.", MIN_LENGTH, MAX_LENGTH)
      );
    }
  }

  private void validatePattern(String value) {
    if (value.matches(PATTERN)) {
      return;
    }

    throw new InvalidPasswordException("회원의 비밀번호는 문자, 숫자, 특수문자가 모두 포함되어야 합니다.");
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
    Password password = (Password) o;
    return Objects.equals(value, password.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
