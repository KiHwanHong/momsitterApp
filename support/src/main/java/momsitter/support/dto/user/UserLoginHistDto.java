package momsitter.support.dto.user;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import momsitter.support.dto.AbstractDto;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class UserLoginHistDto extends AbstractDto {

  @EqualsAndHashCode.Include
  private Long loginSeq;

  private LocalDateTime loginDate;

  private String loginType;

  private String loginUserNumber;

  @Builder
  public UserLoginHistDto(Long loginSeq, LocalDateTime loginDate, String loginType,
      String loginUserNumber) {
    this.loginSeq = loginSeq;
    this.loginDate = loginDate;
    this.loginType = loginType;
    this.loginUserNumber = loginUserNumber;
  }
}
