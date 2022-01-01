package momsitter.support.dto.token;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import momsitter.support.dto.AbstractDto;

@Getter
@Setter
@NoArgsConstructor
public class TokenExpiredHistoryDto extends AbstractDto {

  private String tokenValue;
  private LocalDateTime tokenExpiredDate;

  @Builder
  public TokenExpiredHistoryDto(String tokenValue, LocalDateTime tokenExpiredDate) {
    this.tokenValue = tokenValue;
    this.tokenExpiredDate = tokenExpiredDate;
  }
}
