package com.jhome.springconfig.shiro.filter;

import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by wangmin on 2018/9/8.
 */
public class MyLogoutFilter extends LogoutFilter {

    private static final Logger log = LoggerFactory.getLogger(MyLogoutFilter.class);

    private boolean ssoSystem;

    private String ssoLogoutUrl;

    public void setSsoSystem(boolean ssoSystem) {
        this.ssoSystem = ssoSystem;
    }

    public void setSsoLogoutUrl(String ssoLogoutUrl) {
        this.ssoLogoutUrl = ssoLogoutUrl;
    }

    @Override
    protected String getRedirectUrl(ServletRequest request, ServletResponse response, Subject subject) {
        if(ssoSystem) {
            return getRedirectUrl();
        } else {
            return ssoLogoutUrl;
        }
    }
}
