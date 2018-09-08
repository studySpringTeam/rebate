package com.jhome.sso.web.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wangmin on 2018/9/8.
 */
@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login(String redirectUrl, Model model) {
        model.addAttribute("redirectUrl", redirectUrl);
        return "login";
    }
}
