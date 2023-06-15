package net.shop2k.blog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogApplicationTests {

	// @Autowired
	// CategorysService categorysService;
	// @Autowired
	// ArticlesService articlesService;
	// @Autowired
	// ArticlesRepository articlesRepository;

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
	// @Test
	// void titleを検索(){

	// 	String title = "rồng";
	// 	List <Articles> search = articlesService.searchArticles(title);
	// 	assertFalse(search.isEmpty());
	
	// }

}
