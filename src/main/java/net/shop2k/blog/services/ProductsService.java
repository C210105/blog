package net.shop2k.blog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.shop2k.blog.entitys.Products;
import net.shop2k.blog.repositorys.ProductsRepository;

/*
 * Products Service
 * ロジックを処理する
 */
@Service
public class ProductsService {
    
    @Autowired
    ProductsRepository productsRepository;

    /*
     * 全てProductsをクエリして取得する
     */
    public List <Products> finByAllProducts(){
        return productsRepository.findAll();
    }
} 
