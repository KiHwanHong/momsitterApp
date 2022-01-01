package momsitter.support.validation.constraints;

import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = ByteSizeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ByteSize {

  String message() default "Invalid byte size";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String charset() default "UTF-8";

  int min() default 0;

  int max() default Integer.MAX_VALUE;

}
