package momsitter.core.repository.members;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import java.util.List;
import momsitter.core.MomsitterDomainRepositorySupport;
import momsitter.core.dto.members.MembersDto;
import momsitter.core.entity.members.MembersEntity;
import momsitter.core.entity.members.QMembersEntity;

public class MembersRepositoryCustomImpl extends MomsitterDomainRepositorySupport implements MembersRepositoryCustom {


  public MembersRepositoryCustomImpl() {
    super(MembersEntity.class);
  }

  @Override
  public List<MembersDto> findAllData() {
    QMembersEntity qMembersEntity = QMembersEntity.membersEntity;

    return getQuerydsl().createQuery()
        .select(Projections.constructor(MembersDto.class, qMembersEntity))
        .from(qMembersEntity).fetchResults().getResults();
  }
}
