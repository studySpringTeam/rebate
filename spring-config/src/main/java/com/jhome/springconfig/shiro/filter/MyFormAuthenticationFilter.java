package com.jhome.springconfig.shiro.filter;

import com.jhome.springconfig.shiro.spring.DemoRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangmin on 2018/9/8.
 */
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {

    private static final Logger log = LoggerFactory.getLogger(MyFormAuthenticationFilter.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String KEY_USER_PREFIX = "shiro_redis_user:";

    private boolean ssoSystem;

    public void setSsoSystem(boolean ssoSystem) {
        this.ssoSystem = ssoSystem;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response) || ssoSystem) {
            if (isLoginSubmission(request, response) || ssoSystem) {
                if (log.isTraceEnabled()) {
                    log.trace("Login submission detected.  Attempting to execute login.");
                }
                return executeLogin(request, response);
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("Login page view.");
                }
                //allow them to see the login page ;)
                return true;
            }
        } else {
            if (log.isTraceEnabled()) {
                log.trace("Attempting to access a path which requires authentication.  Forwarding to the " +
                        "Authentication url [" + getLoginUrl() + "]");
            }

            saveRequestAndRedirectToLogin(request, response);
            return false;
        }
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
                                     ServletResponse response) throws Exception {
        Subject currentUser = SecurityUtils.getSubject();
        DemoRealm.ShiroUser shiroUser = (DemoRealm.ShiroUser) subject.getPrincipal();
        Session session = currentUser.getSession();
        session.setAttribute("userCode", shiroUser.getLoggerinName());
        session.setAttribute("userName", shiroUser.getName());

        if(ssoSystem) {
            String nowSessionId = currentUser.getSession().getId().toString();
            //user的信息存入redis，键为sessionId
//            redisTemplate.opsForValue().set(KEY_USER_PREFIX+nowSessionId, shiroUser.getLoggerinName(), 60*30, TimeUnit.SECONDS);
            //存一个键为user信息，值为sessionId的，方便踢出用户
            redisTemplate.opsForValue().set(KEY_USER_PREFIX+shiroUser.getLoggerinName(), nowSessionId, 60*30, TimeUnit.SECONDS);
        }

        //清理原先的地址
        if(!ssoSystem) {
            WebUtils.getAndClearSavedRequest(request);
            WebUtils.redirectToSavedRequest(request, response, "index");
        } else {
            String redirectUrl = WebUtils.getCleanParam(request, "redirectUrl");
            WebUtils.getAndClearSavedRequest(request);
            if(redirectUrl!=null && !"".equals(redirectUrl)) {
                WebUtils.redirectToSavedRequest(request, response, getSuccessUrl()+"?redirectUrl="+redirectUrl);
            } else {
                WebUtils.redirectToSavedRequest(request, response, getSuccessUrl());
            }
        }
        return false;
    }

    @Override
    protected UsernamePasswordToken createToken(ServletRequest request, ServletResponse response) {
        String username;
        String password;

//        String token = null;
//        if(ssoSystem && WebUtils.getSavedRequest(request) != null) {
//            //获得token
//            String requestUrl = WebUtils.getSavedRequest(request).getRequestUrl();
//            requestUrl = (requestUrl.indexOf("token=")==-1)?null:requestUrl.substring(requestUrl.indexOf("token=")+6);
//            token = requestUrl==null?null:requestUrl.split("&")[0].trim();
//        }
//        if(ssoSystem && token != null && !"".equals(token)) {
//            username = redisTemplate.opsForValue().get(KEY_USER_PREFIX+token);
//            password = "123456";
//        } else {
            username = getUsername(request);
            password = getPassword(request);
//        }
        boolean rememberMe = isRememberMe(request);
        String host = getHost(request);
        password = password==null?"":new Md5Hash(password, username, 2).toString();
        return new UsernamePasswordToken(username, password, rememberMe, host);
    }
}
