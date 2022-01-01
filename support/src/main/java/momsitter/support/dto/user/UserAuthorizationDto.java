package momsitter.support.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import momsitter.support.dto.token.Token;

@Getter
@Setter
@NoArgsConstructor
public class UserAuthorizationDto extends UserBaseDto {

  private Token token;

  private Boolean isUpdatedUserPassword;

  @Builder
  public UserAuthorizationDto(String userNumber, Token token, Boolean isUpdatedUserPassword) {
    super(userNumber);
    this.token = token;
    this.isUpdatedUserPassword = isUpdatedUserPassword;
  }
}