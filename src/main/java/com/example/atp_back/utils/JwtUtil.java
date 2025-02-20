package com.example.atp_back.utils;

import com.example.atp_back.user.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private static String SECRET;
    private static Long EXPIRATION;

    @Value("${jwtData.secret}")
    public void setSECRET(String value) {
        JwtUtil.SECRET = value;
    }
    @Value("${jwtData.expiration}")
    public void setEXPIRATION(Long value) {
        JwtUtil.EXPIRATION = value;
    }

    public static String generateToken(Long userIdx, String userEmail, String userRole) {
        Claims claims = Jwts.claims();
        claims.put("userIdx", userIdx);
        claims.put("userEmail", userEmail);
        claims.put("userRole", userRole);
        return Jwts.builder().setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET).build().parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static String getUserEmailFromToken(String token) {
        try {
            Claims claim = Jwts.parserBuilder().setSigningKey(SECRET).build().parseClaimsJws(token).getBody();
            return claim.get("userEmail", String.class);
        } catch (Exception e) {
            return null;
        }
    }
    public static User buildUserDataFromToken(String token) {
        try {
            Claims claim = Jwts.parserBuilder().setSigningKey(SECRET).build().parseClaimsJws(token).getBody();
            User user = User.builder().idx(claim.get("userIdx", Long.class)).email(claim.get("userEmail", String.class)).role(claim.get("userRole", String.class)).build();
            return user;
        } catch (Exception e) {
            return null;
        }
    }
}
