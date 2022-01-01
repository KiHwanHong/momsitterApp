package momsitter.core.service.parents;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import momsitter.core.MomsitterDomainTransactional;
import momsitter.core.dto.parents.ParentsDto;
import momsitter.core.repository.parents.ParentsRepository;
import org.springframework.stereotype.Service;

/**
 * 부모회원 처리관련
 */
@Service
@Slf4j
@RequiredArgsConstructor
@MomsitterDomainTransactional
public class ParentsService {

  private final ParentsRepository parentsRepository;

  public ParentsDto findAll() {
    return parentsRepository.findAllData();
  }
}
