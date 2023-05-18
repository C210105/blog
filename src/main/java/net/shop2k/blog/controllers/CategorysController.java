package net.shop2k.blog.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.shop2k.blog.entitys.Articles;
import net.shop2k.blog.entitys.Categorys;
import net.shop2k.blog.entitys.Products;
import net.shop2k.blog.services.ArticlesService;
import net.shop2k.blog.services.CategorysService;
import net.shop2k.blog.services.ProductsService;

/*
 * Categorys Controller 
 */
@Controller
@RequestMapping(path = "/blog/index")
public class CategorysController {
    
    /*
     * Categorys Service 
     */
    @Autowired
    CategorysService categorysService;

    /*
     * Articles Service 
     */
    @Autowired
    ArticlesService articlesService;
    
    /*
     * Products Service
     */
    @Autowired
    ProductsService productsService;

    /*
     * /index 
     * Model
     */
    @GetMapping("/")
    public String getAllCategorys(Model model){
        List <Categorys> categorys = categorysService.findByAllCategorys();
        Optional <Articles> articles = articlesService.getLatestArticles();
        List <Articles> allArticles = articlesService.getAllArticlesExceptLatest();
        List <Products> products = productsService.finByAllProducts();
        Articles hotArticles = articlesService.getHotArticles();
        model.addAttribute("categorys", categorys); //全てCategorysを表する
        model.addAttribute("articles", articles.orElse(null)); //最新Articles
        model.addAttribute("allArticles", allArticles); //全てArticlesを表示する
        model.addAttribute("products", products); //全てProductsを表示する
        model.addAttribute("hotArticles", hotArticles); //ホットArticlesを表示する
        return "html/index.html";
    }

    // @GetMapping("/get/{categorysId}")
    // public String getArticlesByCategorysId(@PathVariable Long categorysId, Model model){
    //     List<Articles> articles = articlesService.getArticlesByCategorysId(categorysId);
    //     List <Categorys> categorys = categorysService.finByAllCategorys();
    //     model.addAttribute("categorys", categorys);
    //     model.addAttribute("articles", articles);
    //     return "html/categorys.html";
    // }

//     @GetMapping("/get/{categorysId}")
//     public String getArticlesByCategorysId(@PathVariable Long categorysId, Model model){
//     List<Articles> articles = articlesService.getArticlesByCategorysId(null);
//     List<Categorys> categorys = categorysService.finByAllCategorys();
//     model.addAttribute("categorys", categorys);
//     model.addAttribute("articles", articles);
//     return "html/categorys.html";
// }
    
    // @GetMapping("/get/{id}")
    // public String getCategorysById(@PathVariable("id") Long categorysId, Model model){
    //     List <Articles> articles = articlesService.getArticlesByCategorysId(categorysId);
    //     model.addAttribute("articles", articles);
    //     return "html/categorys.html";
    // }


    // @GetMapping("/get/{categorysId}")
    // public String showArticlesByCategory(@PathVariable Long categorysId, Model model) {
    //     // Optional<Categorys> categoryOptional = categorysRepository.findById(categorysId);
    //     Optional<Categorys> categoryOptional = categorysService.findByOptional(categorysId);
    //     if (categoryOptional.isPresent()) {
    //         Categorys categorys = categoryOptional.get();
    //         Articles latestArticle = articlesService.getLatestArticleByCategorys(categorys);
    //         List <Articles> articles = articlesService.getArticlesByCategorys(categorys);
    //         List <Categorys> categoryss = categorysService.findByAllCategorys();
    //         model.addAttribute("categorys", categoryss);
    //         model.addAttribute("articles", articles);
    //         model.addAttribute("latestArticle", latestArticle);
    //         // return "html/index.html";
    //     }
    //     return "html/categorys.html";
    // }
}