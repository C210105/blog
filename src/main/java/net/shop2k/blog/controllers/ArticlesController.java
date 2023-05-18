package net.shop2k.blog.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import net.shop2k.blog.entitys.Articles;
import net.shop2k.blog.entitys.Categorys;
import net.shop2k.blog.entitys.Products;
import net.shop2k.blog.services.ArticlesService;
import net.shop2k.blog.services.CategorysService;
import net.shop2k.blog.services.ProductsService;

/*
 * Articles Controller
 */
@Controller
@RequestMapping(path = "/blog/v1/categorys/articles")
public class ArticlesController {
    
    /*
     * Articles Service
     */
    @Autowired
    ArticlesService articlesService;

    /*
     * CategorysService
     */
    @Autowired
    CategorysService categorysService;

    /*
     * Products Service
     */
    @Autowired
    ProductsService productsService;

    /*
     * @PathVariable
     * Model
     */
    @GetMapping("/{categorysId}")
    public String showArticlesByCategorysId(@PathVariable Long categorysId, Model model){
        Optional <Categorys> cateOptional = categorysService.findByOptional(categorysId);
        if(cateOptional.isPresent()){
            Categorys categoryss = cateOptional.get();
            Articles latestOneArticles = articlesService.getLatestArticleByCategorys(categoryss);
            List <Articles> latestSixArticles = articlesService.getArticles2To7ByCategorys(categoryss);
            List <Articles> latestEightArticles = articlesService.getArticles8To15ByCategorys(categoryss);
            List <Articles> latestSixXArticles = articlesService.getArticles16To22ByCategorys(categoryss);
            Articles hotArticles = articlesService.getHotArticles();
            // List <Articles> articles = articlesService.getArticlesByCategorys(categoryss);
            List <Categorys> categorys = categorysService.findByAllCategorys();
            List <Products> products = productsService.finByAllProducts();
            model.addAttribute("categorys", categorys); //全てCategorysを表示する
            // model.addAttribute("articles", articles); //全てArticlesを表示する
            model.addAttribute("latestOneArticles", latestOneArticles); //Categorysに関連の1Articlesを表示する
            model.addAttribute("latestSixArticles", latestSixArticles); //6Articlesを表示する
            model.addAttribute("latestEightArticles", latestEightArticles); //8Articlesを表示する
            model.addAttribute("latestSixXArticles", latestSixXArticles); //6Articlesを表示する
            model.addAttribute("products", products); //全てProductsを表示する
            model.addAttribute("hotArticles", hotArticles); //ホットArticlesを表示する
            return "html/categorys.html";
        }   
        return "html/erro.html";
    }
}
