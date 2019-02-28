package cn.itcast.goods.category.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.goods.category.domain.Category;
import cn.itcast.jdbc.TxQueryRunner;
/**
 * 分类持久层
 * @author 银涛
 *
 */
public class CategoryDao {
	private QueryRunner qr = new TxQueryRunner();
	/**
	 * 把一个map中的数据手动映射到Category中去
	 * @param map
	 * @return
	 */
	public Category toCategory(Map<String,Object> map) {
		//要将pid赋值到parent中
		Category category = CommonUtils.toBean(map, Category.class);
		String pid = (String) map.get("pid");
		if(null!=pid) {//如果父分类ID没有空
			//用一个父分类对象装载pid,再将父分类设置给category
			Category parent = new Category();
			parent.setCid(pid);
			category.setParent(parent);
		}
		return category;
	}
	/**
	 * 可以将多个map映射成多个category（List<Catgeory>）
	 * @param mapList
	 * @return
	 */
	public List<Category> toCategoryList(List<Map<String,Object>> mapList) {
		List<Category> categoryList = new ArrayList<Category>();
		for(Map<String,Object> map:mapList) {
			Category c = toCategory(map);
			categoryList.add(c);
		}
		return categoryList;
	}
	
	/**
	 * 返回所有分类
	 * @return
	 * @throws SQLException 
	 */
	public List<Category> findAll() throws SQLException{
		/**
		 * 1、查询出所有一级分类
		 */
		String sql = "select * from t_category where pid is null order by orderBy";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler());
		List<Category> parents = toCategoryList(mapList); 
		/**
		 * 2、遍历父分类，查询出其子分类
		 */
		for(Category parent:parents) {
			//查询出父分类中的子分类
			List<Category> children= findByParent(parent.getCid());
			//设置给父分类
			parent.setChildren(children);
		}
		return parents;
	}
	/**
	 * 返回父分类，但不带子分类
	 * @return
	 * @throws SQLException
	 */
	public List<Category> findParents() throws SQLException{
		/**
		 * 1、查询出所有一级分类
		 */
		String sql = "select * from t_category where pid is null order by orderBy";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler());
		return toCategoryList(mapList); 
	}
	/**
	 * 通过父分类的cid查询出子分类
	 * @param cid
	 * @return
	 * @throws SQLException 
	 */
	public List<Category> findByParent(String pid) throws SQLException{
		String sql = "select * from t_category where pid=? order by orderBy";
		List<Map<String,Object>> mapList= qr.query(sql, new MapListHandler(),pid);
		return toCategoryList(mapList);
	}
	
	/**
	 * 添加一级分类和二级分类的通用方法
	 * @param category
	 * @throws SQLException 
	 */
	public void add(Category category) throws SQLException {
		String sql ="insert into t_category(cid,cname,pid,`desc`) values(?,?,?,?)";
		String pid=null;
		if(null!=category.getParent()) {
			pid=category.getParent().getCid();
		}
		Object[] params = {category.getCid(),category.getCname(),pid,category.getDesc()};
		qr.update(sql, params);
	}
	
	/**
	 * 加载一级分类，也可加载二级分类
	 * @param cid
	 * @return
	 * @throws SQLException 
	 */
	public Category load(String cid) throws SQLException {
		String sql = "select * from t_category where cid=?";
		return toCategory(qr.query(sql, new MapHandler(),cid));
	}
	
	/**
	 * 修改分类
	 * 即可修改一级分类，也可修改二级分类
	 * @param category
	 * @throws SQLException 
	 */
	public void edit(Category category) throws SQLException {
		String sql = "update t_category set cname=?,pid=?,`desc`=? where cid=?";
		//防止空指针异常，采取赋值方法
		String pid=null;
		if(null!=category.getParent()) {
			pid= category.getParent().getCid();
		}
		Object[] params = {category.getCname(),pid,category.getDesc(),category.getCid()};
		qr.update(sql,params);
	}
	
	/**
	 * 按照指定父分类下子分类的个数
	 * @param pid
	 * @return
	 * @throws SQLException 
	 */
	public int findChildrenCountByParent(String pid) throws SQLException {
		String sql = "select count(*) from t_category where pid=?";
		Number childCount = (Number) qr.query(sql, new ScalarHandler(),pid);
		return childCount==null?0:childCount.intValue();
	}
	
	/**
	 * 删除分类
	 * @param cid
	 * @throws SQLException
	 */
	public void delete(String cid) throws SQLException {
		String sql = "delete from t_category where cid=?";
		qr.update(sql,cid);
	}
}
