package net.shop2k.blog.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.shop2k.blog.entitys.Categorys;
import net.shop2k.blog.repositorys.CategorysRepository;

@Service
public class CategorysService {
    
    @Autowired
    CategorysRepository categorysRepository;
    
    public List<net.shop2k.blog.entitys.Categorys> findByAllCategorys(){
        return categorysRepository.findAll();
    }

    public Optional<Categorys> findByOptional(Long categorysId){
        return categorysRepository.findById(categorysId);
    }

}
