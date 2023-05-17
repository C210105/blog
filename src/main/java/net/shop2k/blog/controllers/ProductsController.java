package net.shop2k.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import net.shop2k.blog.entitys.Products;
import net.shop2k.blog.services.ProductsService;

@Controller
@RequestMapping(path = "/blog/v1/categorys/articles/")
public class ProductsController {
    
    @Autowired
    ProductsService productsService;

}
