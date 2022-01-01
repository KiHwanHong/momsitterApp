package momsitter.support.dto.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import momsitter.support.validation.regex.Regexp;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class UserMobileNumberRegexDto extends UserBaseDto {

  private String userName;

  @Size(min = 12, max = 13)
  @Pattern(regexp = Regexp.MOBILE_NUMBER)
  @NotEmpty
  private String userMobileNumber;

}
