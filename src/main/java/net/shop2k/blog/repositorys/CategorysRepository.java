package net.shop2k.blog.repositorys;

import java.util.Locale.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.shop2k.blog.entitys.Categorys;


/*
 * Categorys Repository
 */

@Repository
public interface CategorysRepository extends JpaRepository<Categorys, Long>{
    Category getCategoryById(Long categoryId);

    Categorys findByName(String name);
}
