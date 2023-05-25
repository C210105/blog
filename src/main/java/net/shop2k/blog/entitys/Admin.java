package net.shop2k.blog.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/*
 * ADMIN 情報
 */
@Entity
@Table (name = "admins")
@Data
public class Admin{

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "username")
    private String username;

    @Column (name = "password")
    private String password;

    @Column (name = "role")
    private String role;

    @Column(name = "setEnabled")
    private boolean setEnabled;
}
