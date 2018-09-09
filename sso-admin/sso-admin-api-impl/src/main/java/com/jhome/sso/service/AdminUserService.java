package com.jhome.sso.service;

import com.jhome.sso.mapper.AdminUserDao;
import com.jhome.sso.model.po.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wangmin on 2018/9/9.
 */
@Service
@Transactional
public class AdminUserService {

    @Autowired
    private AdminUserDao adminUserDao;

    public AdminUser findByUserCode(String userCode) {
        return adminUserDao.findByUserCode(userCode);
    }
}
