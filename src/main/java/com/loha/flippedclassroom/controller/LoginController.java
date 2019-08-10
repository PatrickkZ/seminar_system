package com.loha.flippedclassroom.controller;

import com.loha.flippedclassroom.service.GeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 控制登录界面
 *
 * @author zhoujian
 * @date 2018/12/15
 */
@Controller
public class LoginController {

    private final GeneralService generalService;

    @Autowired
    LoginController(GeneralService generalService){
        this.generalService=generalService;
    }

    @GetMapping(value = {"/login","/"})
    public String login(){
        return "login";
    }

    @GetMapping(value = "/forgetPwd")
    public String forgetPassword(){
        return "forgetPwd";
    }

    @PostMapping(value = "/password")
    @ResponseBody
    public ResponseEntity sendPassword(String account) throws Exception{
        generalService.sendSimpleEmail(account);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
}
