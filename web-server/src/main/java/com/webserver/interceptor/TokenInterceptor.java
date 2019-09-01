//package com.vivedu.cloud.interceptor;
//
//import com.vivedu.cloud.dao.UserLoginTokenMapper;
//import com.vivedu.cloud.pojo.UserLoginToken;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.PrintWriter;
//import java.util.Date;
//
//public class TokenInterceptor implements HandlerInterceptor {
//    @Autowired
//    private UserLoginTokenMapper TokenMapper;
//
//    //提供查询
//    @Override
//    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object, Exception exception) {
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object, ModelAndView modelAndView) {
//    }
//
//    @Override
//    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
//        //普通路径放行    本地接口测试时候，运行swagger不用登录即可访问
//        if ("/swagger-ui.html".equals(httpServletRequest.getRequestURI())
//                || "/users/login".equals(httpServletRequest.getRequestURI())) {
//            return true;
//        }
//        //权限路径拦截
//        httpServletResponse.setCharacterEncoding("UTF-8");
//        PrintWriter resultWriter = httpServletResponse.getWriter();
//        final String headerToken = httpServletRequest.getHeader("XW-Token");
//        //判断请求信息
//        if (null == headerToken || headerToken.trim().equals("")) {
//            resultWriter.write("No token saved,Please login");
//            return false;
//        }
//        //解析Token信息
//        try {
//            Claims claims = Jwts.parser().setSigningKey("viveUserSign").parseClaimsJws(headerToken).getBody();
//            String tokenUserId = (String) claims.get("viveUserId");
//            int itokenUserId = Integer.parseInt(tokenUserId);
//            //根据客户Token查找数据库Token
//            UserLoginToken myToken = TokenMapper.findTokenByUserId(itokenUserId);
//
//            //数据库没有Token记录
//            if (null == myToken) {
//                resultWriter.write("我没有你的token？,需要登录");
//                return false;
//            }
//            //数据库Token与客户Token比较
//            if (!headerToken.equals(myToken.getToken())) {
//                resultWriter.write("你的token修改过？,需要登录");
//                return false;
//            }
//            //判断Token过期
//            Date tokenDate = claims.getExpiration();
//            int chaoshi = (int) (new Date().getTime() - tokenDate.getTime()) / 1000;
//            //1天token过期
//            if (chaoshi > 60 * 60 * 24) {
//                resultWriter.write("你的token过期了？,需要登录");
//                return false;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            resultWriter.write("Token is not correct,Please Login");
//            return false;
//        }
//        //最后才放行
//        return true;
//    }
//}