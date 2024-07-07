package com.itheima;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.itheima.utils.JwtUtil.parseToken;

public class JwtTest {

    //@Test
    public void jwt(){
        /**
         * 此函数用于生成一个包含特定声明的JWT（JSON Web Token）并打印出来。
         * JWT包含了用户ID和用户名，并设置了一个1分钟的过期时间。
         *
         * @param claims 一个Map对象，存储JWT中的声明，键为字符串，值为任意类型。
         *   - "id" 键用于存放用户ID，示例值为1。
         *   - "username" 键用于存放用户名，示例值为"zzw"。
         *
         * @return 生成的JWT字符串，通过JWT.create()构建并签名。
         */
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("username", "zzw");

        String token = JWT.create()
                .withClaim("user", claims) // 添加一个名为"user"的自定义声明，值为claims
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60)) // 设置过期时间为当前时间后1分钟
                .sign(Algorithm.HMAC256("ZZW")); // 使用HMAC256算法签名生成的JWT

        System.out.println(token); // 打印生成的JWT令牌
    }
   @Test
    public void tParse() {
        // 定义一个JWT令牌字符串，该字符串包含用户信息和过期时间等。
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbGFpbXMiOnsiaWQiOjExLCJ1c2VybmFtZSI6Inp6dzEzOSJ9LCJleHAiOjE3MjAxNjMwOTd9.seKj--FtwO6FGKTRCQppMH2CpT3pbBIpMyeJ5WlcNMU";

        // 创建JWT验证器，指定使用的算法为HMAC256，并提供相应的秘钥。
        JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256("itheima")).build();

        // 使用验证器验证JWT令牌的有效性，并返回解码后的JWT对象。
        DecodedJWT decodedJWT = jwtVerifier.verify(token);

        // 获取JWT中的声明信息，这些声明包含了JWT的元数据和用户信息等。
        Map<String, Claim> claimMap=decodedJWT.getClaims();

        // 打印JWT中的用户信息，这里假设用户信息存储在名为"user"的声明中。
        System.out.println(claimMap);
        System.out.println(claimMap.get("claims"));

    }
    //@Test
    public void testGen() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("username", "张三");
        //生成jwt的代码
        String token = JWT.create()
                .withClaim("user", claims)//添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000))//添加过期时间
                .sign(Algorithm.HMAC256("itheima"));//指定算法,配置秘钥

        System.out.println(token);

    }
  // @Test
    public void testParse() {
        //定义字符串,模拟用户传递过来的token
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbGFpbXMiOnsiaWQiOjExLCJ1c2VybmFtZSI6Inp6dzEzOSJ9LCJleHAiOjE3MjAxNjMwOTd9.seKj--FtwO6FGKTRCQppMH2CpT3pbBIpMyeJ5WlcNMU";

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("itheima")).build();

        DecodedJWT decodedJWT = jwtVerifier.verify(token);//验证token,生成一个解析后的JWT对象
        Map<String, Claim> claims = decodedJWT.getClaims();
        Map<String, Object> map = parseToken(token);
        System.out.println((String)map.get("username"));

        System.out.println(claims.get("username"));

        //如果篡改了头部和载荷部分的数据,那么验证失败
        //如果秘钥改了,验证失败
        //token过期
    }
  //  @Test
    public void generateRandomCode() {
        String captcha = "";
        int length = 4;

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            // 随机选择是使用数字还是大写字母
            int choice = random.nextInt(2);
            if (choice == 0) {
                // 生成数字
                captcha += String.valueOf(random.nextInt(10));
            } else {
                // 生成大写字母
                captcha += (char) (random.nextInt(26) + 'A');
            }
        }
        System.out.println("验证码：" + captcha);
    }
}
