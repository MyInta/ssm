package goods.book.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.commons.CommonUtils;
import goods.book.domain.Book;
import goods.category.domain.Category;
import goods.pager.Expression;
import goods.pager.PageBean;
import goods.pager.PageContants;
import cn.itcast.jdbc.TxQueryRunner;

public class BookDao {
	private QueryRunner qr = new TxQueryRunner();
	
	/**
	 * ɾ��ͼ��
	 * @param bid
	 * @throws SQLException 
	 */
	public void delete(String bid) throws SQLException {
		String sql = "delete from t_book where bid=?";
		qr.update(sql,bid);
	}
	
	/**
	 * �޸�ͼ��
	 * @param book
	 * 		String sql = "insert into t_book(bid,bname,author,price,currPrice," +
				"discount,press,publishtime,edition,pageNum,wordNum,printtime," +
				"booksize,paper,cid,image_w,image_b)" +
	 * @throws SQLException 
	 */
	public void edit(Book book) throws SQLException {
		String sql = "update t_book set bname=?,author=?,price=?,currPrice=?," +
				"discount=?,press=?,publishtime=?,edition=?,pageNum=?,wordNum=?," +
				"printtime=?,booksize=?,paper=?,cid=? where bid=?";
		Object[] params = {book.getBname(),book.getAuthor(),
				book.getPrice(),book.getCurrPrice(),book.getDiscount(),
				book.getPress(),book.getPublishtime(),book.getEdition(),
				book.getPageNum(),book.getWordNum(),book.getPrinttime(),
				book.getBooksize(),book.getPaper(), 
				book.getCategory().getCid(),book.getBid()};
		qr.update(sql, params);
	}
	/**
	 * ����bid��ѯ
	 * @param Bid
	 * @return
	 * @throws SQLException
	 */
	public Book findByBid(String bid) throws SQLException{
		String sql = "select * from t_book b,t_category c where b.cid=c.cid and b.bid=?;";
		//һ�м�¼�У������˺ܶ��book���ԣ�����һ��cid����
		Map<String,Object> map = qr.query(sql, new MapHandler(),bid);
		//��map�г���cid������ȫ��ӳ�䵽book��
		Book book = CommonUtils.toBean(map, Book.class);
		//��map�е�cid����ӳ�䵽category��
		Category category = CommonUtils.toBean(map, Category.class);
		//���߽�����ϵ
		book.setCategory(category);
		
		//��pid��ȡ�󣬴���һ��category parent ��pid��������Ȼ���parent����category
		if(null!=map.get("pid")) {
			Category parent = new Category();
			parent.setCid((String) map.get("pid"));
			category.setParent(parent);
		}
		return book;
	}
	
	/**
	 * ��ѯָ��������ͼ����Ŀ
	 * @param cid
	 * @return
	 * @throws SQLException 
	 */
	public int findBookCountByCategory(String cid) throws SQLException {
		String sql = "select count(*) from t_book where cid=?";
		Number bookCount = (Number) qr.query(sql, new ScalarHandler(),cid);
		return null==bookCount?0:bookCount.intValue();
	}
	
