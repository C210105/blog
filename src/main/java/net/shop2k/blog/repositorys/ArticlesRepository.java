package net.shop2k.blog.repositorys;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.shop2k.blog.entitys.Articles;
import net.shop2k.blog.entitys.Categorys;

@Repository
public interface ArticlesRepository extends JpaRepository <Articles, Long> {

    List <Articles> findByCategory(Categorys categorysId);

    // List <Articles> findAll();

    Optional <Articles> findFirstArticlesByOrderByUpdateDayDesc();

    List<Articles> findAllArticlesByOrderByUpdateDayDesc();

    Optional <Articles> findFirstByOrderByHotArticlesDescUpdateDayDesc();

    Optional <Articles> findFirstByCategoryOrderByUpdateDayDesc(Categorys categorys);

    List <Articles> findTop7ByCategoryOrderByUpdateDayDesc(Categorys categorys);

    List <Articles> findTop16ByCategoryOrderByUpdateDayDesc(Categorys categorys);

    List <Articles> findTop23ByCategoryOrderByUpdateDayDesc(Categorys categorys);

}