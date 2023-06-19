package net.shop2k.blog.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/*
 * コメント情報
 */
@Entity
@Table (name = "comments")
@Data
public class Comment{

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content") //コメント
    private String content;

    @ManyToOne
    @JoinColumn(name = "articles_id") //articlesに関係ある
    private Articles articles;

    @ManyToOne
    @JoinColumn(name = "manager_id") //managerに関係ある
    private Manager manager;

    @ManyToOne
    @JoinColumn(name = "admin_id") //adminに関係ある
    private Admin admin;

    @ManyToOne
    @JoinColumn(name = "user_id") //Userに関係ある
    private User user;
}