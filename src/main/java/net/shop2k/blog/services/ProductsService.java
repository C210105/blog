package net.shop2k.blog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.shop2k.blog.entitys.Products;
import net.shop2k.blog.repositorys.ProductsRepository;

@Service
public class ProductsService {
    
    @Autowired
    ProductsRepository productsRepository;

    public List <Products> finByAllProducts(){
        return productsRepository.findAll();
    }
} 
