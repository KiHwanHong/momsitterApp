package momsitter.support.context;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ApplicationContextInitializeConfig {

  private final ApplicationContext applicationContext;

  @Bean
  public CommandLineRunner applicationContextInitialize() {
    return args -> ApplicationContextProvider.initialize(applicationContext);
  }

}
