package net.shop2k.blog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.shop2k.blog.entitys.Articles;
import net.shop2k.blog.entitys.Categorys;
import net.shop2k.blog.repositorys.ArticlesRepository;
import net.shop2k.blog.services.ArticlesService;
import net.shop2k.blog.services.CategorysService;

@SpringBootTest
class BlogApplicationTests {

	@Autowired
	CategorysService categorysService;
	@Autowired
	ArticlesService articlesService;
	@Autowired
	ArticlesRepository articlesRepository;

	@Test
	void contextLoads() {
	}

	// @Test
	// void category(){

	// 	List <Categorys> category = categorysService.findByAllCategorys();
	// 	assertNotNull(category, "ok");
	// 	assertEquals(2,category.size());
// }

	// @Test
	// void laytatcabaibao (){
		
	// 	List <Categorys> allCategorys = categorysService.findByAllCategorys();
	// 	assertEquals(1, allCategorys.size(), "Loii");
	// }

	/*
	 * titleを検索
	 */
	@Test
	void titleを検索(){

		String title = "rồng";
		List <Articles> search = articlesService.searchArticles(title);
		assertFalse(search.isEmpty());
	
	}

}
