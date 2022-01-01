package momsitter.core;

import com.zaxxer.hikari.HikariDataSource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = {MomsitterDomainDataSourceJpaConfig.MOMSITTER_DOMAIN_PACKAGE},
    transactionManagerRef = MomsitterDomainDataSourceJpaConfig.MOMSITTER_DOMAIN_TRANSACTION_MANAGER,
    entityManagerFactoryRef = MomsitterDomainDataSourceJpaConfig.MOMSITTER_DOMAIN_ENTITY_MANAGER_FACTORY)
@Configuration
public class MomsitterDomainDataSourceJpaConfig {

  public static final String MOMSITTER_DOMAIN_ENTITY_MANAGER_FACTORY = "momsitterDomainEntityManagerFactory";
  public static final String MOMSITTER_DOMAIN_JPA_PROPERTIES = "momsitterDomainJpaProperties";
  public static final String MOMSITTER_DOMAIN_HIBERNATE_PROPERTIES = "momsitterDomainHibernateProperties";
  public static final String MOMSITTER_DOMAIN_DATA_SOURCE = "momsitterDomainDataSource";
  public static final String MOMSITTER_DOMAIN_TRANSACTION_MANAGER = "momsitterDomainTransactionManager";
  public static final String MOMSITTER_DOMAIN_PERSIST_UNIT = "momsitterDomain";
  public static final String MOMSITTER_DOMAIN_PACKAGE = "momsitter.core";
  public static final String MOMSITTER_DOMAIN_JDBC_TEMPLATE = "momsitterDomainJdbcTemplate";

  @Bean(name = MOMSITTER_DOMAIN_ENTITY_MANAGER_FACTORY)
  public LocalContainerEntityManagerFactoryBean momsitterDomainEntityManagerFactory() {
    return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(),
        momsitterDomainJpaProperties().getProperties(), null)
        .dataSource(momsitterDomainDataSource())
        .properties(momsitterDomainHibernateProperties()
            .determineHibernateProperties(momsitterDomainJpaProperties().getProperties(),
                new HibernateSettings()))
        .persistenceUnit(MOMSITTER_DOMAIN_PERSIST_UNIT)
        .packages(
            MOMSITTER_DOMAIN_PACKAGE)
        .build();
  }

  @Bean(name = MOMSITTER_DOMAIN_JPA_PROPERTIES)
  @ConfigurationProperties(prefix = "certificate.domain.jpa")
  public JpaProperties momsitterDomainJpaProperties() {
    return new JpaProperties();
  }

  @Bean(name = MOMSITTER_DOMAIN_HIBERNATE_PROPERTIES)
  @ConfigurationProperties(prefix = "certificate.domain.jpa.hibernate")
  public HibernateProperties momsitterDomainHibernateProperties() {
    return new HibernateProperties();
  }

  @Bean
  @Qualifier(MOMSITTER_DOMAIN_DATA_SOURCE)
  @ConfigurationProperties("momsitter.domain.datasource")
  public DataSource momsitterDomainDataSource() {
    return DataSourceBuilder.create().type(HikariDataSource.class).build();
  }

  @Bean(name = MOMSITTER_DOMAIN_TRANSACTION_MANAGER)
  public PlatformTransactionManager momsitterDomainTransactionManager(
      @Autowired @Qualifier(MOMSITTER_DOMAIN_ENTITY_MANAGER_FACTORY) EntityManagerFactory momsitterDomainEntityManagerFactory) {
    return new JpaTransactionManager(momsitterDomainEntityManagerFactory);
  }

  @Bean(name = MOMSITTER_DOMAIN_JDBC_TEMPLATE)
  public JdbcTemplate momsitterDomainJdbcTemplate(
      @Qualifier("momsitterDomainDataSource") DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }
}
