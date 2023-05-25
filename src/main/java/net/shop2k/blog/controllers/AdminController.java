package net.shop2k.blog.controllers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.shop2k.blog.entitys.Admin;
import net.shop2k.blog.services.AdminService;

@Controller
@RequestMapping (path = "/admin/blog")
public class AdminController {
    
    @Autowired
    private AdminService adminService;

    @GetMapping("/login")
    public String loginAdmin(Model model){
        return "html/admin/login.html";
    }

    @GetMapping("/registeradmin")
    public String registerAdmin(){
        return "html/admin/register.html";
    }

    @PostMapping("/registeradmin")
    public String registerAdmin(@RequestParam("username") String username, @RequestParam("password") String passwrod, Model model){
        
        /*
         * ADMIN を生成する
         */
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(passwrod);
        admin.setSetEnabled(true);
        admin.setRole("ROLE_ADMIN");

        //エラーをチェックする
        try{
            adminService.registerAdmin(admin);
            model.addAttribute("susccessMessage", "Đăng kí thành công");
            return "html/admin/register.html";
        }catch (IllegalArgumentException e){
            model.addAttribute("error", e.getMessage());
            return "html/admin/register.html";
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/index")
    public String adminlogin(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    
        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        model.addAttribute("roles", roles);
        return "html/admin/index.html";
    }
}
