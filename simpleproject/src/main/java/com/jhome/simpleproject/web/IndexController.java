package com.jhome.simpleproject.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by wangmin on 2018/9/8.
 */
@Controller
public class IndexController {

    @RequestMapping("index")
    public String index(Model model, HttpSession session) {
        String username = (String) session.getAttribute("userName");
        model.addAttribute("userName", username);
        System.out.println(SecurityUtils.getSubject().getSession().getId().toString());
        return "index";
    }

    @RequestMapping("noPermission")
    @ResponseBody
    @RequiresRoles("aaa")
    public String noPermission() {
        return "noPermission";
    }
}
