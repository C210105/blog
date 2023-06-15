package net.shop2k.blog.entitys;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
// @Table(name = "users")
@DiscriminatorValue("user")
@Data
@EqualsAndHashCode(callSuper = false)
public class User extends Admin{

    @ManyToOne
    @JoinColumn(name ="manager_id") //managerに関係ある
    private Manager manager;

    @ManyToOne
    @JoinColumn(name = "admin_id") ////adminに関係ある
    private Admin admin;
}