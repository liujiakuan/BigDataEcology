package com.webserver.utils;

import com.alibaba.fastjson.JSON;
import com.webserver.pojo.ViveUser;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreatToken {
    //生成Token信息方法（根据有效的用户信息）TODO 本地测试时候使用，实际开发需要通过请求验证服务器token验证
    public static String creatToken(ViveUser user, Date date) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT") // 设置header
                .setHeaderParam("alg", "HS256").setIssuedAt(date) // 设置签发时间
                .setExpiration(new Date(date.getTime() + 1000 * 60 * 60 * 24 * 3))
                .claim("viveUserId", String.valueOf(user.getId())) // 设置内容
                .setIssuer("viveUser")// 设置签发人
                .signWith(signatureAlgorithm, "viveUserSign"); // 签名，需要算法和key
        return builder.compact();
    }

    //从远端验证服务器获取token信息 Post请求
    public static String getTokenFromAuthenticationServer(ViveUser user, Date date) {
        Map<String, String> map = new HashMap<>();
        map.put("name", user.getName());
        map.put("password", user.getPassword());
        String url = "http://localhost:8081/spring/xxx";//TODO 远端服务器请求url
        //设置请求头信息
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        //设置body部分
        HttpEntity<String> entity = new HttpEntity<>(JSON.toJSONString(map), headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        return result.getBody();
    }


}
