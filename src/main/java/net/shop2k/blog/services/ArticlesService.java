package net.shop2k.blog.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.shop2k.blog.entitys.Articles;
import net.shop2k.blog.entitys.Categorys;
import net.shop2k.blog.repositorys.ArticlesRepository;

@Service
public class ArticlesService {

    @Autowired
    ArticlesRepository articlesRepository;

    public List<Articles> getArticlesByCategorys(Categorys categorysId){
        return articlesRepository.findByCategory(categorysId);
    }

    public List <Articles> getAllArticles(){
        return articlesRepository.findAll();
    }

    public Optional <Articles> getLatestArticles(){
        return articlesRepository.findFirstArticlesByOrderByUpdateDayDesc();
    }

    public List <Articles> getAllArticlesExceptLatest(){
        List<Articles> allArticles = articlesRepository.findAllArticlesByOrderByUpdateDayDesc();
        if (!allArticles.isEmpty()) {
            allArticles.remove(0);
        }
        return allArticles;
    }

    public Articles getLatestArticleByCategorys(Categorys categorys) {
        return articlesRepository.findFirstByCategoryOrderByUpdateDayDesc(categorys).orElse(null);
    }

    public Optional<Articles> findById(Long articlesId){
        return articlesRepository.findById(articlesId);
    }

    public List<Articles> getArticles2To7ByCategorys(Categorys categorys){
        List<Articles> latestArticles = articlesRepository.findTop7ByCategoryOrderByUpdateDayDesc(categorys);
        if(latestArticles.size() > 1){
            return latestArticles.subList(1,latestArticles.size());
        }
        return new ArrayList<>();
    }

    public List<Articles> getArticles8To15ByCategorys(Categorys categorys){
        List<Articles> latestArticless = articlesRepository.findTop16ByCategoryOrderByUpdateDayDesc(categorys);
        if(latestArticless.size() > 8){
            return latestArticless.subList(8, latestArticless.size());
        }
        return new ArrayList<>();
    }

    public List<Articles> getArticles16To22ByCategorys(Categorys categorys){
        List<Articles> latestArticlesss = articlesRepository.findTop23ByCategoryOrderByUpdateDayDesc(categorys);
        if(latestArticlesss.size() > 16){
            return latestArticlesss.subList(16, latestArticlesss.size());
        }
        return new ArrayList<>();
    }

    // public List<Articles> getLatestSixArticlesByCategory(Categorys categorys) {
    //     List<Articles> latestArticles = articlesRepository.findTop7ByCategoryOrderByUpdateDayDesc(categorys);
    //     if (latestArticles.size() > 1) {
    //         Articles latestArticle = getLatestArticleByCategorys(categorys);
    //         if (latestArticles.contains(latestArticle)) {
    //             latestArticles.remove(latestArticle);
    //         }
    //     }
    //     return latestArticles;
    // }

    // public List<Articles> findNewsPostArticles(int count){
    //     return articlesRepository.findAllByOrderByUpdatedAtDesc(PageRequest.of(0, count));
    // }

    // public Optional<Articles> finById(Long id){
    //     return articlesRepository.findById(id);
    // }
    

    // public List <Articles> getArticlesByCategorysId(Long categroysId){
    //     return articlesRepository.findByCategory(categroysId);
    // }

}
