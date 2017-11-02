package cn.emay.laiqiang.common.persistence;


import java.util.List;

/**
 * DAO֧����ʵ��
 * @author ThinkGem
 * @version 2014-05-16
 * @param <T>
 */
public interface CrudDao<T> extends BaseDao {

	/**
	 * ��ȡ��������
	 * @param id
	 * @return
	 */
	public T get(String id);
	
	/**
	 * ��ȡ��������
	 * @param entity
	 * @return
	 */
	public T get(T entity);
	
	/**
	 * ��ѯ�����б������Ҫ��ҳ�������÷�ҳ�����磺entity.setPage(new Page<T>());
	 * @param entity
	 * @return
	 */
	public List<T> findList(T entity);
	
	/**
	 * ��ѯ���������б�
	 * @param entity
	 * @return
	 */
	public List<T> findAllList(T entity);
	
	/**
	 * ��ѯ���������б�
	 * @see public List<T> findAllList(T entity)
	 * @return
	 */
	@Deprecated
	public List<T> findAllList();
	
	/**
	 * ��������
	 * @param entity
	 * @return
	 */
	public int insert(T entity);
	
	/**
	 * ��������
	 * @param entity
	 * @return
	 */
	public int update(T entity);
	
	/**
	 * ɾ�����ݣ�һ��Ϊ�߼�ɾ��������del_flag�ֶ�Ϊ1��
	 * @param id
	 * @see public int delete(T entity)
	 * @return
	 */
	@Deprecated
	public int delete(String id);
	
	/**
	 * ɾ�����ݣ�һ��Ϊ�߼�ɾ��������del_flag�ֶ�Ϊ1��
	 * @param entity
	 * @return
	 */
	public int delete(T entity);
	
}
