package momsitter.core.repository.members;

import momsitter.core.entity.members.MembersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembersRepository extends JpaRepository<MembersEntity, Long>,
    MembersRepositoryCustom {
}
