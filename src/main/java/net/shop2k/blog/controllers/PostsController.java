package net.shop2k.blog.controllers;

import java.util.List;

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
@RequestMapping(path = "/blog/v1/categorys/articles/posts/")
public class PostsController {
    
    @Autowired
    CategorysService categorysService;

    @Autowired
    ArticlesService articlesService;

    @Autowired
    ProductsService productsService;

    @GetMapping("/{articlesId}")
    public String showArticleDetails(@PathVariable Long articlesId, Model model) {
        Articles articles = articlesService.findById(articlesId).orElse(null);
        List <Categorys> categorys = categorysService.findByAllCategorys();
        List <Products> products = productsService.finByAllProducts();
        Articles hotArticles = articlesService.getHotArticles();
        model.addAttribute("articles", articles);
        model.addAttribute("categorys", categorys);
        model.addAttribute("products", products);
        model.addAttribute("hotArticles", hotArticles);
        return "html/posts.html";
    }


}
