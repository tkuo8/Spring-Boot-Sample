package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class LoginController {

    public LoginController() {
        // TODO 自動生成されたコンストラクター・スタブ
    }

    /** ログイン画面を表示 */
    @GetMapping("/login")
    public String getLogin() {
        return "login/login";
    }
    
    /** ユーザー一覧画面にリダイレクト */
    @PostMapping("/login")
    public String postLogin() {
        return "redirect:/user/list";
    }
}
