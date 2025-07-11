package com.example.controller;

import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.application.service.UserApplicationService;
import com.example.domain.user.model.MUser;
import com.example.domain.user.service.UserService;
import com.example.form.GroupOrder;
import com.example.form.SignupForm;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user")
@Slf4j
public class SignupController {
    @Autowired
    private UserApplicationService userApplicationService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    /** ユーザー登録画面を表示 */
    @GetMapping("/signup")
    public String getSignup(Model model, @ModelAttribute SignupForm form) {
        // 性別を取得
        Map<String, Integer> genderMap = userApplicationService.getGenderMap();
        model.addAttribute("genderMap", genderMap);

        // ユーザ登録画面に遷移
        return "user/signup";
    }

    /** ユーザー登録処理 */
    @PostMapping("/signup")
    public String postSignup(Model model, @ModelAttribute @Validated(GroupOrder.class) SignupForm form,
            BindingResult bindingResult) {
        //バインドエラーチェック
        if (bindingResult.hasErrors()) {
            // NG|ユーザー登録画面へ戻る
            return getSignup(model, form);
        }

        log.info(form.toString());

        MUser user = modelMapper.map(form, MUser.class);

        userService.signup(user);

        // ログイン画面にリダイレクト
        return "redirect:/login";
    }

    /**
     * データベース関連の例外処理
     * */
    @ExceptionHandler(DataAccessException.class)
    public String dataAccessExcepionHandler(DataAccessException e, Model model) {
        // 空文字をセット
        model.addAttribute("error", "");
        
        // メッセージをModelに登録
        model.addAttribute("message", "SignupControllerで例外が発生しました");
        
        // HTTPのエラーコード（500）をModelに登録
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
        
        return "error";
    }
    
    /**
     * その他の例外処理*/
    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e, Model model) {
     // 空文字をセット
        model.addAttribute("error", "");
        
        // メッセージをModelに登録
        model.addAttribute("message", "SignupControllerで例外が発生しました");
        
        // HTTPのエラーコード（500）をModelに登録
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
        
        return "error";
    }
}
