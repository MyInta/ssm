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
		 * 1��commonfileupload�ϴ�������
		 */
		//��������
		FileItemFactory factory = new DiskFileItemFactory();
		/**
		 * 2����������������
		 */
		ServletFileUpload sfu = new ServletFileUpload(factory);
		sfu.setFileSizeMax(80*1024);//���õ����ļ��ϴ�����Ϊ80kb
		/**
		 * 3������request,�õ�List<FileItem>
		 */
		List<FileItem> fileItemList = null;
		try {
			fileItemList = sfu.parseRequest(req);
		} catch (FileUploadException e) {
			//��������쳣��˵�������ϴ��ļ�������80kb
			error("������Ϣ���ϴ����ļ�������80kb",req,resp);
			return;
		}
		/**
		 * 4����List<FileItem>��װ��Book������
		 * �Ƚ����ŵ�map ��ת��book��category�����ڽ������߹�ϵ
		 */
		Map<String,Object> map = new HashMap<String,Object>();
		for(FileItem fileItem:fileItemList) {
			//���Ϊ��ͨ���ֶ�
			if(fileItem.isFormField()) {
				//��ȡ�������ֶ�����
				map.put(fileItem.getFieldName(),fileItem.getString("UTF-8"));
			}
		}
		//��map�о��󲿷����ݷ�װ��book��
		Book book = CommonUtils.toBean(map, Book.class);
		//��map�е�cid��װ��category��
		Category category = CommonUtils.toBean(map, Category.class);
		//�������ߵ���ϵ
		book.setCategory(category);
		/**
		 * 4.2���ϴ���ͼƬ��������
		 * ��ȡ�ļ�������ȡ֮
		 * ����ļ�ǰ׺��uuid
		 * У���ļ���չ��jpg
		 * У��ͼƬ�ĳߴ�
		 * ָ��ͼƬ�ı���·�� servletContext.getRealPath()
		 * ��ͼƬ·�������book����
		 */
		//��ȡ�ļ���
		FileItem fileItem = fileItemList.get(1);//��ȡ��ͼ
		String fileName = fileItem.getName();
		//��ȡ�ļ�������Ϊ�����������ϴ����Ǿ���·��
		int index = fileName.lastIndexOf("\\");
		if(-1!=index) {
			fileName = fileName.substring(index+1);
		}
		//�ļ����ǰ׺
		fileName = CommonUtils.uuid()+"_"+fileName;
		//У���ļ�������չ��
		if(!fileName.toLowerCase().endsWith(".jpg")) {
			error("�ϴ���ͼƬ������jpg��չ��", req, resp);
			return;
		}
		//У��ͼƬ�ĳߴ�:�ȱ����ϴ��ĵ�ͼƬ��ͼƬnew�ɶ�����Image��
		String savePath = this.getServletContext().getRealPath("/book_img");
		//TODO ͼƬ�����ַ
		System.out.println(savePath);
		//����Ŀ���ļ�
		File destFile = new File(savePath,fileName);
		try {
			fileItem.write(destFile);//����ʱ�ļ��ض���ָ��·������ɾ����ʱ�ļ�
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		//У��ߴ�
		ImageIcon icon = new ImageIcon(destFile.getAbsolutePath());
		Image image = icon.getImage();
		//����������350����ʾ����
		if(image.getWidth(null)>350||image.getHeight(null)>350) {
			error("���ϴ���ͼƬ�ߴ糬��350*350��", req, resp);
			destFile.delete();//ɾ��ͼƬ
			return;
		}
		//��ͼƬ��·�����ø�book����
		book.setImage_w("book_img/"+fileName);
		
		
		//��ȡ�ļ���
		fileItem = fileItemList.get(2);//��ȡСͼ
		fileName = fileItem.getName();
		//��ȡ�ļ�������Ϊ�����������ϴ����Ǿ���·��
		index = fileName.lastIndexOf("\\");
		if(-1!=index) {
			fileName = fileName.substring(index+1);
		}
		//�ļ����ǰ׺
		fileName = CommonUtils.uuid()+"_"+fileName;
		//У���ļ�������չ��
		if(!fileName.toLowerCase().endsWith(".jpg")) {
			error("�ϴ���ͼƬ������jpg��չ��", req, resp);
			return;
		}
		//У��ͼƬ�ĳߴ�:�ȱ����ϴ��ĵ�ͼƬ��ͼƬnew�ɶ�����Image��
		savePath = this.getServletContext().getRealPath("/book_img");
		//����Ŀ���ļ�
		destFile = new File(savePath,fileName);
		try {
			fileItem.write(destFile);//����ʱ�ļ��ض���ָ��·������ɾ����ʱ�ļ�
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		//У��ߴ�
		icon = new ImageIcon(destFile.getAbsolutePath());
		image = icon.getImage();
		//����������350����ʾ����
		if(image.getWidth(null)>250||image.getHeight(null)>250) {
			error("���ϴ���ͼƬ�ߴ糬��250*250��", req, resp);
			destFile.delete();//ɾ��ͼƬ
			return;
		}
		//��ͼƬ��·�����ø�book����
		book.setImage_b("book_img/"+fileName);
		
		//����service��ɱ���
		book.setBid(CommonUtils.uuid());
		BookService bookService = new BookService();
		bookService.add(book);
		
		//����ɹ���Ϣ��ת����msg.jsp
		req.setAttribute("msg", "����ɹ���");
		req.getRequestDispatcher("/adminjsps/msg.jsp").forward(req, resp);
	}
	/**
	 * ���ô�����Ϣ
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
