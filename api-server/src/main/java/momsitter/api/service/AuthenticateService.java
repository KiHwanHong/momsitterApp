package momsitter.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import momsitter.api.config.security.authentication.JwtProvider;
import momsitter.support.dto.token.Token;
import momsitter.support.dto.token.TokenAuthKey;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticateService {

  private static final String AUTHORIZATION_HEADER_NAME = "X-Api-Token";

  private final JwtProvider jwtProvider;

  public Token issueToken(final TokenAuthKey authKey) {

    Token token = jwtProvider.createToken();

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add(AUTHORIZATION_HEADER_NAME, token.getAccessToken());

    return token;
  }

}
