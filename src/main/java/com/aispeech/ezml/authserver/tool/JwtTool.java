package com.aispeech.ezml.authserver.tool;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * JWT工具类
 *
 * @author ZhangXi
 */
@Slf4j
public final class JwtTool {


    private static final int INIT_CLAIM_SIZE = 10;
    private static final String BASIC_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String JWT_CLAIM_KEY_USER_ID = "userId";
    public static final String JWT_CLAIM_KEY_USER_NAME = "userName";
    public static final String JWT_CLAIM_KEY_EXPIRE_DATE = "expireDate";

    /**
     * JWT Token 密钥
     */
    public static volatile String JWT_TOKEN_KEY = "ezml&auth20200618";

    /**
     * JWT Access Token 过期时间(单位:毫秒), 默认1天
     */
    public static volatile long JWT_ACCESS_TOKEN_EXPIRE_TIME = 24 * 60 * 60 * 1000;

    /**
     * JWT Refresh Token 过期时间(单位:毫秒),默认30天
     */
    public static volatile long JWT_REFRESH_TOKEN_EXPIRE_TIME = 30 * JWT_ACCESS_TOKEN_EXPIRE_TIME;

    /**
     * JWT Token 授权
     * @param userId 用戶ID
     * @param userName 用户名称
     * @param extMap 属性Map表
     * @return {@link TokenData} 对象
     */
    public static TokenData grantToken(String userId, String userName, HashMap<String, String> extMap) {
        TokenData tokenData = new TokenData();
        tokenData.setUserId(userId);
        tokenData.setUserName(userName);
        tokenData.setExtMap(extMap);
        SimpleDateFormat sdf = new SimpleDateFormat(BASIC_DATETIME_FORMAT, Locale.CHINA);

        //accessToken
        Date accessExpireDate = new Date(System.currentTimeMillis() + JWT_ACCESS_TOKEN_EXPIRE_TIME);
        JWTCreator.Builder accessBuilder = com.auth0.jwt.JWT.create()
                .withClaim(JWT_CLAIM_KEY_USER_ID, userId)
                .withClaim(JWT_CLAIM_KEY_USER_NAME, userName)
                .withClaim(JWT_CLAIM_KEY_EXPIRE_DATE, sdf.format(accessExpireDate))
                .withExpiresAt(accessExpireDate);
        if (extMap != null) {
            extMap.forEach(accessBuilder::withClaim);
        }
        String accessToken = accessBuilder.sign(Algorithm.HMAC256(JWT_TOKEN_KEY));
        tokenData.setAccessToken(accessToken);

        //refreshToken
        Date refreshExpireDate = new Date(System.currentTimeMillis() + JWT_REFRESH_TOKEN_EXPIRE_TIME);
        JWTCreator.Builder refreshBuilder = com.auth0.jwt.JWT.create()
                .withClaim(JWT_CLAIM_KEY_USER_ID, userId)
                .withClaim(JWT_CLAIM_KEY_USER_NAME, userName)
                .withClaim(JWT_CLAIM_KEY_EXPIRE_DATE, sdf.format(refreshExpireDate))
                .withExpiresAt(refreshExpireDate);
        String refreshToken = refreshBuilder.sign(Algorithm.HMAC256(JWT_TOKEN_KEY));
        tokenData.setRefreshToken(refreshToken);

        return tokenData;
    }


    /**
     * java-jwt创建Token通用方法封装
     * @param payload {@link JwtPayload} JWT默认的声明对象
     * @param claims {@link Map} 自定义声明Map集合
     * @param algorithm {@link Algorithm} 算法对象
     * @return 生成的Token字符串
     */
    public static String createToken(JwtPayload payload, Map<String, String> claims, Algorithm algorithm) {
        /*
         * header
         * alg: jwt签名算法
         * typ: jwt类型，多数默认为jwt
         * kid: (可选) Key的ID
         * payload
         * iss: jwt签发者的名称
         * sub: jwt主题
         * aud: jwt的接收者
         * exp: jwt的过期时间
         * iat: jwt签发时间
         * jti: (可选) jwt的唯一标识
         * nbf: (可选) Not Before value
         */
        JWTCreator.Builder builder = JWT.create().withIssuer(payload.getIssuer())
                .withSubject(payload.getSubject()).withAudience(payload.getAudience())
                .withExpiresAt(payload.getExpiredAt()).withIssuedAt(payload.getIssuedAt());
        // 添加字符串声明
        if(null != claims && !claims.isEmpty()) {
            claims.forEach(builder::withClaim);
        }
        return builder.sign(algorithm);
    }

