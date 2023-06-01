package net.shop2k.blog.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public List<Categorys> findByAllCategorys() {
        return categorysRepository.findAll();
    }

    public List <Categorys> getAllCategorysUpdateDay(){
        return categorysRepository.findAllCategorysByOrderByCreateDayDesc();
    }

    /*
     * 指定したcategorysIdに基づいてCategorysをクエリして取得する
     */
    public Optional<Categorys> findByOptional(Long categorysId) {
        return categorysRepository.findById(categorysId);
    }

    /*
     * Categorys CRUDのAPI　機能
     */

    /*
     * Categorysを登録機能
     */
    public void createCategorys(Categorys categorys) {
        /*
         * name 空白時
         */
        if(categorys.getName() == null || categorys.getName().isEmpty()){
            throw new IllegalArgumentException("Nhập chủ đề");
        }
        /*
         * name 存在してる時
         */
        else if(categorysRepository.findByName(categorys.getName()) != null){
            throw new IllegalArgumentException("Chủ đề đã tồn tại");
        }
        /*
         * DBに保存する
         */
        categorysRepository.save(categorys);
    }

    /*
     * カテゴリーを更新機能
     */
    public void updateCategorys(Categorys categorys){

        if(categorys.getName() == null || categorys.getName().isEmpty()){
            throw new IllegalArgumentException("Nhập chủ đề");
        }

        categorysRepository.save(categorys);
    }

    /*
     * カテゴリーを削除機能
     */
    public void deleteCategorys (Long categorysId){
        categorysRepository.deleteById(categorysId);
    }

}
