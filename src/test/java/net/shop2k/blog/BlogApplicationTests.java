package net.shop2k.blog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.shop2k.blog.entitys.Categorys;
import net.shop2k.blog.services.CategorysService;

@SpringBootTest
class BlogApplicationTests {

	@Autowired
	CategorysService categorysService;

	@Test
	void contextLoads() {
	}

	// @Test
	// void category(){

	// 	List <Categorys> category = categorysService.findByAllCategorys();
	// 	assertNotNull(category, "ok");
	// 	assertEquals(2,category.size());
// }

	@Test
	void laytatcabaibao (){
		
		List <Categorys> allCategorys = categorysService.findByAllCategorys();
		assertEquals(1, allCategorys.size(), "Loii");
	}

}
