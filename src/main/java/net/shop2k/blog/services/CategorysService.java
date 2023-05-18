package net.shop2k.blog.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.shop2k.blog.entitys.Categorys;
import net.shop2k.blog.repositorys.CategorysRepository;

/*
 * Categorys Service
 * ロジックを処理する
 */
@Service
public class CategorysService {
    
    @Autowired
    CategorysRepository categorysRepository;
    
    /*
     * 全てCategorysを取得する
     */
    public List<Categorys> findByAllCategorys(){
        return categorysRepository.findAll();
    }

    /*
     * 指定したcategorysIdに基づいてCategorysをクエリして取得する
     */
    public Optional<Categorys> findByOptional(Long categorysId){
        return categorysRepository.findById(categorysId);
    }

}
