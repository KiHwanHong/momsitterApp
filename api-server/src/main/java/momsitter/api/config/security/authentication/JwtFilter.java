package momsitter.api.config.security.authentication;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        String jwt = jwtTokenProvider.resolveToken(authorization);

        if(jwtTokenProvider.validateToken(jwt)) {
            // 현재 사용하는 토큰이 만료시간이 지나지 않아서 사용가능하지만, 로그아웃 처리된 토큰이면 인증 불가
//            if(!BooleanUtils.toBoolean(apiCallService.existsByToken(jwt))) {
                Authentication authentication = jwtTokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
        }

        filterChain.doFilter(request, response);
    }
}
