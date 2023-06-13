package net.shop2k.blog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import net.shop2k.blog.entitys.Admin;
import net.shop2k.blog.entitys.Manager;
import net.shop2k.blog.repositorys.AdminRepository;
import net.shop2k.blog.repositorys.ManagerRepository;

@Service
public class ManagerService {
    
    @Autowired
    private ManagerRepository managerRepository;
    
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /*
     * Managerを設定する
     */
    @Transactional
    public void registerManager(){  
        Manager manager = new Manager();
        manager.setNickName("manager");
        manager.setUsername("long.bmw10@gmail.com");
        String password = "123456789A";
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        manager.setPassword(encodedPassword);
        manager.setRole("ROLE_MANAGER");
        manager.setSetEnabled(true);
        managerRepository.save(manager);
    }

    /*
     * 申請フォームを不-許可する
     */
    public void setAdmin(String username){
        
        Admin admin = adminRepository.findByUsername(username);
        admin.setSetEnabled(true);
        adminRepository.save(admin);
    }

    /*
     * ADMINを削除する
     */
    public void deleteAdmin(Long id){
        
        adminRepository.deleteById(id);
    }
}
