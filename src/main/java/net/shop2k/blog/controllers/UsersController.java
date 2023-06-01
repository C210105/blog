package net.shop2k.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.shop2k.blog.entitys.User;
import net.shop2k.blog.services.UserService;

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
    public String registerUser(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        // Tạo đối tượng User từ thông tin đăng ký
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setSetEnabled(true);
        user.setRole("ROLE_ADMIN");
        
        // Lưu thông tin người dùng vào cơ sở dữ liệu
        try {
            userService.registerUser(user);
            model.addAttribute("susccessMessage", "Đăng kí thành công");
            // return "redirect:/blog/login"; // Chuyển hướng sau khi đăng ký thành công
            return "html/users/register.html";
        } catch (IllegalArgumentException e){
            model.addAttribute("error", e.getMessage());
            return "html/users/register.html";
        }
    }

}
