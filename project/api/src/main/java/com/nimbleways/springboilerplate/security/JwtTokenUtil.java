package com.nimbleways.springboilerplate.security;

import com.nimbleways.springboilerplate.entities.User;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static java.lang.String.format;

@Component
public class JwtTokenUtil {

    private String jwtSecret;

    private String jwtIssuer;

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);


    public JwtTokenUtil() {
    }

    @Autowired
    public JwtTokenUtil(@Value("${security.jwt.secret}") final String jwtSecret,
            @Value("${security.jwt.issuer}") final String jwtIssuer) {
        this.jwtSecret = jwtSecret;
        this.jwtIssuer = jwtIssuer;
    }


    public String generateAccessToken(final User user) {
        final long jwtValidityDuration = 7 * 24 * 60 * 60 * 1000L; // Token valid one week
        return Jwts.builder()
                .setSubject(format("%s,%s", user.getId(), user.getEmail()))
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtValidityDuration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserId(final String token) {
        final Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject().split(",")[0];
    }

    public String getUsername(final String token) {
        final Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject().split(",")[1];
    }

    public Date getExpirationDate(final String token) {
        final Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    public boolean validate(final String token) {
        if(token == null) {
            return false;
        }
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            LOGGER.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            LOGGER.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            LOGGER.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            LOGGER.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }

}
