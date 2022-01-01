package momsitter.support.validation;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ValidatorFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import momsitter.support.context.ApplicationContextProvider;
import momsitter.support.exception.InvalidValueException;

@Component
public class ValidationHelper {

  private final ValidatorFactory validatorFactory;

  public ValidationHelper() {
    this(
        ApplicationContextProvider.getApplicationContext().getBean(ValidatorFactory.class)
    );
  }

  @Autowired
  public ValidationHelper(final ValidatorFactory validatorFactory) {
    this.validatorFactory = validatorFactory;
  }

  public <T> Set<ConstraintViolation<T>> validate(final T data) {
    return validatorFactory.getValidator().validate(data);
  }

  public <T> void throwIfNotValid(final T data, final String message) {
    if (!validate(data).isEmpty()) {
      throw new InvalidValueException(message);
    }
  }

  public void throwIfTrue(final boolean value, final String message) {
    if (value) {
      throw new InvalidValueException(message);
    }
  }

  public void throwIfFalse(final boolean value, final String message) {
    if (!value) {
      throw new InvalidValueException(message);
    }
  }

  public <L, R> void throwIfEquals(final L left, final R right, final String message) {
    if (left.equals(right)) {
      throw new InvalidValueException(message);
    }
  }

  public <L, R> void throwIfNotEquals(final L left, final R right, final String message) {
    if (!left.equals(right)) {
      throw new InvalidValueException(message);
    }
  }

  public void throwIfGreaterThan(final Long left, final Long right, final String message) {
    if (left > right) {
      throw new InvalidValueException(message);
    }
  }

  public void throwIfGreaterThan(final Integer left, final Integer right, final String message) {
    if (left > right) {
      throw new InvalidValueException(message);
    }
  }

  public void throwIfGreaterThanOrEqual(final Integer left, final Integer right,
      final String message) {
    if (left >= right) {
      throw new InvalidValueException(message);
    }
  }

  public void throwIfLessThan(final Integer left, final Integer right, final String message) {
    if (left < right) {
      throw new InvalidValueException(message);
    }
  }

  public void throwIfBeforeOrEqual(final LocalDateTime left, final LocalDateTime right,
      final String message) {
    if (left.isEqual(right)) {
      throw new InvalidValueException(message);
    }
    throwIfBeforeThan(left, right, message);
  }

  public void throwIfAfterOrEqual(final LocalDateTime left, final LocalDateTime right,
      final String message) {
    if (left.isEqual(right)) {
      throw new InvalidValueException(message);
    }
    throwIfAfterThan(left, right, message);
  }

  public void throwIfBeforeThan(final LocalDateTime left, final LocalDateTime right,
      final String message) {
    if (left.isBefore(right)) {
      throw new InvalidValueException(message);
    }
  }

  public void throwIfAfterThan(final LocalDateTime left, final LocalDateTime right,
      final String message) {
    if (left.isAfter(right)) {
      throw new InvalidValueException(message);
    }
  }

  public void throwIfNull(final Object value, final String message) {
    if (Objects.isNull(value)) {
      throw new InvalidValueException(message);
    }
  }

  public void throwIfEmpty(final String value, final String message) {
    if (StringUtils.isEmpty(value)) {
      throw new InvalidValueException(message);
    }
  }

  public void throwIfEqualsGroup(final Object[][] groups, final String message) {
    throwIfEqualsOrNotEqualsGroups(groups, message, true);
  }

  public void throwIfNotEqualsGroup(final Object[][] groups, final String message) {
    throwIfEqualsOrNotEqualsGroups(groups, message, false);
  }

  private void throwIfEqualsOrNotEqualsGroups(final Object[][] groups, final String message,
      boolean equals) {
    for (Object[] pair : groups) {
      if (pair.length != 2) {
        continue;
      }

      if (equals) {
        throwIfEquals(pair[0], pair[1], message);
      } else {
        throwIfNotEquals(pair[0], pair[1], message);
      }
    }
  }

}
