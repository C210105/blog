package net.shop2k.blog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.shop2k.blog.entitys.Articles;
import net.shop2k.blog.entitys.Comment;
import net.shop2k.blog.entitys.User;
import net.shop2k.blog.repositorys.CommentRepository;

@Service
public class CommentService {
    
    @Autowired
    private CommentRepository commentRepository;

    /*
     * 記事のコメントより検索
     */
    public List <Comment> getCommentsByArticles(Articles articles){
        return commentRepository.findByArticles(articles);
    }

    /*
     * ユーザーのコメントより検索
     */
    public List <Comment> getCommentsByUsers(User user){
        return commentRepository.findByUser(user);
    }

    /*
     * コメントを登録
     */
    public Comment createComment(Comment comment){
        return commentRepository.save(comment);
    }

    /*
     * コメントを削除
     */
    public void deleteComment(Long id){
        commentRepository.deleteById(id);
    }
}
