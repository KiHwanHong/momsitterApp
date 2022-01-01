package momsitter.support.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import momsitter.support.dto.AbstractDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserBaseDto extends AbstractDto {

  /** 대상자 사원번호 */
  private String userNumber;

}
