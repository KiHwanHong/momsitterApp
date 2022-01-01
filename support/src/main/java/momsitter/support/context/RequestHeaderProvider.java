package momsitter.support.context;

import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RequestHeaderProvider {

  private RequestHeaderProvider() {
    throw new UnsupportedOperationException();
  }

  private static final List<String> IP_HEADER_CANDIDATES = List.of(
      "X-Forwarded-For",
      "Proxy-Client-IP",
      "WL-Proxy-Client-IP",
      "HTTP_X_FORWARDED_FOR",
      "HTTP_X_FORWARDED",
      "HTTP_X_CLUSTER_CLIENT_IP",
      "HTTP_CLIENT_IP",
      "HTTP_FORWARDED_FOR",
      "HTTP_FORWARDED",
      "HTTP_VIA",
      "REMOTE_ADDR");

  public static String clientIp() {

    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

    if (requestAttributes == null) {
      return "0.0.0.0";
    }

    HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

    return IP_HEADER_CANDIDATES.stream()
        .map(request::getHeader)
        .filter(Objects::nonNull)
        .filter(str -> !str.isBlank())
        .filter(str -> !"unknown".equalsIgnoreCase(str))
        .findFirst()
        .orElse(request.getRemoteAddr())
        .split(",")[0];
  }

  public static String userAgent() {
    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

    if (requestAttributes == null) {
      return "default";
    }

    HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

    return request.getHeader(HttpHeaders.USER_AGENT);
  }
}
