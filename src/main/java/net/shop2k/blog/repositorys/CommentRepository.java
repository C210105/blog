package net.shop2k.blog.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.shop2k.blog.entitys.Articles;
import net.shop2k.blog.entitys.Comment;
import net.shop2k.blog.entitys.User;

@Repository
public interface CommentRepository extends JpaRepository <Comment, Long>{
    
    List <Comment> findByArticles (Articles articles); //記事を検索

    List <Comment> findByUser (User user); //ユーザーを検索
}
