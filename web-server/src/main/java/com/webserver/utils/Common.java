package com.webserver.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Common {
//    public static JSONObject commonPost(String url, String s, Object o) {
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        JSONObject map = new JSONObject();
//        map.put(s, o);
//        HttpEntity<String> request = new HttpEntity<>(map.toString(), headers);
//        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
//        return JSONObject.parseObject(response.getBody());
//    }

    public static JSONObject commonPost(String url, JSONObject jsonObject) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        return JSONObject.parseObject(response.getBody());
    }

    public static JSONObject commonGet(String url) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return JSONObject.parseObject(response.getBody());
    }
}
