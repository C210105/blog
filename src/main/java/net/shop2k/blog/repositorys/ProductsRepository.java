package net.shop2k.blog.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.shop2k.blog.entitys.Products;

/*
 * Products Repository
 */
@Repository
public interface ProductsRepository extends JpaRepository <Products, Long> { 
    
}
