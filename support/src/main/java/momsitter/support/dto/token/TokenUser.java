package momsitter.support.dto.token;

import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenUser extends Token {

  private String userNumber;

  public TokenUser(String userNumber) {
    this.userNumber = userNumber;
  }

  public TokenUser(String accessToken, Date issuedAt, Date expiresAt,
      String userNumber) {
    super(accessToken, issuedAt, expiresAt);
    this.userNumber = userNumber;
  }
}
