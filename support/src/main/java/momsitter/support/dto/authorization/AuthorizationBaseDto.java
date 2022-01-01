package momsitter.support.dto.authorization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import momsitter.support.dto.AbstractDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationBaseDto extends AbstractDto {

  private String keyUserNumber;

}
