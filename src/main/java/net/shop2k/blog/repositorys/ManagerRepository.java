package net.shop2k.blog.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.shop2k.blog.entitys.Manager;


@Repository
public interface ManagerRepository extends JpaRepository <Manager, Long>{
    
    Manager findByUsername(String username);
}
