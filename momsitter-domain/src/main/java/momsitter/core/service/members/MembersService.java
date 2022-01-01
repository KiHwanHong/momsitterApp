package momsitter.core.service.members;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import momsitter.core.MomsitterDomainTransactional;
import momsitter.core.dto.members.MembersDto;
import momsitter.core.dto.parents.ParentsDto;
import momsitter.core.repository.members.MembersRepository;
import org.springframework.stereotype.Service;

/**
 * 시터회원 처리관련
 */
@Service
@Slf4j
@RequiredArgsConstructor
@MomsitterDomainTransactional
public class MembersService {

  private final MembersRepository membersRepository;

  public List<MembersDto> findAllData() {
    return membersRepository.findAllData();
  }
}
