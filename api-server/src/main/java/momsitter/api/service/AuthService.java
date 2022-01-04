package momsitter.api.service;

import lombok.RequiredArgsConstructor;
import momsitter.api.config.security.authentication.JwtTokenProvider;
import momsitter.api.controller.Request.LoginRequestDto;
import momsitter.api.controller.Response.LoginResponse;
import momsitter.core.domain.Id;
import momsitter.core.domain.Password;
import momsitter.core.dto.AuthMemberDto;
import momsitter.core.entity.members.MembersEntity;
import momsitter.core.repository.members.MembersRepository;
import momsitter.support.exception.AuthenticationException;
import momsitter.support.exception.LoginFailedException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

  private final JwtTokenProvider jwtTokenProvider;
  private final MembersRepository membersRepository;

  public LoginResponse login(LoginRequestDto loginRequestDto) {
    Id id = new Id(loginRequestDto.getId());
    Password password = new Password(loginRequestDto.getPassword());

    MembersEntity membersEntity = membersRepository.findByIdAndPassword(id, password).orElseThrow(() -> new LoginFailedException("로그인에 실패했습니다."));
    return new LoginResponse(jwtTokenProvider.createToken(String.valueOf(membersEntity.getMembersId())));
  }

  public AuthMemberDto findAuthMemberByToken(String token) {
    Id id = new Id(jwtTokenProvider.getPayload(token));

    MembersEntity membersEntity = membersRepository.findById(id)
        .orElseThrow(() -> new AuthenticationException("로그인 정보가 유효하지 않습니다"));

    return new AuthMemberDto(membersEntity.getMembersNumber());
  }
}
