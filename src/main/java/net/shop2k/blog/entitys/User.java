package net.shop2k.blog.entitys;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table (name = "users")
@Data
public class User{

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "nickName")
    private String nickName;
    
    @Column (name = "username")  //email
    private String username;

    @Column (name = "password")
    private String password;

    @Column (name = "confirmedPassword")
    private String confirmedPassword;
    
    @Column (name = "role")
    private String role;

    @Column(name = "setEnabled")
    private boolean setEnabled;

    @Column(name = "confirmation_code")
    private String confirmationCode;

    @OneToMany (mappedBy = "user")
    private List<Admin> admins;
}