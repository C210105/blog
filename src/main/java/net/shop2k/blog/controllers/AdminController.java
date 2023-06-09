package net.shop2k.blog.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import net.shop2k.blog.entitys.Admin;
import net.shop2k.blog.entitys.Articles;
import net.shop2k.blog.entitys.Categorys;
import net.shop2k.blog.services.AdminService;
import net.shop2k.blog.services.ArticlesService;
import net.shop2k.blog.services.CategorysService;

@Log4j2
@Controller
@RequestMapping(path = "/admin/blog")
public class AdminController {

    @Autowired
    ArticlesService articlesService;

    @Autowired
    AdminService adminService;

    @Autowired
    CategorysService categorysService;

    /*
     * ログイン
     */
    @GetMapping("/login")
    public String loginAdmin(Model model) {
        return "html/admin/login.html";
    }

    /*
     * login
     */
    @PostMapping("/login")
    public String getAdmin(RedirectAttributes redirectAttributes){
        log.info("ログインできた");
        return "redirect:/admin/blog/index";
    }
        
        
    // }
    /*
     * ログアウト
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
        return "redirect:/admin/blog/login";
    }

    @GetMapping("/registeradmin")
    public String registerAdmin() {
        return "html/admin/register.html";
    }

    @GetMapping("/error")
    public String loginError() {
        return "html/error.html";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/index")
    public String adminlogin(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        model.addAttribute("roles", roles);
        return "html/admin/index.html";
    }

    /*
     * Articles: CRUDのAPI 機能
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/articles/create")
    public String createArticles(Model model) {
        List<Articles> allArticles = articlesService.ggetAllArticlesExceptLatest();
        List<Categorys> allCategorys = categorysService.findByAllCategorys();
        model.addAttribute("allCategorys", allCategorys);
        model.addAttribute("allArticles", allArticles);
        return "html/admin/createarticles.html";
    }

    /*
     * 記事を登録する
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/articles/create")
    public String createArticles(@RequestParam("category") Long categoryId, @RequestParam("title") String title,
            @RequestParam("shortTitle") String shortTile, @RequestParam("shortConTent") String shortConTent, @RequestParam("conTent") String conTent,
            @RequestParam("urlImage") MultipartFile urlImage, @RequestParam("hotArticles") Long hotArticles,
            Model model) {

        Articles articles = new Articles();
        Optional<Categorys> optionalCategory = categorysService.findByOptional(categoryId);

        if (optionalCategory.isPresent()) {
            Categorys category = optionalCategory.get();
            List<Articles> allArticles = articlesService.ggetAllArticlesExceptLatest();
            List<Categorys> allCategorys = categorysService.findByAllCategorys();
            articles.setTitle(title);
            articles.setShortTitle(shortTile);
            articles.setShortConTent(shortConTent);
            articles.setConTent(conTent);
            articles.setCreateDay(LocalDateTime.now());
            articles.setCategory(category);
            articles.setHotArticles(hotArticles);
            try {
                if (!urlImage.isEmpty()) {
                    String fileName = UUID.randomUUID().toString() + "-" + urlImage.getOriginalFilename();
                    java.nio.file.Path filePath = Paths.get("src/main/resources/static/images/", fileName);
                    Files.copy(urlImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                    articles.setUrlImage(fileName);
                    // articlesService.createArticles(articles);
                }
            } catch (IOException e) {
                return "redirect:/error";
            }
            articlesService.createArticles(articles);
            log.info("記事を登録できました");
            model.addAttribute("allCategorys", allCategorys);
            model.addAttribute("allArticles", allArticles);
            model.addAttribute("susccessMessageCreate", "Đăng bài viết thành công");
            model.addAttribute("susccessMessageUpdate", "Đã Sửa bài viết thành công");
            // return "redirect:/admin/blog/create";
            return "html/admin/createarticles.html";
        } else {
            log.info("No");
            model.addAttribute("error", "Looix");
            return "redirect:/error";
        }
    }

    /*
     * 記事を削除する
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/articles/delete/{articlesId}")
    public String deleteArticles(@PathVariable("articlesId") Long articlesId, Model model) {

        articlesService.deleteArticles(articlesId);
        List<Articles> allArticles = articlesService.ggetAllArticlesExceptLatest();
        List<Categorys> allCategorys = categorysService.findByAllCategorys();
        model.addAttribute("allArticles", allArticles); // 全て記事を表示する
        model.addAttribute("allCategorys", allCategorys); // 全てカテゴリーを表示する
        model.addAttribute("susccessMessageDelete", "Xóa bài viết thành công");
        log.info(articlesId + " を削除できました"); // Console 表示する
        return "html/admin/createarticles.html";
    }

    /*
     * 記事を更新する
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/articles/update/{articlesId}")
    public String updateArticles(@PathVariable("articlesId") Long articlesId, Model model) {

        Optional<Articles> articlesOptional = articlesService.findById(articlesId);

        if (articlesOptional.isPresent()) {
            Articles articles = articlesOptional.get();
            List<Categorys> allCategorys = categorysService.findByAllCategorys();
            List<Articles> allArticles = articlesService.getAllArticles();
            model.addAttribute("articles", articles);
            model.addAttribute("allCategorys", allCategorys);
            model.addAttribute("allArticles", allArticles);
            model.addAttribute("update", "update");
            return "html/admin/updatearticles.html";
        }
        return "redirect:/error";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/update/{articlesId}")
    public String updateArticles(@PathVariable("articlesId") Long articlesId,
            @ModelAttribute("articles") Articles updateArticles,
            BindingResult bindingResult, @RequestParam("urlImage") MultipartFile urlImage, Model model) {

        // if (bindingResult.hasErrors()) {
        // // Xử lý lỗi validation nếu có
        // return "html/admin/update.html";
        // }
        List<Categorys> allCategorys = categorysService.findByAllCategorys();
        Optional<Articles> articlesOptional = articlesService.findById(articlesId);

        if (articlesOptional.isPresent()) {
            Articles articles = articlesOptional.get();
            articles.setTitle(updateArticles.getTitle());
            articles.setShortTitle(updateArticles.getShortTitle());
            articles.setShortConTent(updateArticles.getShortConTent());
            articles.setConTent(updateArticles.getConTent());
            articles.setCategory(updateArticles.getCategory());
            articles.setUpdateDay(LocalDateTime.now());
            // アップした写真を確認する
            if (!urlImage.isEmpty()) {
                try {
                    // 写真を保存する場所
                    String fileName = UUID.randomUUID().toString() + "-" + urlImage.getOriginalFilename();
                    java.nio.file.Path filePath = Paths.get("src/main/resources/static/images/", fileName);
                    Files.copy(urlImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                    articles.setUrlImage(fileName);
                } catch (IOException e) {
                    // エラーになる時
                    log.info("Lỗi");
                    return "redirect:/error";
                }
            }
            log.info("Sửa thành công");
            articlesService.updateArticles(articles);
            model.addAttribute("susccessMessageUpdate", "Đã Sửa thành công bài viết số " + articlesId);
            model.addAttribute("return", "return");
            model.addAttribute("allCategorys", allCategorys);
            // return "redirect:/admin/blog/update/" + articlesId;
            return "html/admin/updatearticles.html";
        }
        // List <Categorys> allCategorys = categorysService.findByAllCategorys();
        // List <Articles> allArticles = articlesService.ggetAllArticlesExceptLatest();
        // model.addAttribute("allCategorys", allCategorys);
        // model.addAttribute("allArticles", allArticles);
        // log.info("更新できました");
        return "redirect:/error";
    }

    /*
     * Categorys: CRUDのAPI 機能
     */

