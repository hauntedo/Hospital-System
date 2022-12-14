package ru.itis.hauntedo.simbirtest.provider.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.itis.hauntedo.simbirtest.dto.response.UserResponse;
import ru.itis.hauntedo.simbirtest.exception.unauthorized.AuthenticationHeaderException;
import ru.itis.hauntedo.simbirtest.provider.JwtAccessTokenProvider;
import ru.itis.hauntedo.simbirtest.service.UserService;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static ru.itis.hauntedo.simbirtest.utils.consts.ApiConst.ROLE;

@Component
@RequiredArgsConstructor
public class JwtAccessTokenProviderImpl implements JwtAccessTokenProvider {

    @Value("${jwt.expiration.access.mills}")
    private long expirationAccessInMills;

    @Value("${jwt.secret}")
    private String jwtSecret;

    private final UserService userService;

    @Override
    public String generateAccessToken(String subject, Map<String, Object> data) {
        Map<String, Object> claims = new HashMap<>(data);
        claims.put(Claims.SUBJECT, subject);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(Instant.now().plusMillis(expirationAccessInMills)))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    @Override
    public boolean validateAccessToken(String accessToken) {
        try {
            Claims claims = parseAccessToken(accessToken);
            Date date = claims.getExpiration();
            return date.after(new Date());
        } catch (ExpiredJwtException e) {
            throw new AuthenticationHeaderException("Token date expired");
        }
    }

    @Override
    public UserResponse getUserByToken(String token) throws AuthenticationHeaderException {
        Claims claims = parseAccessToken(token);
        String subject = claims.getSubject();
        try {
            return userService.findBySubject(subject);
        } catch (ExpiredJwtException e) {
            throw new AuthenticationHeaderException("Token date expired");
        }

    }

    @Override
    public String getRoleFromAccessToken(String accessToken) {
        try {
            return  String.valueOf(parseAccessToken(accessToken).get(ROLE));
        } catch (ExpiredJwtException e) {
            return String.valueOf(e.getClaims().get(ROLE));
        }
    }

    @Override
    public Date getExpirationDateFromAccessToken(String accessToken) {
        try {
            return Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(accessToken)
                    .getBody().getExpiration();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getExpiration();
        }
    }

    @Override
    public String getSubjectFromAccessToken(String accessToken) {
        try {
            return parseAccessToken(accessToken).getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        }
    }

    private Claims parseAccessToken(String accessToken) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(accessToken).getBody();
    }
}
