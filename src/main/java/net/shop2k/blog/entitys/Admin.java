package net.shop2k.blog.entitys;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/*
 * ADMIN 情報
 */
@Entity
// @Table(name = "admins")
@DiscriminatorValue("admin")
@Data
@EqualsAndHashCode(callSuper = false)
public class Admin extends Manager{

    @Column (name = "confirmedPassword")
    private String confirmedPassword;

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

    @Column (name = "emailCode") //コードで承認かどうか
    private boolean emailCode;

    @Column(name = "confirmation_code") //承認コード
    private String confirmationCode;

    @ManyToOne
    @JoinColumn (name = "manager_id") //managerに関係ある
    private Manager manager;

    @OneToMany(mappedBy = "admin") //adminに関係ある
    private List<User> users;
}
