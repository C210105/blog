package net.shop2k.blog.entitys;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "managers")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@Data
public class Manager {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column (name = "nickName") //名
    private String nickName;

    @Column (name = "username") //メール
    private String username;    

    @Column (name = "password") // パスワード
    private String password;

    @Column(name = "setEnabled") // 不ー許可
    private boolean setEnabled;

    @Column (name = "role") //role
    private String role;

    @OneToMany(mappedBy = "manager") //複数adminに関係ある
    private List <Admin> admins;

    @OneToMany(mappedBy = "manager") //複数userに関係ある
    private List <User> users;

    @OneToMany(mappedBy = "manager")
    private List <Comment> comments;
}
