package com.example.application.service;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class UserApplicationService {
    @Autowired
    private MessageSource messageSource;
    
    public UserApplicationService() {
        // TODO 自動生成されたコンストラクター・スタブ
    }

    /** 性別のMapを生成する */
    public Map<String, Integer> getGenderMap() {
        Map<String, Integer> genderMap = new LinkedHashMap<>();
        String male = messageSource.getMessage("male", null, Locale.JAPAN);
        String female = messageSource.getMessage("female", null, Locale.JAPAN);
        genderMap.put(male, 1);
        genderMap.put(female, 2);
        return genderMap;
    }
}
