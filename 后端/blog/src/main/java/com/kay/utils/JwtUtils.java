package com.kay.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JwtUtils {

    //设置token过期时间
    private static final Long EXP_TIME = 1000 * 60 * 60 * 2L;
    //定义签名
    private static final String SIGNATURE = "BornThisWay";

    /**
     * 生成token
     *
     * @param uId 用户id
     * @return token字符串
     */
    public static String generateToken(String uId) {
        return JWT.create()
                .withJWTId(uId)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXP_TIME))
                .sign(Algorithm.HMAC256(SIGNATURE));
    }

    /**
     * 验证token是否合法
     *
     * @param token token字符串
     */
    public static void verifyToken(String token) {
        JWTVerifier build = JWT.require(Algorithm.HMAC256(SIGNATURE)).build();
        build.verify(token);
    }

    /**
     * 解析token
     *
     * @param token token字符串
     * @return 用户id
     */
    public static String analysisToken(String token) {
        JWTVerifier build = JWT.require(Algorithm.HMAC256(SIGNATURE)).build();
        return build.verify(token).getId();
    }
}
