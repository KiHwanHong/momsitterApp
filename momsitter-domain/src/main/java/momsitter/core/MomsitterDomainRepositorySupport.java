package momsitter.core;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.util.Assert;

@MomsitterDomainTransactional(readOnly = true, propagation = Propagation.SUPPORTS)
public abstract class MomsitterDomainRepositorySupport extends
    QuerydslRepositorySupport implements
    InitializingBean {

  public MomsitterDomainRepositorySupport(Class<?> domainClass) {
    super(domainClass);
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    Assert.notNull(getQuerydsl(), "The QueryDsl must not be null.");
  }

  @Nonnull
  @Override
  public Querydsl getQuerydsl() {
    return Objects.requireNonNull(super.getQuerydsl());
  }

  @Nonnull
  @Override
  public EntityManager getEntityManager() {
    return Objects.requireNonNull(super.getEntityManager());
  }

  @Override
  @PersistenceContext(unitName = MomsitterDomainDataSourceJpaConfig.MOMSITTER_DOMAIN_PERSIST_UNIT)
  public void setEntityManager(
      @Nonnull EntityManager entityManager) {
    super.setEntityManager(entityManager);
  }
}
