package momsitter.api.intercepter;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import momsitter.api.config.security.authentication.JwtStore;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ApiClientIntercepter  implements RequestInterceptor {

  private static final String AUTHORIZATION_HEADER_NAME = "X-Api-Token";

  @Override
  public void apply(RequestTemplate template) {
    JwtStore jwtStore = JwtStore.getInstance();
    template.header(AUTHORIZATION_HEADER_NAME, jwtStore.getAccessToken());
    log.debug("ApiClientIntercepter > RequestTemplate={}", template);
  }

}
