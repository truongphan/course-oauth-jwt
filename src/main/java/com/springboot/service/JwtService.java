package com.springboot.service;

import com.springboot.model.CustomUserDetails;
import com.springboot.model.LoginRequest;
import com.springboot.model.LoginResponse;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Service
public class JwtService {
    private final String JWT_SECRET = "truongphan0810";
    private final long JWT_EXPIRATION = 604800000L;

    @Autowired
    AuthenticationProvider authenticationProvider;

    public LoginResponse getAccessToken(HttpServletRequest httpRequest) {
        LoginRequest loginRequest = getBasicAuthentication(httpRequest);
        if (loginRequest == null) {
            throw new BadCredentialsException("Can not get basic authentication");
        }
        // Authenticate from user,pass
        Authentication authentication = null;
        try {
            authentication = authenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        // if no exception, set Authorization info into Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // return jwt for user
        String jwt = generateToken((CustomUserDetails)authentication.getPrincipal());
        return new LoginResponse(jwt);
    }

    private LoginRequest getBasicAuthentication(HttpServletRequest httpRequest) {
        String authorization = httpRequest.getHeader("Authorization");
        if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
            String base64Credentials = authorization.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            // credentials = username:password
            String[] values = credentials.split(":", 2);
            return LoginRequest.builder().username(values[0]).password(values[1]).build();
        }
        return null;
    }

    public String generateToken(CustomUserDetails userDetails) {
        // Get user info
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        // Create jwt from user id
        return Jwts.builder()
                .setSubject(Long.toString(userDetails.getUser().getId()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey("fds").parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}
