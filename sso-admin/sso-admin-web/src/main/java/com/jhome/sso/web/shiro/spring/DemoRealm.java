package com.jhome.sso.web.shiro.spring;

import com.jhome.sso.model.po.AdminUser;
import com.jhome.sso.service.AdminUserService;
import com.jhome.utils.shiro.exception.PasswordEmptyException;
import com.jhome.utils.shiro.exception.UsernameEmptyException;
import com.jhome.utils.shiro.spring.CommonRealm;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wangmin on 2018/9/9.
 */
public class DemoRealm extends CommonRealm {

    @Autowired
    private AdminUserService adminUserService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole("admin");
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String username = token.getUsername();
        if (username == null || "".equals(username)) {
            throw new UsernameEmptyException(
                    "用户名为空");
        }
        if (token.getPassword() == null || "".equals(token.getPassword())){
            throw new PasswordEmptyException("密码为空");
        }
        AdminUser adminUser = adminUserService.findByUserCode(username);
        if(adminUser == null){
            throw new UnknownAccountException(username + "不存在");
        }
        return new SimpleAuthenticationInfo(new ShiroUser(username, adminUser.getUserName()), adminUser.getPassword(), getName());
    }
}
