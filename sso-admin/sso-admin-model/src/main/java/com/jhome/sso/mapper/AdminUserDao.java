package com.jhome.sso.mapper;

import com.jhome.sso.model.po.AdminUser;

/**
 * Created by wangmin on 2018/9/9.
 */
public interface AdminUserDao {

    AdminUser findByUserCode(String userCode);
}
