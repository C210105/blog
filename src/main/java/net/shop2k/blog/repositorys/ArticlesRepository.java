package net.shop2k.blog.repositorys;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.shop2k.blog.entitys.Articles;
import net.shop2k.blog.entitys.Categorys;

/*
 * ArticlesRepository
 * データの保存とクエリを担当 
 */

@Repository
public interface ArticlesRepository extends JpaRepository <Articles, Long> {

    /*
     * Categoryフィルードが指定したcategorysIdを持つCategorysオブジェクトに対応するArticlesオブジェクトをDBで検索
     * 返される結果はクエリ条件を満たすArticlesオブジェクトのList
     */
    List <Articles> findByCategory(Categorys categorysId);

    /*
     * Articlesオブジェクトを全て検索
     */
    // List <Articles> findAll();

    /*
     * updateDayフィルードによって
     * 降順(Desc)に並べ替えられたDB内の最初のArticlesオブジェクトを検索して返す
     */
    Optional <Articles> findFirstArticlesByOrderByCreateDayDesc();

    /*
     * updateDayフィルードによって
     * 降順(Desc)に並べ替えられたDB内の全てのArticlesオブジェクトを検索して返す
     */
    List<Articles> findAllArticlesByOrderByCreateDayDesc();

    /*
     * DB内のArticlesオブジェクトを検索し
     * クエリの条件を満たす最初のオブジェクトをArticlesフィルードを降順に並べ替える
     * もし、同じねのArticles場合はupdateDayフィルードでさらに降順並べ替える
     */
    Optional <Articles> findFirstByOrderByHotArticlesDescCreateDayDesc();

    /*
     * 指定したCategorysオブジェクトに関連付けられたArticlesオブジェクトの中で
     * 最初のオブジェクトをupdateDayフィルードを降順に並べ替える。
     */
    Optional <Articles> findFirstByCategoryOrderByCreateDayDesc(Categorys categorys);

    /*
     * 指定したCategorysオブジェクトに関連付けられたArticlesオブジェクトの中で
     * 7のオブジェクトをupdateDayフィルードを降順に並べ替える
     */
    List <Articles> findTop7ByCategoryOrderByCreateDayDesc(Categorys categorys);

    /*
     * 指定したCategorysオブジェクトに関連付けられたArticlesオブジェクトの中で
     * 16オブジェクトをupdateDayフィルードを並べ替える
     */
    List <Articles> findTop16ByCategoryOrderByCreateDayDesc(Categorys categorys);

    /*
     * 指定したCategorysオブジェクトに関連付けられたArticlesオブジェクトの中で
     * 23オブジェクトをupdateDayフィルードを並べ替える
     */
    List <Articles> findTop23ByCategoryOrderByCreateDayDesc(Categorys categorys);
}