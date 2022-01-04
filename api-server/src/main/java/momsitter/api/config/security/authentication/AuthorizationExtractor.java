package momsitter.api.config.security.authentication;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

public class AuthorizationExtractor {

  private static final String AUTHORIZATION = "Authorization";
  private static final String BEARER = "Bearer";
  private static final String ACCESS_TOKEN_TYPE = "AuthorizationExtractor.ACCESS_TOKEN_TYPE";

  public static String extract(HttpServletRequest request) {
    Enumeration<String> headers = request.getHeaders(AUTHORIZATION);

    while(headers.hasMoreElements()) {
      String value = headers.nextElement();
      if(isBearerHeader(value)) {
        String authHeaderValue = value.substring(BEARER.length()).trim();
        request.setAttribute(ACCESS_TOKEN_TYPE, value.substring(0, BEARER.length()).trim());
        int commaIndex = authHeaderValue.indexOf(',');

        if (commaIndex > 0) {
          authHeaderValue = authHeaderValue.substring(0, commaIndex);
        }
        return authHeaderValue;
      }
    }
    return null;

  }
  public static boolean isBearerHeader(String value) {
    return value.toLowerCase().startsWith(BEARER.toLowerCase());
  }

}
