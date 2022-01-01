package momsitter.core.repository.members;

import java.util.List;
import momsitter.core.dto.members.MembersDto;

public interface MembersRepositoryCustom {

  List<MembersDto> findAllData();

}
