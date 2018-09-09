package com.jhome.sso.model.po;

import java.io.Serializable;

/**
 * Created by wangmin on 2018/9/9.
 */
public class AdminUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户编码
     */
    private String userCode;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 是或有效
     */
    private String valid;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }
}
