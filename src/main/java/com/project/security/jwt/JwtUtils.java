package com.project.security.jwt;

import com.project.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

   private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);


    @Value("${backendapi.app.jwtSecretKey}")
    private String jwtSecret;

    @Value("${backendapi.app.jwtExpirationMs}")
    private long jwtExpirationMs;

    public  String generateJwtToken(Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String token=generateJwtTokenFromUsername(userDetails.getUsername());
        return token;


    }

    public String generateJwtTokenFromUsername(String username) {
       return Jwts.builder()
               .setSubject(username)
               .setIssuedAt(new Date())
               .setExpiration(new Date(new Date().getTime()+jwtExpirationMs))
               .signWith(SignatureAlgorithm.HS512, jwtSecret)
               .compact();

    }

    public boolean validateJwtToken(String jwtToken){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwtToken);
            return true;
        } catch (SignatureException e) {
            LOGGER.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            LOGGER.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            LOGGER.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOGGER.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public String getUsernameFromJwtToken(String token){
        return Jwts.parser()
             .setSigningKey(jwtSecret)
             .parseClaimsJws(token)
             .getBody()
             .getSubject();
    }

}
