package momsitter.api.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import momsitter.api.controller.Request.LoginRequestDto;
import momsitter.api.controller.Response.LoginResponse;
import momsitter.api.service.AuthService;
import momsitter.support.dto.ApiResponse;
import momsitter.support.dto.ApiResponseGenerator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api("맘시터서비스 - 인증관련 API")
@Slf4j
@RestController("authenticate_w2")
@RequestMapping("/api/auth/token")
@AllArgsConstructor
public class AuthenticateController {

  private final AuthService authService;

  @ApiOperation("인증 토큰 발행")
  @PostMapping
  public @ResponseBody ApiResponse<LoginResponse> issueToken(@RequestBody LoginRequestDto loginRequestDto) {
    return ApiResponseGenerator.success(authService.login(loginRequestDto));
  }
}
