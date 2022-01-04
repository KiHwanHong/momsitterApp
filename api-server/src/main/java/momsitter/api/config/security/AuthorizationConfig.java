package momsitter.api.config.security;

import java.util.List;
import lombok.RequiredArgsConstructor;
import momsitter.api.config.interceptor.AuthorizationInterceptor;
import momsitter.api.config.resolver.AuthMemberArgumentResolver;
import momsitter.api.config.security.authentication.JwtTokenProvider;
import momsitter.api.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthorizationConfig implements WebMvcConfigurer {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthorizationInterceptor authorizationInterceptor;

    public AuthorizationConfig(AuthService authService,
        JwtTokenProvider jwtTokenProvider,
        AuthorizationInterceptor authorizationInterceptor) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authorizationInterceptor = authorizationInterceptor;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(createAuthMemberArgumentResolver());
    }

    @Bean
    public AuthMemberArgumentResolver createAuthMemberArgumentResolver() {
        return new AuthMemberArgumentResolver(authService, jwtTokenProvider);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor)
            .addPathPatterns("/api/members/me");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedMethods("*")
            .allowedOrigins("*");
    }
}
