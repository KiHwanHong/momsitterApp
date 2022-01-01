package momsitter.core.repository.parents;

import momsitter.core.entity.parents.ParentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ParentsRepository extends JpaRepository<ParentsEntity, Long>, ParentsRepositoryCustom {

}
