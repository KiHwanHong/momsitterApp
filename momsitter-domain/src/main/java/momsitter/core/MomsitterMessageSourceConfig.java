package momsitter.core;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
@RequiredArgsConstructor
public class MomsitterMessageSourceConfig {

  @Bean
  public ReloadableResourceBundleMessageSource MomsitterMessageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasenames(
        "classpath:/messages/sample"
    );
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }

  @Bean(name = "momsitterMessages")
  public MessageSourceAccessor MomsitterMessages() {
    return new MessageSourceAccessor(MomsitterMessageSource());
  }
}