    /**
     * auth0 JWT验证方法封装
     * @param issuer 签发人
     * @param token Token字符串
     * @param algorithm 算法
     * @return {@link DecodedJWT} 对象
     */
    public static DecodedJWT verifyToken(String issuer, String token, Algorithm algorithm) {
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
        return verifier.verify(token);
    }




    /**
     * JWT Token 解码
     * @param token Token 字符串
     * @return {@link TokenData} 对象
     */
    public static TokenData decodeToken(String token) {
        DecodedJWT jwt = null;
        try {
            jwt = JWT.decode(token);
        } catch (Exception e) {
            log.error("JWT Token 解码失败", e);
        }
        return getTokenDataFromDecodedJwt(jwt);
    }

    /**
     * JWT Token 验证
     * @param token Token 字符串
     * @return boolean 值
     */
    public static boolean verify(String token) {
        Algorithm algorithm = Algorithm.HMAC256(JWT_TOKEN_KEY);
        //todo 是否需要验证token的编码与格式？
        try {
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            jwtVerifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            log.error("JWT Token 验证错误", e);
            return false;
        }
    }

    /**
     * 验证并获取 {@link TokenData} 对象
     * @param token Token 字符串
     * @return {@link TokenData} 对象
     */
    public static TokenData verifyAndGetTokenData (String token) throws TokenVerifyFailedException {
        Algorithm algorithm = Algorithm.HMAC256(JWT_TOKEN_KEY);
        try {
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT jwt = jwtVerifier.verify(token);
            return getTokenDataFromDecodedJwt(jwt);
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
            throw new TokenVerifyFailedException(e);
        }
    }

    /**
     * 获取 {@link TokenData} 对象
     * @param jwt {@link DecodedJWT} 对象
     * @return {@link TokenData} 对象
     */
    private static TokenData getTokenDataFromDecodedJwt(DecodedJWT jwt) {
        if (null == jwt) {
            return null;
        }
        Map<String, Claim> claims = jwt.getClaims();
        Map<String, String> extMap = new HashMap<>(INIT_CLAIM_SIZE);
        claims.forEach((k, v) -> extMap.put(k, v.asString()));
        TokenData tokenData = new TokenData();
        tokenData.setExtMap(extMap);
        tokenData.setUserId(claims.get(JWT_CLAIM_KEY_USER_ID).asString());
        tokenData.setUserName(claims.get(JWT_CLAIM_KEY_USER_NAME).asString());
        tokenData.setAccessToken(jwt.getToken());
        //todo refreshToken?
        return tokenData;
    }

    /**
     * payload资源
     */
    @Data
    private static class JwtPayload {
        private String issuer;
        private String subject;
        private String[] audience;
        private Date expiredAt;
        private Date issuedAt;
    }

    /**
     * token相关数据封装
     */
    @Data
    public static class TokenData {
        private String userId;
        private String userName;
        private String accessToken;
        private String refreshToken;
        private Map<String, String> extMap;
    }

    /**
     * Token验证失败异常
     * @author ZhangXi
     */
    public static class TokenVerifyFailedException extends Exception {

        private static final String DEFAULT_MESSAGE = "Token验证失败";

        private String desc;

        public TokenVerifyFailedException(RuntimeException e) {
            super(e.getMessage(), e);
            this.desc = DEFAULT_MESSAGE;
        }

        public TokenVerifyFailedException(JWTVerificationException e) {
            super(e.getMessage(), e);
            this.desc = DEFAULT_MESSAGE;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

}
