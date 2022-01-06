package momsitter.api.config.resolver;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.CacheControl;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import momsitter.api.config.converter.DescriptionCodeJsonConverter;
import momsitter.api.config.converter.LocalDateJsonConverter;
import momsitter.api.config.converter.LocalDateTimeJsonConverter;
import momsitter.support.code.DescriptionCode;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig extends WebMvcConfigurationSupport {

  private static final String RESOURCE_LOCATION = "classpath:/META-INF/resources/";
  private static final String ALL_PATH_PATTERN = "/**";

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler(ALL_PATH_PATTERN)
        .addResourceLocations(RESOURCE_LOCATION)
        .setCacheControl(CacheControl.noCache());

    registry.addResourceHandler("index.html")
        .addResourceLocations(RESOURCE_LOCATION)
        .setCacheControl(CacheControl.maxAge(0, TimeUnit.SECONDS));

    registry.addResourceHandler("/favicon.ico")
        .addResourceLocations(RESOURCE_LOCATION)
        .setCacheControl(CacheControl.noCache());

    registry.addResourceHandler("/static/**")
        .addResourceLocations(RESOURCE_LOCATION + "static/")
        .setCacheControl(CacheControl.noCache());

    registry.addResourceHandler("/webjars/**")
        .addResourceLocations(RESOURCE_LOCATION + "webjars/");
  }

//  @Override
//  public void addInterceptors(InterceptorRegistry registry) {
//    registry.addInterceptor(new TraceLogInterceptor());
//  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
    argumentResolvers.add(new AuthenticationPrincipalArgumentResolver());
  }

  @Bean
  public MappingJackson2HttpMessageConverter converter() {
    return new MappingJackson2HttpMessageConverter(objectMapper());
  }

  private ObjectMapper objectMapper() {
    final ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(javaTimeModule());
    objectMapper.disable(FAIL_ON_UNKNOWN_PROPERTIES);
    return objectMapper;
  }

  private SimpleModule javaTimeModule() {
    return new JavaTimeModule()
        .addSerializer(LocalDateTime.class, new LocalDateTimeJsonConverter.Serializer())
        .addDeserializer(LocalDateTime.class, new LocalDateTimeJsonConverter.Deserializer())
        .addSerializer(LocalDate.class, new LocalDateJsonConverter.Serializer())
        .addDeserializer(LocalDate.class, new LocalDateJsonConverter.Deserializer())
        .addSerializer(DescriptionCode.class, new DescriptionCodeJsonConverter.Serializer())
        .addDeserializer(Enum.class, new DescriptionCodeJsonConverter.Deserializer());
  }
}
