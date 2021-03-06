package momsitter.api.config.resolver;

import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
public class AuthMemberArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return false;
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    return null;
  }
//
//  private final AuthService authService;
//  private final JwtTokenProvider jwtTokenProvider;
//
//  @Override
//  public boolean supportsParameter(MethodParameter parameter) {
//    return parameter.hasParameterAnnotation(AuthMember.class);
//  }
//
//  @Override
//  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
//      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
//
//    String token = AuthorizationExtractor.extract(webRequest.getNativeRequest(HttpServletRequest.class));
//    if (isInvalidToken(token)) {
//      throw new AuthenticationException("로그인 정보가 유효하지 않습니다.");
//    }
//    return authService.findAuthMemberByToken(token);
//  }
//
//  private boolean isInvalidToken(String token) {
//    return Objects.isNull(token) || token.isEmpty() || !jwtTokenProvider.validateToken(token);
//  }

}
