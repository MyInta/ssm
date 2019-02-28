package goods.admin.book.web.servlet;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.itcast.commons.CommonUtils;
import goods.book.domain.Book;
import goods.book.service.BookService;
import goods.category.domain.Category;
import goods.category.service.CategoryService;


public class AdminAddBookServlet extends HttpServlet{

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=utf-8");
		/**
		 * 1、commonfileupload上传的三步
		 */
		//创建工具
		FileItemFactory factory = new DiskFileItemFactory();
		/**
		 * 2、创建解析器对象
		 */
		ServletFileUpload sfu = new ServletFileUpload(factory);
		sfu.setFileSizeMax(80*1024);//设置单个文件上传上限为80kb
		/**
		 * 3、解析request,得到List<FileItem>
		 */
		List<FileItem> fileItemList = null;
		try {
			fileItemList = sfu.parseRequest(req);
		} catch (FileUploadException e) {
			//如果出现异常，说明单个上传文件超过了80kb
			error("错误信息：上传的文件超过了80kb",req,resp);
			return;
		}
		/**
		 * 4、把List<FileItem>封装到Book对象中
		 * 先将表单放到map 再转成book和category对象，在建立两者关系
		 */
		Map<String,Object> map = new HashMap<String,Object>();
		for(FileItem fileItem:fileItemList) {
			//如果为普通表单字段
			if(fileItem.isFormField()) {
				//获取表单对象字段名字
				map.put(fileItem.getFieldName(),fileItem.getString("UTF-8"));
			}
		}
		//将map中绝大部分数据封装到book中
		Book book = CommonUtils.toBean(map, Book.class);
		//把map中的cid封装到category中
		Category category = CommonUtils.toBean(map, Category.class);
		//建立两者的联系
		book.setCategory(category);
		/**
		 * 4.2将上传的图片保存起来
		 * 获取文件名：截取之
		 * 添加文件前缀，uuid
		 * 校验文件扩展名jpg
		 * 校验图片的尺寸
		 * 指定图片的保存路径 servletContext.getRealPath()
		 * 把图片路径保存给book对象
		 */
		//获取文件名
		FileItem fileItem = fileItemList.get(1);//获取大图
		String fileName = fileItem.getName();
		//截取文件名，因为部分游览器上传的是绝对路径
		int index = fileName.lastIndexOf("\\");
		if(-1!=index) {
			fileName = fileName.substring(index+1);
		}
		//文件添加前缀
		fileName = CommonUtils.uuid()+"_"+fileName;
		//校验文件名的扩展名
		if(!fileName.toLowerCase().endsWith(".jpg")) {
			error("上传的图片必需是jpg扩展名", req, resp);
			return;
		}
		//校验图片的尺寸:先保存上传的的图片；图片new成对象（如Image）
		String savePath = this.getServletContext().getRealPath("/book_img");
		//TODO 图片保存地址
		System.out.println(savePath);
		//创建目标文件
		File destFile = new File(savePath,fileName);
		try {
			fileItem.write(destFile);//把临时文件重定向到指定路径，再删除临时文件
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		//校验尺寸
		ImageIcon icon = new ImageIcon(destFile.getAbsolutePath());
		Image image = icon.getImage();
		//如果长宽大于350就显示错误
		if(image.getWidth(null)>350||image.getHeight(null)>350) {
			error("您上传的图片尺寸超出350*350！", req, resp);
			destFile.delete();//删除图片
			return;
		}
		//把图片的路径设置给book对象
		book.setImage_w("book_img/"+fileName);
		
		
		//获取文件名
		fileItem = fileItemList.get(2);//获取小图
		fileName = fileItem.getName();
		//截取文件名，因为部分游览器上传的是绝对路径
		index = fileName.lastIndexOf("\\");
		if(-1!=index) {
			fileName = fileName.substring(index+1);
		}
		//文件添加前缀
		fileName = CommonUtils.uuid()+"_"+fileName;
		//校验文件名的扩展名
		if(!fileName.toLowerCase().endsWith(".jpg")) {
			error("上传的图片必需是jpg扩展名", req, resp);
			return;
		}
		//校验图片的尺寸:先保存上传的的图片；图片new成对象（如Image）
		savePath = this.getServletContext().getRealPath("/book_img");
		//创建目标文件
		destFile = new File(savePath,fileName);
		try {
			fileItem.write(destFile);//把临时文件重定向到指定路径，再删除临时文件
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		//校验尺寸
		icon = new ImageIcon(destFile.getAbsolutePath());
		image = icon.getImage();
		//如果长宽大于350就显示错误
		if(image.getWidth(null)>250||image.getHeight(null)>250) {
			error("您上传的图片尺寸超出250*250！", req, resp);
			destFile.delete();//删除图片
			return;
		}
		//把图片的路径设置给book对象
		book.setImage_b("book_img/"+fileName);
		
		//调用service完成保存
		book.setBid(CommonUtils.uuid());
		BookService bookService = new BookService();
		bookService.add(book);
		
		//保存成功信息，转发到msg.jsp
		req.setAttribute("msg", "保存成功！");
		req.getRequestDispatcher("/adminjsps/msg.jsp").forward(req, resp);
	}
	/**
	 * 设置错误信息
	 * @param msg
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void error(String msg,HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("msg", msg);
		req.setAttribute("parents", new CategoryService().findParents());
		req.getRequestDispatcher("/adminjsps/admin/book/add.jsp").forward(req, resp);
	}
	
		
}
