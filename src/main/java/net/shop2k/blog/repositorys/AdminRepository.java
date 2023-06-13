package net.shop2k.blog.repositorys;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.shop2k.blog.entitys.Admin;
/*
 * ADMIN Repository
 */
@Repository
public interface AdminRepository extends JpaRepository <Admin, Long> {

    Optional<Admin> findById(Long id);
    
    Admin findByUsername (String username); //emailで検索

    Admin findByUsernameAndSetEnabled(String username, boolean setEnabled); 

    Admin findByConfirmationCode(String confirmationCode); //code

}

