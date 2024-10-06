package com.project.forum.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class InterlockTestController {

    @GetMapping("/google")
    public String google(){
        return "googleTest";
    }

    @GetMapping("/googleSuccess")
    public String googleSuccess(){
        return "googleTestGood";
    }

    @GetMapping("/test/login")
    public @ResponseBody String loginTest(Authentication authentication){
        System.out.println("===================================");
        System.out.println("authentication: "+authentication);
        System.out.println("authentication: "+authentication.getPrincipal());
        return "세션 정보 확인";
    }

}
