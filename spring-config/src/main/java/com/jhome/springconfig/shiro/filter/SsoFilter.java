package com.jhome.springconfig.shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wangmin on 2018/9/8.
 */
public class SsoFilter extends AccessControlFilter {

    private String ssoLoginUrl;

    public SsoFilter(String ssoLoginUrl) {
        this.ssoLoginUrl = ssoLoginUrl;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        if(!subject.isAuthenticated() && !subject.isRemembered()) {
            //如果没有登录，重定向到单点登录系统
            HttpServletRequest req = (HttpServletRequest) request;
            String serverip = req.getLocalName();
            if("0:0:0:0:0:0:0:1".equals(serverip)) {
                serverip = "127.0.0.1";
            }
            String reqUrl = req.getScheme() + "://"+serverip+":" + req.getServerPort() + req.getContextPath() + WebUtils.getSavedRequest(request).getRequestURI();
            HttpServletResponse res = (HttpServletResponse) response;
            res.sendRedirect(ssoLoginUrl + "?redirectUrl="+reqUrl);
            return false;
        }
        return true;
    }
}
