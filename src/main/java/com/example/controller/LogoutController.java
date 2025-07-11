package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
public class LogoutController {

    /** ログイン画面にリダイレクト */
    @PostMapping("/logout")
    public String postLogout(@RequestBody String entity) {
        //TODO: process POST request
        log.info("ログアウト");
        
        return "redirect:/login";
    }
    
}
