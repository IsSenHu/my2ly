package com.husen.jian.jwt;

import com.husen.jian.dao.po.UserPo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by HuSen on 2018/9/21 11:06.
 */
@Component
public class JwtUtil {
    private static final String CLAIM_KEY_USER_ACCOUNT = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    //秘钥
    @Value("${jwt.secret}")
    private String secret;
    //过期时间
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 从token中获取用户username
     * @param token token
     * @return username
     */
    public String getUserAccountFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 从token中获取创建时间
     * @param token token
     * @return token的创建时间
     */
    private Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    /**
     * 获取token的过期时间
     * @param token token
     * @return token的过期时间
     */
    private Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    /**
     * 从token中获取claims
     * @param token token
     * @return claims
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 生成token的过期时间
     * @return token的过期时间
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 判断token是否过期
     * @param token token
     * @return 是否过期
     */
    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return Objects.isNull(expiration) || expiration.before(new Date());
    }

    /**
     * 生成token
     * @param userDetails userDetails实现类
     * @return 生成的token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USER_ACCOUNT, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * token 是否可刷新
     * @param token token
     * @return token是否可刷新
     */
    public Boolean canTokenBeRefreshed(String token) {
        final Date created = getCreatedDateFromToken(token);
        return !Objects.isNull(created) && !isTokenExpired(token);
    }

    /**
     * 刷新token
     * @param token token
     * @return 刷新token
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 验证token
     * @param token token
     * @param userDetails userDetails实现类
     * @return token是否正确
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        UserPo user = (UserPo) userDetails;
        final String username = getUserAccountFromToken(token);
        final Date created = getCreatedDateFromToken(token);
        return username.equals(user.getUsername())
                && !isTokenExpired(token);
    }
}
