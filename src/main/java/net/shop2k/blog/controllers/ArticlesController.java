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

@Controller
@RequestMapping(path = "/blog/v1/categorys/articles")
public class ArticlesController {
    
    @Autowired
    ArticlesService articlesService;

    @Autowired
    CategorysService categorysService;

    @Autowired
    ProductsService productsService;

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
            List <Articles> articles = articlesService.getArticlesByCategorys(categoryss);
            List <Categorys> categorys = categorysService.findByAllCategorys();
            List <Products> products = productsService.finByAllProducts();
            model.addAttribute("categorys", categorys);
            model.addAttribute("articles", articles);
            model.addAttribute("latestOneArticles", latestOneArticles);
            model.addAttribute("latestSixArticles", latestSixArticles);
            model.addAttribute("latestEightArticles", latestEightArticles);
            model.addAttribute("latestSixXArticles", latestSixXArticles);
            model.addAttribute("products", products);
            model.addAttribute("hotArticles", hotArticles);
            return "html/categorys.html";
        }
        return "html/erro.html";
    }
}
