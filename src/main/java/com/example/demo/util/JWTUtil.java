package com.example.demo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JWTUtil {

    /**
     * JWT 过期时间（分钟）
     */
    private Long ttl;

    /**
     * JWT 加密密钥
     */
    private String key;

    /**
     * 生成 token
     *
     * @param audience 用户
     * @return JsonWebToken
     */
    public String createJWT(String audience) {
        String uuid = ToolUtil.generalUUID();
        byte[] key = ToolUtil.getBase64(this.key);
        long ttlMillis = this.ttl * 60 * 1000L;
        Date date = new Date(System.currentTimeMillis() + ttlMillis);
        return JWT.create()
                .withJWTId(uuid)
                .withAudience(audience)
                .withExpiresAt(date)
                .sign(Algorithm.HMAC256(key));
    }

    /**
     * 解析 token
     *
     * @param jwt JsonWebToken
     * @return Object token 信息
     */
    public DecodedJWT parseJWT(String jwt) {
        byte[] key = ToolUtil.getBase64(this.key);
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(key)).build();
        return verifier.verify(jwt);
    }

    /**
     * token 是否失效
     *
     * @param claims 解密后的 token 信息
     * @return boolean
     */
    public Boolean isJWTExpired(DecodedJWT claims) {
        return claims.getExpiresAt().before(new Date());
    }

}
