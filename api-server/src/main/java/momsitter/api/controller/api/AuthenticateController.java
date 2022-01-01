package momsitter.api.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import momsitter.api.service.AuthenticateService;
import momsitter.support.dto.ApiResponse;
import momsitter.support.dto.ApiResponseGenerator;
import momsitter.support.dto.token.Token;
import momsitter.support.dto.token.TokenAuthKey;

@Api("맘시터서비스 - 인증관련 API")
@Slf4j
@RestController("authenticate_w2")
@RequestMapping("/api/auth/token")
@AllArgsConstructor
public class AuthenticateController {

  private final AuthenticateService authenticateService;

  @ApiOperation("인증 토큰 발행")
  @PostMapping
  public @ResponseBody ApiResponse<Token> issueToken(@RequestBody TokenAuthKey authKey) {
    return ApiResponseGenerator.success(authenticateService.issueToken(authKey));
  }
}