	/**
	 * �������ѯ
	 * @param cid
	 * @param pc
	 * @return
	 * @throws SQLException 
	 */
	public PageBean<Book> findByCategory(String cid,int pc) throws SQLException{
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("cid","=",cid));
		return findByCriteria(exprList,pc);
	}
	/**
	 * ������ģ����ѯ
	 * @param bname
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Book> findByBname(String bname,int pc) throws SQLException{
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("bname","like","%"+bname+"%"));
		return findByCriteria(exprList,pc);
	}
	/**
	 * �����߲�ѯ
	 * @param author
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Book> findByAuthor(String author,int pc) throws SQLException{
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("author","like","%"+author+"%"));
		return findByCriteria(exprList,pc);
	}
	/**
	 * ���������ѯ
	 * @param press
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Book> findByPress(String press,int pc) throws SQLException{
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("press","like","%"+press+"%"));
		return findByCriteria(exprList,pc);
	}
	/**
	 * ���������ѯ
	 * @param criteria
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Book> findByCombination(Book criteria,int pc) throws SQLException{
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("bname","like","%"+criteria.getBname()+"%"));
		exprList.add(new Expression("author","like","%"+criteria.getAuthor()+"%"));
		exprList.add(new Expression("press","like","%"+criteria.getPress()+"%"));
		return findByCriteria(exprList,pc);
	}
	
	/**
	 * ͨ�õĲ�ѯ����
	 * @param exprList
	 * @param pc
	 * @return
	 * @throws SQLException 
	 */
	private PageBean<Book> findByCriteria(List<Expression> exprList,int pc) throws SQLException{
		/**
		 * 1���õ�ps
		 * 2���õ�tr
		 * 3���õ�beanList
		 * 4������PageBean������
		 */
		/**
		 * 1���õ�ps
		 */
		int ps = PageContants.BOOK_PAGE_SIZE;
		/**
		 * 2��ͨ��exprList������where�Ӿ�
		 * 
		 */
		StringBuilder whereSql =new StringBuilder(" where 1=1 ");
		List<Object> params = new ArrayList<Object>();//sql����ж�Ӧռλ����ֵ
		for(Expression expr:exprList) {
			//���������and��ͷ�����������ơ��������������������Ϊis null����ֵ��
			whereSql.append(" and ").append(expr.getName()).append(" ")
			.append(expr.getOperator()).append(" ");
			// where 1=1 and bid = 
			if(!expr.getOperator().equals("is null")) {//���ֵ�ǿ�
				//�����Ϊis null׷���ʺã�params��Ӷ�Ӧֵ
				whereSql.append("?");
				params.add(expr.getValue());
			}
		}
		/**
		 * 3���õ��ܼ�¼��tr
		 */
		String sql = "select count(*) from t_book"+whereSql;
		Number number = (Number) qr.query(sql, new ScalarHandler(),params.toArray());
		int tr = number.intValue();//�õ��ܼ�¼��
		
		/**
		 * 4�����beanList,����ǰҳ��¼ ����sql����orderBy��һ���ֶ�
		 */
		sql = "select * from t_book"+whereSql+" order by orderBy limit ?,?";
		params.add((pc-1)*ps);//��һ���ʺ�,��ǰҳ��ż�һ����ҳ���¼������������ս��Ϊ���м�¼�±�
		params.add(ps);//�ڶ����ʺ�.һ����ѯ���У���ÿҳ��¼��
		List<Book> beanList = qr.query(sql, new BeanListHandler<Book>(Book.class),params.toArray());
		/**
		 * 5������PageBean�����ò���
		 */
		PageBean<Book> pb = new PageBean();
		//����pageBean û��url ��servletʵ��
		pb.setBeanList(beanList);
		pb.setPc(pc);
		pb.setPs(ps);
		pb.setTr(tr);
		return pb;
	}
	/**
	 * ���ͼ��
	 * @param book
	 * @throws SQLException 
	 */
	public void add(Book book) throws SQLException {
		String sql = "insert into t_book(bid,bname,author,price,currPrice,"+
					"discount,press,publishtime,edition,pageNum,wordNum,printtime,"+
				"booksize,paper,cid,image_w,image_b)"+
					" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] params = {book.getBid(),book.getBname(),book.getAuthor(),
				book.getPageNum(),book.getCurrPrice(),book.getDiscount(),
				book.getPress(),book.getPublishtime(),book.getEdition(),
				book.getPageNum(),book.getWordNum(),book.getPrinttime(),
				book.getBooksize(),book.getPaper(),
				book.getCategory().getCid(),book.getImage_w(),book.getImage_b()};
		qr.update(sql,params);
	}
	public static void main(String[] args) {
		BookDao bookDao = new BookDao();
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("bid","=","1"));
		exprList.add(new Expression("bname","like","%java%"));
		exprList.add(new Expression("edition","is null",null));
		
//		bookDao.findByCriteria(exprList, 10);
	}

}