    /*
     * Categorysを登録機能
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/categorys/create")
    public String getCategorys(Model model) {
        List <Categorys> allCategorys = categorysService.getAllCategorysUpdateDay();
        model.addAttribute("allCategorys", allCategorys);
        return "html/admin/createcategorys.html";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/categorys/create")
    public String createCategorys(@RequestParam("name") String name, Model model){
        List <Categorys> allCategorys = categorysService.getAllCategorysUpdateDay();
        Categorys categorys = new Categorys();
        categorys.setName(name);
        categorys.setCreateDay(LocalDateTime.now());
        try{
            categorysService.createCategorys(categorys);
            model.addAttribute("allCategorys", allCategorys);
            model.addAttribute("susccessMessageCategorys", "Đã tạo chủ đề thành công");
            log.info("カテゴリーを追加できました");
            return "html/admin/createcategorys.html";
        }catch(IllegalArgumentException e){
            log.info("エラー：カテゴリーを追加できなかった");
            model.addAttribute("allCategorys", allCategorys);
            model.addAttribute("error", e.getMessage());
            model.addAttribute("error", e.getMessage());
            return "html/admin/createcategorys.html";
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/categorys/delete/{categorysId}")
    public String deleteCategorys(@PathVariable("categorysId") Long categorysId, Model model){
        
        List <Categorys> allCategorys = categorysService.getAllCategorysUpdateDay();
        try{
            categorysService.deleteCategorys(categorysId);
            model.addAttribute("allCategorys", allCategorys);
            log.info("カテゴリーを削除できた");
            model.addAttribute("susccessMessageDeleteCategorys", "Xóa chủ đề thành công");
            return "/html/admin/createcategorys.html";
        }catch(Exception e){
            model.addAttribute("allCategorys", allCategorys);
            model.addAttribute("deleteCategorysError", "Lỗi không thể xóa chủ đề này");
            log.info("カテゴリーを削除できなかった");
            return "html/admin/createcategorys.html";
        }
    }

    /*
     * カテゴリーを更新機能
     */
    /*
     * GET
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/categorys/update/{categorysId}")
    public String getUpdateCategorys(@PathVariable("categorysId") Long categorysId, Model model){

        List <Categorys> allCategorys = categorysService.getAllCategorysUpdateDay();
        Optional <Categorys> categorysOptional = categorysService.findByOptional(categorysId);
        if(categorysOptional.isPresent()){
            Categorys categorys = categorysOptional.get();
            model.addAttribute("categorys", categorys);
            model.addAttribute("allCategorys", allCategorys);
            log.info("カテゴリーを更新ページ");
            model.addAttribute("update", "update");
            return "html/admin/updatecategorys.html";
        }
        return "html/admin/error.html";
    }

    /*
     * POST
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/categorys/update/{categorysId}")
    public String updateCategorys(@PathVariable("categorysId") Long categorysId, @ModelAttribute("categorys") Categorys updateCategorys, Model model){
        
        List <Categorys> allCategorys = categorysService.getAllCategorysUpdateDay();
        Optional <Categorys> categorysOptional = categorysService.findByOptional(categorysId);
        if(categorysOptional.isPresent()){
            Categorys categorys = categorysOptional.get();
            categorys.setName(updateCategorys.getName());
            categorys.setUpdateDay(LocalDateTime.now());

            try{
                categorysService.updateCategorys(categorys);
                log.info("カテゴリーを更新できた");
                model.addAttribute("return", "return");
                model.addAttribute("allCategorys", allCategorys);
                model.addAttribute("updateMessageCategorys", "Cập nhật chủ đề thành công");
                return "html/admin/updatecategorys.html";
            }catch(IllegalArgumentException e){
                model.addAttribute("updateCategorysError", e.getMessage());
                model.addAttribute("return", "return");
                return "html/admin/updatecategorys.html";
            }
        }
        // model.addAttribute("return");
        // model.addAttribute("")
        return "html/admin/error.html";
    }

    /*
     * ADMINになりたい方は
     * 申請機能
     */

