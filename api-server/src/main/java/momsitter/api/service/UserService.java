package momsitter.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import momsitter.api.config.security.authentication.JwtTokenProvider;
import momsitter.support.dto.token.Token;
import momsitter.support.dto.user.UserAuthorizationDto;
import momsitter.support.dto.user.UserAuthorizationKeyDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * 사용자 관련 서비스
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final JwtTokenProvider jwtTokenProvider;


  public UserAuthorizationDto verifyAuthorizationKeyBy(final UserAuthorizationKeyDto userAuthorizationKeyDto) {

    Token token = this.createUserToken(userAuthorizationKeyDto.getMembersNumber(), userAuthorizationKeyDto.getMembersPassword());
//
//    Boolean isUpdatedUserPassword = DefaultDateTimeConverter.convertDateTime(user.getUserPasswordSettingDate())
//        .isBefore(DefaultDateTimeConverter.DATE_TIME_2999_12_31);

    return UserAuthorizationDto.builder().userNumber(userAuthorizationKeyDto.getMembersNumber()).token(token).isUpdatedUserPassword(true).build();
  }

  private Token createUserToken(final String username, final String password) {
    return jwtTokenProvider.createUserToken(new UsernamePasswordAuthenticationToken(username, password));
  }
}
