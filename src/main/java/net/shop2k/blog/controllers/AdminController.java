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
import org.springframework.data.jpa.domain.JpaSort.Path;
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
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j2;
import net.shop2k.blog.entitys.Articles;
import net.shop2k.blog.entitys.Categorys;
import net.shop2k.blog.services.ArticlesService;
import net.shop2k.blog.services.CategorysService;

@Log4j2
@Controller
@RequestMapping (path = "/admin/blog")
public class AdminController {
    
    @Autowired
    ArticlesService articlesService;

    @Autowired
    CategorysService categorysService;

    @GetMapping("/login")
    public String loginAdmin(Model model){
        return "html/admin/login.html";
    }

    @GetMapping("/registeradmin")
    public String registerAdmin(){
        return "html/admin/register.html";
    }

    @GetMapping("/error")
    public String loginError(){
        return "html/error.html";
    }



    // @PostMapping("/registeradmin")
    // public String registerAdmin(@RequestParam("username") String username, @RequestParam("password") String passwrod, Model model){
        
    //     /*
    //      * ADMIN を生成する
    //      */
    //     Admin admin = new Admin();
    //     admin.setUsername(username);
    //     admin.setPassword(passwrod);
    //     admin.setSetEnabled(true);
    //     admin.setRole("ROLE_ADMIN");

    //     //エラーをチェックする
    //     try{
    //         adminService.registerAdmin(admin);
    //         model.addAttribute("susccessMessage", "Đăng kí thành công");
    //         return "html/admin/register.html";
    //     }catch (IllegalArgumentException e){
    //         model.addAttribute("error", e.getMessage());
    //         return "html/admin/register.html";
    //     }
    // }

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

    /*
     * CRUD
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/create")
    public String createArticles(Model model) {
        List <Articles> allArticles = articlesService.ggetAllArticlesExceptLatest();
        List <Categorys> categorys = categorysService.findByAllCategorys();
        model.addAttribute("categorys", categorys);
        model.addAttribute("allArticles", allArticles);
        return "html/admin/crud.html";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public String createArticles (@RequestParam("category") Long categoryId, @RequestParam("title") String title, @RequestParam("shortTitle") String shortTile, @RequestParam("conTent") String conTent,
        @RequestParam("urlImage") MultipartFile urlImage, Model model) {

            Articles articles = new Articles();
            Optional<Categorys> optionalCategory = categorysService.findByOptional(categoryId);
            
            if (optionalCategory.isPresent()) {
                Categorys category = optionalCategory.get();
                List <Articles> allArticles = articlesService.ggetAllArticlesExceptLatest();
                List <Categorys> categorys = categorysService.findByAllCategorys();
                articles.setTitle(title);
                articles.setShortTitle(shortTile);
                articles.setConTent(conTent);
                articles.setUpdateDay(LocalDateTime.now());
                articles.setCategory(category);
                try{
                    if(!urlImage.isEmpty()){
                        String fileName = UUID.randomUUID().toString() + "-" + urlImage.getOriginalFilename();
                        java.nio.file.Path filePath = Paths.get("src/main/resources/static/images/", fileName);
                        Files.copy(urlImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                        articles.setUrlImage(fileName);
                        articlesService.createArticles(articles);
                    }
                }catch(IOException e){
                    return "/";
                }
                log.info("OK");
                model.addAttribute("categorys", categorys);
                model.addAttribute("allArticles", allArticles);
                model.addAttribute("susccessMessage", "Đăng bài viết thành công");
                return "html/admin/crud.html";
            } else{
                log.info("No");
                model.addAttribute("error", "Looix");
                return "html/admin/crud.html";
            }
            // articles.setUrlImage(urlImage);
            // try{
            //     log.info("Loix");
            //     model.addAttribute("successMassage", "Đăng bài viết thành công");
            //     articlesService.createArticles(articles);
            //     return "html/admin/crud.html";
            // } catch (IllegalArgumentException e){
            //     model.addAttribute("error", "Looix");
            //     return "html/admin/crud.html";
            // }
            }
}
