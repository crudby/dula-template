package com.crud.dula.common.jwt;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.*;


/**
 * jwt 工具类
 *
 * @author crud
 * @date 2020/10/9
 */
@Slf4j
public class JwtUtil {

    private static final String SECRET = "KeidLe!ddkeIkeng*dke^dkvmg$kdj++=#jdneiIDKEidkLEokEkdieI9028J#kd83#kd93_";

    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    private static final long EXPIRE_SECOND = 30 * 60;

    /**
     * 生成token 默认有效时间为 30*60s
     *
     * @param claims claims
     * @return token
     */
    public static String createJwt(Map<String, Object> claims) {
        return createJwt(claims, EXPIRE_SECOND);
    }

    /**
     * 生成token 自定义有效时间 秒(s)
     *
     * @param claims claims
     * @param expire expire
     * @return token
     */
    public static String createJwt(Map<String, Object> claims, long expire) {
        Date nowDate = new Date();
        Date exprireDate = Date.from(Instant.now().plusSeconds(expire));
        return Jwts.builder()
                .header().add("typ", "JWT").add("alg", "HS256")
                .and()
                .claims().add(claims)
                .and()
                .issuedAt(nowDate)
                .expiration(exprireDate)
                .signWith(KEY)
                .compact();
    }

    /**
     * 解析Claims
     *
     * @param token token
     * @return claims
     */
    public static Claims parseJwt(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .verifyWith(KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Token invalided.");
        }
        return claims;
    }

    /**
     * 检查token是否是否被伪造
     *
     * @param token 令牌
     * @return 有效true 无效false
     */
    public static boolean isSigned(String token) {
        return Jwts.parser()
                .verifyWith(KEY)
                .build()
                .isSigned(token);
    }

    /**
     * 手动解析Base64编码的Token payload段
     *
     * @param token token内容
     * @return 返回解析出来的参数数组
     */
    public static Map<String, Object> decodeBase64Token(String token) {

        String[] data = token.split("[.]");
        if (data.length == 3) {
            byte[] decode = Base64.getDecoder().decode(data[1]);
            String payload = new String(decode);
            return JSON.parseObject(payload);
        } else {
            throw new RuntimeException("认证失败[Token解析Base64失败]");
        }
    }

    /**
     * 根据当前token生成新token
     *
     * @param oldToken oldToken
     * @return token
     */
    public static String createNewToken(String oldToken) {
        Map<String, Object> claims = decodeBase64Token(oldToken);
        claims.remove(Claims.ISSUED_AT);
        claims.remove(Claims.EXPIRATION);
        return createJwt(claims);
    }

    /**
     * 获取jwt失效时间
     */
    public static Date getExpiration(String token) {
        return parseJwt(token).getExpiration();
    }

    /**
     * 解析token获取值
     */
    public static Optional<Object> getSafeValue(String token, String key) {
        try {
            return Optional.ofNullable(parseJwt(token).get(key));
        } catch (ExpiredJwtException e) {
            log.warn("过期的token：{}", token);
        } catch (Exception e) {
            log.error("解析token失败：{}", token);
        }
        return Optional.empty();
    }

    /**
     * 直接获取token信息
     *
     * @param token token
     * @param key   key
     * @return obj
     */
    public static Optional<Object> getUnSafeValue(String token, String key) {
        try {
            return Optional.ofNullable(decodeBase64Token(token).get(key));
        } catch (Exception e) {
            log.error("解析token失败：{}", token);
            return Optional.empty();
        }
    }

    /**
     * 获取当前有效时间 秒(s)
     */
    public static long getLiveTime(String token) {
        if (isExpired(token)) {
            return (getExpiration(token).getTime() - new Date().getTime()) / 1000;
        } else {
            return 0;
        }
    }

    /**
     * 验证token是否在保护时长内有效
     *
     * @param token       令牌
     * @param protectTime 保护时长 秒
     * @return true:未过期   false:过期
     */
    public static boolean isExpired(String token, int protectTime) {
        //手动解析token
        Map<String, Object> claims = decodeBase64Token(token);
        //获取token过期时间
        Integer exp = (Integer) claims.get(Claims.EXPIRATION);
        //判断时间是否在有效期+保护期
        return new Date(exp * 1000L + protectTime * 1000L).after(new Date());
    }

    /**
     * 验证token是否有效
     *
     * @param token 令牌
     * @return true:未过期   false:过期
     */
    public static boolean isExpired(String token) {
        return isExpired(token, 0);
    }

    /**
     * 在保护时间
     *
     * @param token       token
     * @param protectTime protectTime
     * @return bool
     */
    public static boolean inProtectedTime(String token, int protectTime) {
        Map<String, Object> claims = decodeBase64Token(token);
        //获取token过期时间
        Integer exp = (Integer) claims.get(Claims.EXPIRATION);
        Date now = new Date();
        return new Date(exp * 1000L).before(now) && now.before(new Date(exp * 1000L + protectTime * 1000L));
    }

}
