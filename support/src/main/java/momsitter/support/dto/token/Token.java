package momsitter.support.dto.token;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import momsitter.support.dto.AbstractDto;

@Getter
@Setter
@NoArgsConstructor
public class Token extends AbstractDto {

  private String accessToken;

  @JsonInclude(Include.NON_NULL)
  private Date issuedAt;

  @JsonInclude(Include.NON_NULL)
  private Date expiresAt;

  @Builder
  public Token(String accessToken, Date issuedAt, Date expiresAt) {
    this.accessToken = accessToken;
    this.issuedAt = issuedAt;
    this.expiresAt = expiresAt;
  }
}