     //Get
    @GetMapping("/register-admins")
    public String showRegisterAdmin(){
        return "html/admin/register-admins.html";
    }

    // Post
    @PostMapping("/register-admins")
    public String getRegisterAdmin(@RequestParam("nickName") String nickName,
        @RequestParam("age") int age, @RequestParam("gender") String gender, 
        @RequestParam("phone") String phone, @RequestParam("address") String address,
        @RequestParam("selfPr") String selfPr, @RequestParam("username") String username,
        @RequestParam("password") String password, @RequestParam("confirmedPassword") String confirmedPassword,
        RedirectAttributes redirectAttributes){
        
        //登録情報からAdminプロジェクトを作成
        Admin admin = new Admin();
        admin.setNickName(nickName);
        admin.setAge(age);
        admin.setGender(gender);
        admin.setPhone(phone);
        admin.setAddress(address);
        admin.setSelfPr(selfPr);
        admin.setUsername(username);
        admin.setPassword(password);
        admin.setConfirmedPassword(confirmedPassword);
        admin.setRole("ROLE_ADMIN");

        try{
            Admin registerAdmin = adminService.registerAdmin(admin);
            adminService.sendConfirmationEmail(registerAdmin);
            log.info("このフォームが使えた");
            return "html/admin/confirmation-code.html";
        }catch(IllegalArgumentException e){
            log.info(e.getMessage());
            return "redirect:/admin/blog/login";
        }
    }

    /*
     * confirmation_code
     */
    @PostMapping("/register-admins/confirm")
    public String confirmRegistrationAdmin(@RequestParam("confirmationCode") String confirmationCode, Model model){
        try{
            adminService.confirmationCodeAdmin(confirmationCode);
            log.info("emailで承認できた");
            return "redirect:/admin/blog/login";
        }catch(IllegalArgumentException e){
            model.addAttribute("errorConfirmationCode", e.getMessage());
            log.info("エラー：承認コードがダメ");
            return "html/admin/confirmation-code.html";
        }
    }
}