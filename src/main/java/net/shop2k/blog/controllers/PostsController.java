package net.shop2k.blog.controllers;

import java.security.Principal;
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
import net.shop2k.blog.entitys.Articles;
import net.shop2k.blog.entitys.Categorys;
import net.shop2k.blog.entitys.Comment;
import net.shop2k.blog.entitys.Products;
import net.shop2k.blog.entitys.User;
import net.shop2k.blog.services.ArticlesService;
import net.shop2k.blog.services.CategorysService;
import net.shop2k.blog.services.CommentService;
import net.shop2k.blog.services.ProductsService;
import net.shop2k.blog.services.UserService;

/*
 * Posts Controller
 */
@Log4j2
@Controller
@RequestMapping(path = "/blog/v1/categorys/articles/posts/")
public class PostsController {
    @Autowired
    private UserService userService;

    @Autowired
    CategorysService categorysService;

    @Autowired
    ArticlesService articlesService;

    @Autowired
    ProductsService productsService;

    @Autowired
    private CommentService commentService;

    /*
     * @PathVariable
     * Model
     */
    @GetMapping("/{articlesId}")
    public String showArticleDetails(@PathVariable Long articlesId, Model model) {
        Articles articles = articlesService.findById(articlesId).orElse(null);
        List <Comment> comments = commentService.getCommentsByArticles(articles);
        List <Categorys> categorys = categorysService.findByAllCategorys();
        List <Products> products = productsService.finByAllProducts();
        Articles hotArticles = articlesService.getHotArticles();
        model.addAttribute("comments", comments);
        model.addAttribute("articles", articles); //Articlesを表示する
        model.addAttribute("categorys", categorys); //全てCategorysを表示する
        model.addAttribute("products", products); //全てProductsを表示する
        model.addAttribute("hotArticles", hotArticles); //ホットArticlesを表示する
        return "html/posts.html";
    }

    /*
     * 記事にコメント機能
     */
    @PostMapping("/{articlesId}/comments")
    public String createComment(@PathVariable Long articlesId, @RequestParam("content") String content, Principal principal, RedirectAttributes redirectAttributes){
        
        Articles articles = articlesService.findById(articlesId).orElse(null);
        try{
            // ユーザーかどうかをチェック
            User user = userService.getUsername(principal.getName());
            Comment comment = new Comment();
            comment.setUser(user);
            comment.setArticles(articles);
            comment.setContent(content);
            try{
                //コメントできる時
                commentService.createComment(comment);
                log.info("コメントできた");
                return "redirect:/blog/v1/categorys/articles/posts/" + articlesId;
            } catch(Exception e){
                //できない時
                log.info("エラー：コメントできなかった");
                return "redirect:/blog/v1/categorys/articles/posts/" + articlesId;
            }
        }catch(Exception e){
            //ユーザーじゃないとき
            redirectAttributes.addFlashAttribute("commentErrorMessage", "Hãy đăng nhập để sử dụng chức năng này");
            log.info("エラー");
            return "redirect:/blog/login";
        }
    }


}
