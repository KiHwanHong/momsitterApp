package momsitter.api.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import momsitter.api.service.UserService;
import momsitter.api.util.MobileDetector;
import momsitter.support.dto.ApiResponse;
import momsitter.support.dto.ApiResponseGenerator;
import momsitter.support.dto.user.UserAuthorizationDto;
import momsitter.support.dto.user.UserAuthorizationKeyDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api("서비스 - 인증관련 API")
@RestController("momsitter_auth")
@Slf4j
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthenticateController {

  private final UserService userService;

  @ApiOperation("인증 토큰 발행")
  @PostMapping("/token")
  public @ResponseBody ApiResponse<UserAuthorizationDto> verifyAuthorizationKey(@Valid @RequestBody UserAuthorizationKeyDto userAuthorizationKeyDto) {
    final var result = userService.verifyAuthorizationKeyBy(userAuthorizationKeyDto);
//    final var loginType = MobileDetector.isMobileDevice(request.getHeader("user-agent")) ? "MO" : "PC";
    return ApiResponseGenerator.success(result);
  }
}
