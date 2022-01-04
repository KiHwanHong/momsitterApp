package momsitter.api.controller.Request;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LoginRequestDto {

  @NotBlank(message = "회원 아이디는 Null 이거나 공백일 수 없습니다.")
  private String id;

  @NotBlank(message = "회원 비밀번호는 Null 이거나 공백일 수 없습니다.")
  private String password;
}
