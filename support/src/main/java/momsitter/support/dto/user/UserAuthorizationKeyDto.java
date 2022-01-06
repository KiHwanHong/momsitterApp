package momsitter.support.dto.user;

import javax.validation.constraints.NotEmpty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class UserAuthorizationKeyDto extends UserBaseDto {

  @NotEmpty
  private String membersNumber;

  @NotEmpty
  private String membersPassword;

//  @NotEmpty
  // private String authorizationKeyValue;


}
