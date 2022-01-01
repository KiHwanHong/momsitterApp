package momsitter.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

import static springfox.documentation.builders.RequestHandlerSelectors.any;

@Profile("local")
@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket api() {
    List<ResponseMessage> responseMessageList = Arrays.asList(
        new ResponseMessageBuilder().code(HttpStatus.BAD_REQUEST.value()).message("잘못된 요청").build(),
        new ResponseMessageBuilder().code(HttpStatus.UNAUTHORIZED.value()).message("인증 실패").build(),
        new ResponseMessageBuilder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .message("API 오류").build()
    );

    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .globalResponseMessage(RequestMethod.GET, responseMessageList)
        .globalResponseMessage(RequestMethod.POST, responseMessageList)
        .globalResponseMessage(RequestMethod.PUT, responseMessageList)
        .globalResponseMessage(RequestMethod.DELETE, responseMessageList)
        .select()
        .apis(any())
        //.paths(apiPaths())
        .paths(PathSelectors.any())
        .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("맘시터서비스 REST API 명세서")
        .description("맘시터서비스 REST API 명세서입니다.")
        .version("1.0")
        .build();
  }
}
