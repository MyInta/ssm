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
	 * 删除图书
	 * @param bid
	 * @throws SQLException 
	 */
	public void delete(String bid) throws SQLException {
		String sql = "delete from t_book where bid=?";
		qr.update(sql,bid);
	}
	
	/**
	 * 修改图书
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
	 * 按照bid查询
	 * @param Bid
	 * @return
	 * @throws SQLException
	 */
	public Book findByBid(String bid) throws SQLException{
		String sql = "select * from t_book b,t_category c where b.cid=c.cid and b.bid=?;";
		//一行记录中，包含了很多的book属性，还有一个cid属性
		Map<String,Object> map = qr.query(sql, new MapHandler(),bid);
		//将map中除了cid的属性全部映射到book中
		Book book = CommonUtils.toBean(map, Book.class);
		//将map中的cid属性映射到category中
		Category category = CommonUtils.toBean(map, Category.class);
		//两者建立联系
		book.setCategory(category);
		
		//把pid获取后，创建一个category parent 把pid赋给它，然后把parent赋给category
		if(null!=map.get("pid")) {
			Category parent = new Category();
			parent.setCid((String) map.get("pid"));
			category.setParent(parent);
		}
		return book;
	}
	
	/**
	 * 查询指定分类下图书数目
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
	 * 按分类查询
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
	 * 按书名模糊查询
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
	 * 按作者查询
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
	 * 按出版社查询
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
	 * 组合条件查询
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
	 * 通用的查询方法
	 * @param exprList
	 * @param pc
	 * @return
	 * @throws SQLException 
	 */
	private PageBean<Book> findByCriteria(List<Expression> exprList,int pc) throws SQLException{
		/**
		 * 1、得到ps
		 * 2、得到tr
		 * 3、得到beanList
		 * 4、创建PageBean，返回
		 */
		/**
		 * 1、得到ps
		 */
		int ps = PageContants.BOOK_PAGE_SIZE;
		/**
		 * 2、通过exprList来生成where子句
		 * 
		 */
		StringBuilder whereSql =new StringBuilder(" where 1=1 ");
		List<Object> params = new ArrayList<Object>();//sql语句中对应占位符的值
		for(Expression expr:exprList) {
			//添加条件以and开头，条件的名称、运算符（其中运算符如果为is null则无值）
			whereSql.append(" and ").append(expr.getName()).append(" ")
			.append(expr.getOperator()).append(" ");
			// where 1=1 and bid = 
			if(!expr.getOperator().equals("is null")) {//如果值非空
				//运算符为is null追加问好，params添加对应值
				whereSql.append("?");
				params.add(expr.getValue());
			}
		}
		/**
		 * 3、得到总记录数tr
		 */
		String sql = "select count(*) from t_book"+whereSql;
		Number number = (Number) qr.query(sql, new ScalarHandler(),params.toArray());
		int tr = number.intValue();//得到总记录数
		
		/**
		 * 4、获得beanList,即当前页记录 其中sql语句的orderBy是一个字段
		 */
		sql = "select * from t_book"+whereSql+" order by orderBy limit ?,?";
		params.add((pc-1)*ps);//第一个问号,当前页编号减一乘以页面记录数量，获得最终结果为首行记录下标
		params.add(ps);//第二个问号.一共查询几行，即每页记录数
		List<Book> beanList = qr.query(sql, new BeanListHandler<Book>(Book.class),params.toArray());
		/**
		 * 5、创建PageBean，设置参数
		 */
		PageBean<Book> pb = new PageBean();
		//其中pageBean 没有url 由servlet实现
		pb.setBeanList(beanList);
		pb.setPc(pc);
		pb.setPs(ps);
		pb.setTr(tr);
		return pb;
	}
	/**
	 * 添加图书
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
