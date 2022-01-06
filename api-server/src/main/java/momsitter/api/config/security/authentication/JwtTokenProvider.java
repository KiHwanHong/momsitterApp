package momsitter.api.config.security.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import momsitter.support.dto.token.Token;
import momsitter.support.dto.token.TokenUser;
import momsitter.support.exception.AuthenticationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.token.secret-key}")
    private String secret;
    private String secretKey;

    @Value("${jwt.token.expire-length}")
    private long tokenValidityInSeconds;

    @PostConstruct
    protected void init() {secretKey= Base64.getEncoder().encodeToString(secret.getBytes());}

    public Token createUserToken(Authentication authentication) {
        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + tokenValidityInSeconds * 1000); // 90분

        String token = Jwts.builder()
            .setSubject(authentication.getName())
            .setIssuedAt(issuedAt)
            .setExpiration(expiration)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();

        return Token.builder().accessToken(token).issuedAt(issuedAt).expiresAt(expiration).build();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

        Date issuedAt = claims.getIssuedAt();
        Date expiresAt = claims.getExpiration();

        String userNumber = claims.getSubject();

        final TokenUser principal = new TokenUser(token, issuedAt, expiresAt, userNumber);

        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(principal, null, authorities);
    }

    public boolean validateToken(String token) {
        if(StringUtils.hasText(token)) {
            try {
                Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
                return true;
            } catch (SecurityException | MalformedJwtException | SignatureException e) {
                log.info("잘못된 jwt 서명");
            } catch (ExpiredJwtException e) {
                log.info("만료된 jwt 토큰");
            } catch (UnsupportedJwtException e) {
                log.info("지원되지 않는 jwt 토큰");
            } catch (IllegalArgumentException e) {
                log.info("jwt 토큰 오류");
            }
        }
        return false;
    }

    public String resolveToken(String header) {
        if(StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
