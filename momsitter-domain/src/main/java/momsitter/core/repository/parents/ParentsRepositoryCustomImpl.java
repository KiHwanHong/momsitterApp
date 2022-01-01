package momsitter.core.repository.parents;


import com.querydsl.core.types.Projections;
import momsitter.core.MomsitterDomainRepositorySupport;
import momsitter.core.dto.members.MembersDto;
import momsitter.core.dto.parents.ParentsDto;
import momsitter.core.entity.parents.ParentsEntity;
import momsitter.core.entity.parents.QParentsEntity;

public class ParentsRepositoryCustomImpl extends MomsitterDomainRepositorySupport implements ParentsRepositoryCustom {

  public ParentsRepositoryCustomImpl() {
    super(ParentsEntity.class);
  }

  @Override
  public ParentsDto findAllData() {

    QParentsEntity qParentsEntity = QParentsEntity.parentsEntity;

    return getQuerydsl().createQuery()
        .select(Projections.constructor(ParentsDto.class, qParentsEntity))
        .from(qParentsEntity).limit(1).fetchOne();
  }
}
