package pl.dawid.transportapp.security.filter;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;
import pl.dawid.transportapp.security.config.Constants;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import static pl.dawid.transportapp.security.config.Constants.*;


public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthorizationFilter.class);
    public JwtAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_AUTHORIZATION);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(header, response);
        if (authentication == null) {
            chain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token, HttpServletResponse response) {
        try{
        var signingKey = SECRET.getBytes(StandardCharsets.UTF_8);
        var parsedToken = Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""));

        var username = parsedToken
                .getBody()
                .getSubject();

        var authorities = ((List<?>) parsedToken.getBody()
                .get(ROLES)).stream()
                .map(authority -> new SimpleGrantedAuthority((String) authority))
                .collect(Collectors.toList());

        if (!StringUtils.isEmpty(username)) {
            return new UsernamePasswordAuthenticationToken(username, null, authorities);
        }
    } catch (ExpiredJwtException exception) {
        log.warn("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
    } catch (UnsupportedJwtException exception) {
        log.warn("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
    } catch (MalformedJwtException exception) {
        log.warn("Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
    } catch (SignatureException exception) {
        log.warn("Request to parse JWT with invalid signature : {} failed : {}", token, exception.getMessage());
    } catch (IllegalArgumentException exception) {
        log.warn("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
    }
        return null;
    }
}
