package net.shop2k.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.log4j.Log4j2;
import net.shop2k.blog.entitys.Articles;
import net.shop2k.blog.services.ArticlesService;

@Log4j2
@Controller
@RequestMapping(path= "/blog/index")
public class SearchController {
    
    @Autowired
    private ArticlesService articlesService;

    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model){

        /*
         * Search Title
         */
        List <Articles> searchTitle = articlesService.searchArticles(keyword);
        if(!searchTitle.isEmpty()){
            model.addAttribute("searchTitle", searchTitle);
            log.info("検索できた");
            return "html/searchTitle.html";
        }else{
            model.addAttribute("searchError", "Từ khóa không hợp lệ");
            log.info("このキーワードを検索できなかった");
            return "html/searchtitle.html";
        }
    }

}
