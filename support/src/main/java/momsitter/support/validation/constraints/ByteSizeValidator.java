package momsitter.support.validation.constraints;

import java.nio.charset.Charset;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ByteSizeValidator implements
    ConstraintValidator<ByteSize, String> {

  private Charset charset;

  private int min;

  private int max;

  @Override
  public void initialize(
      final ByteSize constraintAnnotation) {
    this.charset = Charset.forName(constraintAnnotation.charset());
    this.min = constraintAnnotation.min();
    this.max = constraintAnnotation.max();
  }

  @Override
  public boolean isValid(final String value, final ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }

    int byteLength = value.getBytes(charset).length;

    return byteLength >= min && byteLength <= this.max;
  }

}
