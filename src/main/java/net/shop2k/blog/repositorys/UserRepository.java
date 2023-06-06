package net.shop2k.blog.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.shop2k.blog.entitys.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    User findByUsername(String username); //Emailで検索

    User findByUsernameAndSetEnabled(String username, boolean setEnabled); //EmailとsetEnable(email承認かどうか)で検索

    User findByConfirmationCode(String confirmationCode); //Email承認のコードで検索
}
