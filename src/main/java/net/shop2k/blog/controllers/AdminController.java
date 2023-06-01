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

import lombok.extern.log4j.Log4j2;
import net.shop2k.blog.entitys.Articles;
import net.shop2k.blog.entitys.Categorys;
import net.shop2k.blog.services.ArticlesService;
import net.shop2k.blog.services.CategorysService;

@Log4j2
@Controller
@RequestMapping(path = "/admin/blog")
public class AdminController {

    @Autowired
    ArticlesService articlesService;

    @Autowired
    CategorysService categorysService;

    @GetMapping("/login")
    public String loginAdmin(Model model) {
        return "html/admin/login.html";
    }

    @GetMapping("/registeradmin")
    public String registerAdmin() {
        return "html/admin/register.html";
    }

    @GetMapping("/error")
    public String loginError() {
        return "html/error.html";
    }

    // @PostMapping("/registeradmin")
    // public String registerAdmin(@RequestParam("username") String username,
    // @RequestParam("password") String passwrod, Model model){

    // /*
    // * ADMIN を生成する
    // */
    // Admin admin = new Admin();
    // admin.setUsername(username);
    // admin.setPassword(passwrod);
    // admin.setSetEnabled(true);
    // admin.setRole("ROLE_ADMIN");

    // //エラーをチェックする
    // try{
    // adminService.registerAdmin(admin);
    // model.addAttribute("susccessMessage", "Đăng kí thành công");
    // return "html/admin/register.html";
    // }catch (IllegalArgumentException e){
    // model.addAttribute("error", e.getMessage());
    // return "html/admin/register.html";
    // }
    // }

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
    @GetMapping("/create")
    public String createArticles(Model model) {
        List<Articles> allArticles = articlesService.ggetAllArticlesExceptLatest();
        List<Categorys> allCategorys = categorysService.findByAllCategorys();
        model.addAttribute("allCategorys", allCategorys);
        model.addAttribute("allArticles", allArticles);
        return "html/admin/crud.html";
    }

    /*
     * 記事を登録する
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public String createArticles(@RequestParam("category") Long categoryId, @RequestParam("title") String title,
            @RequestParam("shortTitle") String shortTile, @RequestParam("conTent") String conTent,
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
            articles.setConTent(conTent);
            articles.setUpdateDay(LocalDateTime.now());
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
            return "html/admin/crud.html";
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
        return "html/admin/crud.html";
    }

    /*
     * 記事を更新する
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/update/{articlesId}")
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
            return "html/admin/update.html";
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
            articles.setConTent(updateArticles.getConTent());
            articles.setCategory(updateArticles.getCategory());
            // articles.setUpdateDay(LocalDateTime.now());
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
            return "html/admin/update.html";
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
        return "html/admin/categorys.html";
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
            return "html/admin/categorys.html";
        }catch(IllegalArgumentException e){
            log.info("エラー：カテゴリーを追加できなかった");
            model.addAttribute("allCategorys", allCategorys);
            model.addAttribute("error", e.getMessage());
            model.addAttribute("error", e.getMessage());
            return "html/admin/categorys.html";
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
            return "/html/admin/categorys.html";
        }catch(Exception e){
            model.addAttribute("allCategorys", allCategorys);
            model.addAttribute("deleteCategorysError", "Lỗi không thể xóa chủ đề này");
            log.info("カテゴリーを削除できなかった");
            return "html/admin/categorys.html";
        }
    }
}