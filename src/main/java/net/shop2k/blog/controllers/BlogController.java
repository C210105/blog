package net.shop2k.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping(path = "/blog")
public class BlogController {
    
    @GetMapping("/login")
    public String showLogin(){
        log.info("ログインページを表示");
        return "html/login";
    }

    @GetMapping("/logout")
    public String logoutAdmin(HttpServletRequest request, HttpServletResponse response, Model model){

        // session を削除する
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                cookie.setMaxAge(0); //cokkies:期限切れになり、削除する
                cookie.setValue(null); //cokkies の値: nullに設定する
                cookie.setPath("/");
                response.addCookie(cookie); //クライアント側からcookiesを削除される
            }
        }
        log.info("ログアウトできた");
        return "redirect:/blog/login";
    }
}
