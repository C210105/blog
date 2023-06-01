package net.shop2k.blog.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.shop2k.blog.entitys.Articles;
import net.shop2k.blog.entitys.Categorys;
import net.shop2k.blog.repositorys.ArticlesRepository;

/*
 * Articles Service
 * ロジックを処理する
 */
@Service
public class ArticlesService {

    @Autowired
    ArticlesRepository articlesRepository;

    /*
     * categorysId引数として特定のCategorysオブジェクトに関連するArticlesオブジェクトをクエリする
     * 戻り値：List <Articles>
     */
    // public List<Articles> getArticlesByCategorys(Categorys categorysId){
    //     return articlesRepository.findByCategory(categorysId);
    // }

    /*
     * DB内の全てArticlesを取得する
     * 戻り値：
     */
    public List <Articles> getAllArticles(){
        return articlesRepository.findAll();
    }

    /*
     * 最新のArticlesを取得する
     */
    public Optional <Articles> getLatestArticles(){
        return articlesRepository.findFirstArticlesByOrderByUpdateDayDesc();
    }

    /*
     * １番hotArticlesを取得する
     */
    public Articles getHotArticles(){
        return articlesRepository.findFirstByOrderByHotArticlesDescUpdateDayDesc().orElse(null);
    }


    /*
     * 最新Articles以外全てArticlesを取得する
     */
    public List <Articles> getAllArticlesExceptLatest(){
        List<Articles> allArticles = articlesRepository.findAllArticlesByOrderByUpdateDayDesc();
        if (!allArticles.isEmpty()) {
            allArticles.remove(0);
        }
        return allArticles;
    }

    public List <Articles> ggetAllArticlesExceptLatest(){
        List<Articles> allArticles = articlesRepository.findAllArticlesByOrderByUpdateDayDesc();
        // if (!allArticles.isEmpty()) {
        //     allArticles.remove(0);
        // }
        return allArticles;
    }

    /*
     * 特定のCategorysに関連の最新Articlesを取得する
     */
    public Articles getLatestArticleByCategorys(Categorys categorys) {
        return articlesRepository.findFirstByCategoryOrderByUpdateDayDesc(categorys).orElse(null);
    }

    /*
     * 指定したarticlesId基づいてArticlesを取得する
     * 戻り値：Optinal <Articles>
     */
    public Optional<Articles> findById(Long articlesId){
        return articlesRepository.findById(articlesId);
    }

    /*
     * 指定したcategorysに関連するArticlesのうち、
     * 2番から7番目のArticlesを取得する
     */
    public List<Articles> getArticles2To7ByCategorys(Categorys categorys){
        List<Articles> latestArticles = articlesRepository.findTop7ByCategoryOrderByUpdateDayDesc(categorys);
        if(latestArticles.size() > 1){
            return latestArticles.subList(1,latestArticles.size());
        }
        return new ArrayList<>();
    }

    /*
     * 指定したcategorysに関連するArticlesのうち、
     * 8番から15番目のArticlesを取得する
     */
    public List<Articles> getArticles8To15ByCategorys(Categorys categorys){
        List<Articles> latestArticless = articlesRepository.findTop16ByCategoryOrderByUpdateDayDesc(categorys);
        if(latestArticless.size() > 8){
            return latestArticless.subList(8, latestArticless.size());
        }
        return new ArrayList<>();
    }

    /*
     * 指定したcategorysに関連するArticlesのうち,
     * 16番から22番目のArticlesを取得する
     */
    public List<Articles> getArticles16To22ByCategorys(Categorys categorys){
        List<Articles> latestArticlesss = articlesRepository.findTop23ByCategoryOrderByUpdateDayDesc(categorys);
        if(latestArticlesss.size() > 16){
            return latestArticlesss.subList(16, latestArticlesss.size());
        }
        return new ArrayList<>();
    }


    /*
     * Articles: CRUDのAPI 機能
     */

     /*
      * 新しい記事を登録機能
      */
    public void createArticles (Articles articles){
        articlesRepository.save(articles);
    }

    /*
     * 記事を更新機能
     */
    public Articles updateArticles (Articles articles){
        return articlesRepository.save(articles);
    }

    /*
     * 記事を削除機能
     */
    public void deleteArticles (Long articlesId){
        articlesRepository.deleteById(articlesId);
    }

}
