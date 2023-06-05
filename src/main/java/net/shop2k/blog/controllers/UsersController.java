package net.shop2k.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.log4j.Log4j2;
import net.shop2k.blog.entitys.User;
import net.shop2k.blog.services.UserService;

@Log4j2
@Controller
@RequestMapping(path = "/blog")
public class UsersController {
    
    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String loginUser(){
        return "html/users/login.html";
    }

    @GetMapping("/register")
    public String registerUser(){
        return "html/users/register.html";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("nickName") String nickName, Model model) {
        // Tạo đối tượng User từ thông tin đăng ký
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setNickName(nickName);
        // user.setSetEnabled(true);
        user.setRole("ROLE_ADMIN");
        
        // Lưu thông tin người dùng vào cơ sở dữ liệu
        try {
            User registeredUser = userService.registerUser(user);
            userService.sendConfirmationEmail(registeredUser);
            model.addAttribute("susccessMessage", "Nhập mã xác nhận đã được gửi đến trong email");
            log.info("このEmailは使える");
            return "html/users/confiramationcode.html";
        } catch (IllegalArgumentException e){
            model.addAttribute("error", e.getMessage());
            log.info("エラー：このemailは存在してる");
            return "html/users/register.html";
        }
    }

    @PostMapping("/register/confirm")
    public String confirmRegistration(@RequestParam("code") String confirmationCode, Model model){
        try{
            userService.confirmRegistration(confirmationCode);
            model.addAttribute("successMessage", "Xác nhận đăng kí thành công");
            log.info("Emailで承認できた");
            return "html/users/login.html";
        } catch(IllegalArgumentException e){
            model.addAttribute("errorMessage", e.getMessage());
            log.info("Emailで承認できなかった");
            return "html/users/confiramationcode.html";
        }
    }

}
