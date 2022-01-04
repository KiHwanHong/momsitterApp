package momsitter.core.repository.members;

import java.util.Optional;
import momsitter.core.domain.Id;
import momsitter.core.domain.Password;
import momsitter.core.entity.members.MembersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembersRepository extends JpaRepository<MembersEntity, Long>,
    MembersRepositoryCustom {

  Optional<MembersEntity> findByIdAndPassword(Id id, Password password);

  Optional<MembersEntity> findById(Id id);
}
