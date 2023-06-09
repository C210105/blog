package net.shop2k.blog.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

/*
 * ADMIN 情報
 */
@Entity
@Table (name = "admins")
@PrimaryKeyJoinColumn(name = "id")
@Data
public class Admin extends User{

    @Column (name = "age") //年齢
    private int age;

    @Column (name = "gender") //性別
    private String gender;

    @Column (name = "phone") //携帯電話
    private String phone;

    @Column (name = "address") //住所
    private String address;

    @Column (name = "selfPr") //自己PR
    private String selfPr;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
