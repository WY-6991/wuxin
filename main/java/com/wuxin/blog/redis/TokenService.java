package com.wuxin.blog.redis;

import com.wuxin.blog.constant.Constants;
import com.wuxin.blog.mode.UserAccount;
import com.wuxin.blog.shiro.redis.RedisCache;
import com.wuxin.blog.utils.string.StringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: wuxin001
 * @Date: 2022/01/13/10:37
 * @Description:
 */
@Component
public class TokenService {

    // 令牌自定义标识
    @Value("${token.header}")
    private String header;

    // 令牌秘钥
    @Value("${token.secret}")
    private String secret;

    // 令牌有效期（默认30分钟）
    @Value("${token.expireTime}")
    private int expireTime;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    @Autowired
    private RedisCache redisCache;

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public UserAccount getUserAccount(HttpServletRequest request)
    {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token))
        {
            try
            {
                Claims claims = parseToken(token);
                // 解析对应的权限以及用户信息
                String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
                String userKey = getTokenKey(uuid);
                UserAccount user = (UserAccount) redisCache.get(userKey);
                return user;
            }
            catch (Exception e)
            {
            }
        }
        return null;
    }

    /**
     * 设置用户身份信息
     */
    // public void setUserAccount(UserAccount UserAccount)
    // {
    //     if (StringUtils.isNotNull(UserAccount) && StringUtils.isNotEmpty(UserAccount.getToken()))
    //     {
    //         refreshToken(UserAccount);
    //     }
    // }

    /**
     * 删除用户身份信息
     */
    public void delUserAccount(String token)
    {
        if (StringUtils.isNotEmpty(token))
        {
            String userKey = getTokenKey(token);
            redisCache.remove(userKey);
        }
    }

    /**
     * 创建令牌
     *
     * @param UserAccount 用户信息
     * @return 令牌
     */
    // public String createToken(UserAccount UserAccount)
    // {
    //     String token = IdUtils.fastUUID();
    //     UserAccount.setToken(token);
    //     setUserAgent(UserAccount);
    //     refreshToken(UserAccount);
    //
    //     Map<String, Object> claims = new HashMap<>();
    //     claims.put(Constants.LOGIN_USER_KEY, token);
    //     return createToken(claims);
    // }

    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     *
     * @param UserAccount
     * @return 令牌
     */
    // public void verifyToken(UserAccount UserAccount)
    // {
    //     long expireTime = UserAccount.getExpireTime();
    //     long currentTime = System.currentTimeMillis();
    //     if (expireTime - currentTime <= MILLIS_MINUTE_TEN)
    //     {
    //         refreshToken(UserAccount);
    //     }
    // }

    // /**
    //  * 刷新令牌有效期
    //  *
    //  * @param UserAccount 登录信息
    //  */
    // public void refreshToken(UserAccount UserAccount)
    // {
    //     UserAccount.setLoginTime(System.currentTimeMillis());
    //     UserAccount.setExpireTime(UserAccount.getLoginTime() + expireTime * MILLIS_MINUTE);
    //     // 根据uuid将UserAccount缓存
    //     String userKey = getTokenKey(UserAccount.getToken());
    //     redisCache.setCacheObject(userKey, UserAccount, expireTime, TimeUnit.MINUTES);
    // }

    /**
     * 设置用户代理信息
     *
     * @param UserAccount 登录信息
     */
    // public void setUserAgent(UserAccount UserAccount)
    // {
    //     UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
    //     String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
    //     UserAccount.setIpaddr(ip);
    //     UserAccount.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
    //     UserAccount.setBrowser(userAgent.getBrowser().getName());
    //     UserAccount.setOs(userAgent.getOperatingSystem().getName());
    // }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    // private String createToken(Map<String, Object> claims)
    // {
    //     String token = Jwts.builder()
    //             .setClaims(claims)
    //             .signWith(SignatureAlgorithm.HS512, secret).compact();
    //     return token;
    // }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token)
    {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token)
    {
        Claims claims = parseToken(token);
        return claims.getSubject();
    }

    /**
     * 获取请求token
     *
     * @param request
     * @return token
     */
    private String getToken(HttpServletRequest request)
    {
        String token = request.getHeader(header);
        if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX))
        {
            token = token.replace(Constants.TOKEN_PREFIX, "");
        }
        return token;
    }

    private String getTokenKey(String uuid)
    {
        return Constants.LOGIN_TOKEN_KEY + uuid;
    }
}
