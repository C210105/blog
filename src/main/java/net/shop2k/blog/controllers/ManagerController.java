package net.shop2k.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.log4j.Log4j2;
import net.shop2k.blog.entitys.Admin;
import net.shop2k.blog.repositorys.AdminRepository;
import net.shop2k.blog.services.AdminService;
import net.shop2k.blog.services.ManagerService;

@Log4j2
@Controller
@RequestMapping("/manager/blog")
public class ManagerController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private AdminRepository adminRepository;
    /*
     * ログインページ
     */
    @GetMapping("/login")
    // @PreAuthorize("hasRole('ROLE_MANAGER')")
    public String showLoginManager(){
        return "html/manager/login.html";
    }

    /*
     * index ページを表示
     */
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/index")
    // @PreAuthorize("hasRole('ROLE_MANAGER')")
    public String showIndexManager(){
        return "html/manager/index.html";
    }

    /*
     * ログイン出来たら
     * index ページを表示
     */
    @PostMapping("/login")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public String getIndexManager(){
        log.info("MANAGERとしてログインできた");
        return "redirect:/manager/blog/index";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/set-admin")
    public String showSetAdmin(Model model){

        List <Admin> allAdmin = adminService.getAllAdmin();
        model.addAttribute("allAdmin", allAdmin);
        log.info("set-adminを表示");
        return "html/manager/set-admin";
    }

    /*
     * 申請フォームを不-許可する
     */
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping("/set-admin/approve-registration")
    public String approveRegistration(@RequestParam("username") String username, RedirectAttributes redirectAttributes){

        
        // Admin admin = adminRepository.findByUsername(username);
        // admin.setSetEnabled(true);
        // adminRepository.save(admin);
        
        managerService.setAdmin(username);
        log.info(username + "申請フォームは許可した");
        redirectAttributes.addFlashAttribute("successMessageApprove", "Cấp quyền ADMIN thành công");
        return "redirect:/manager/blog/set-admin";
    }

    /*
     * ADMINを削除機能
     */
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping("delete-admin/{id}")
    public String deleteAdmin(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
            managerService.deleteAdmin(id);
        try{
            log.info("ADMIN " + id + " 削除した");
            redirectAttributes.addFlashAttribute("successMessageDeleteAdmin", "Xóa ADMIN thành công");
            return "redirect:/manager/blog/set-admin";
        }catch(Exception e){
            log.info("エラー");
            return "/";
        }
    }
}
