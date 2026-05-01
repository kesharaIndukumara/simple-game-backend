package com.navishkadarshana.smileygame.utilities;

import com.navishkadarshana.smileygame.exception.dto.CustomServiceException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * @author Navishka Darshana - navishkada@gmail.com
 * @project simple-game-backend
 * @CreatedBy IntelliJ IDEA
 * @created 17/09/2021 - 10:04 AM
 */
@Log4j2
@RequiredArgsConstructor
@Component
public class EmailVerificationTokenGenerator {

    private final DateGenerator dateGenerator;

    private static final String SECRET = "AIsanifM4hK0sMK2ahi8KRp1zhiAX18b654051rLK51it0m4y19932612a92vA3Ws8Q14381Kth5asMn0pm5";
    public static final String EMAIL_CLAIM = "email";


    private Jws<Claims> getClaimsJws(String auth, SecretKey secretKey) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(auth);
        } catch (Exception e) {
        log.error(e.getMessage());
            if (e.getMessage().toLowerCase().startsWith("jwt expired")){
                throw new CustomServiceException("Email Verification Link Expired!");
            }else{
            throw new CustomServiceException("Something went wrong, please try again");
            }
        }
    }

    public String generate(long userId, String email) {

        Date issued = new Date();
        SecretKey secretKey = Keys.hmacShaKeyFor((SECRET).getBytes());

        return Jwts.builder()
                .setIssuer("ceyentra")
                .setSubject(String.valueOf(userId))
                .setIssuedAt(issued)
                .setExpiration(dateGenerator.changeDateFromMinutes(new Date(), 30))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .claim(EMAIL_CLAIM, email)
                .compact();
    }

    public Jws<Claims> verify(String auth) {
        SecretKey secretKey = Keys.hmacShaKeyFor((SECRET).getBytes());
        return getClaimsJws(auth, secretKey);
    }

}
