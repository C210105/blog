package net.shop2k.blog;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.shop2k.blog.entitys.Articles;
import net.shop2k.blog.entitys.Comment;
import net.shop2k.blog.entitys.Manager;
import net.shop2k.blog.repositorys.CommentRepository;
import net.shop2k.blog.repositorys.ManagerRepository;
import net.shop2k.blog.services.CommentService;
import net.shop2k.blog.services.ManagerService;

@SpringBootTest
class BlogApplicationTests {

	// @Autowired
	// CategorysService categorysService;
	// @Autowired
	// ArticlesService articlesService;
	// @Autowired
	// ArticlesRepository articlesRepository;
	@Autowired
	private CommentService commentService;

	@Autowired
	private ManagerService managerService;

	// @Test
	// void createComment(){
	// 	Comment comments = new Comment();
	// 	comments.setContent("IHI");
	// 	commentService.createComment(comments);
	// }
	// @Test
	// void deleteComment(){
	// 	commentService.deleteComment((long) 1);
	// }


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
