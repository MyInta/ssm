package goods.category.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import goods.category.domain.Category;
import goods.category.service.CategoryService;
import cn.itcast.servlet.BaseServlet;

public class CategoryServlet extends BaseServlet{
	
	private CategoryService categoryService = new CategoryService(); 
	/**
	 * 查询所有分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		List<Category> parents = categoryService.findAll();
		req.setAttribute("parents", parents);
		return "/jsps/left.jsp";
	}
}
