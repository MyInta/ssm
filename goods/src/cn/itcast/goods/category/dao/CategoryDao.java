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
 * ����־ò�
 * @author ����
 *
 */
public class CategoryDao {
	private QueryRunner qr = new TxQueryRunner();
	/**
	 * ��һ��map�е������ֶ�ӳ�䵽Category��ȥ
	 * @param map
	 * @return
	 */
	public Category toCategory(Map<String,Object> map) {
		//Ҫ��pid��ֵ��parent��
		Category category = CommonUtils.toBean(map, Category.class);
		String pid = (String) map.get("pid");
		if(null!=pid) {//���������IDû�п�
			//��һ�����������װ��pid,�ٽ����������ø�category
			Category parent = new Category();
			parent.setCid(pid);
			category.setParent(parent);
		}
		return category;
	}
	/**
	 * ���Խ����mapӳ��ɶ��category��List<Catgeory>��
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
	 * �������з���
	 * @return
	 * @throws SQLException 
	 */
	public List<Category> findAll() throws SQLException{
		/**
		 * 1����ѯ������һ������
		 */
		String sql = "select * from t_category where pid is null order by orderBy";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler());
		List<Category> parents = toCategoryList(mapList); 
		/**
		 * 2�����������࣬��ѯ�����ӷ���
		 */
		for(Category parent:parents) {
			//��ѯ���������е��ӷ���
			List<Category> children= findByParent(parent.getCid());
			//���ø�������
			parent.setChildren(children);
		}
		return parents;
	}
	/**
	 * ���ظ����࣬�������ӷ���
	 * @return
	 * @throws SQLException
	 */
	public List<Category> findParents() throws SQLException{
		/**
		 * 1����ѯ������һ������
		 */
		String sql = "select * from t_category where pid is null order by orderBy";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler());
		return toCategoryList(mapList); 
	}
	/**
	 * ͨ���������cid��ѯ���ӷ���
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
	 * ���һ������Ͷ��������ͨ�÷���
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
	 * ����һ�����࣬Ҳ�ɼ��ض�������
	 * @param cid
	 * @return
	 * @throws SQLException 
	 */
	public Category load(String cid) throws SQLException {
		String sql = "select * from t_category where cid=?";
		return toCategory(qr.query(sql, new MapHandler(),cid));
	}
	
	/**
	 * �޸ķ���
	 * �����޸�һ�����࣬Ҳ���޸Ķ�������
	 * @param category
	 * @throws SQLException 
	 */
	public void edit(Category category) throws SQLException {
		String sql = "update t_category set cname=?,pid=?,`desc`=? where cid=?";
		//��ֹ��ָ���쳣����ȡ��ֵ����
		String pid=null;
		if(null!=category.getParent()) {
			pid= category.getParent().getCid();
		}
		Object[] params = {category.getCname(),pid,category.getDesc(),category.getCid()};
		qr.update(sql,params);
	}
	
	/**
	 * ����ָ�����������ӷ���ĸ���
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
	 * ɾ������
	 * @param cid
	 * @throws SQLException
	 */
	public void delete(String cid) throws SQLException {
		String sql = "delete from t_category where cid=?";
		qr.update(sql,cid);
	}
}
