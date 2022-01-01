package momsitter.api.config;

import feign.Logger;
import momsitter.core.MomsitterConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableCaching
@ComponentScan(basePackages = {"momsitter.api", "momsitter.support"})
@Import({ MomsitterConfig.class})
public class ApiConfig {

  @Bean
  @Profile({"beta", "dev", "local"})
  Logger.Level feignLoggerLevelDev() {
    return Logger.Level.FULL;
  }

  @Bean
  @Profile({"prod"})
  Logger.Level feignLoggerLevelProd() {
    return Logger.Level.BASIC;
  }
}
