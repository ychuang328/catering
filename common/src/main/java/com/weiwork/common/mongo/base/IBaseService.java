package com.weiwork.common.mongo.base;

import java.util.List;
import java.util.Map;

import cn.vko.component.pageframework.pagination.PageModel;
import cn.vko.component.pageframework.pagination.Pagination;

import com.weiwork.common.base.BaseEntity;

public interface IBaseService<T extends BaseEntity,DAO extends IBaseDao<T>> {
	public abstract DAO getDefaultDao();
	/**
	 * 返回对象主键
	 * @param entity
	 * @return
	 */
    public Long insert(T entity);
    
    /**
     * 返回插入记录条数
     * @param entityList
     * @return
     */
    public void insertBatch(List<T> entityList);
    /**
     * 返回影响记录条数
     * @param entity
     * @return
     */
    public int updateByPrimaryKey(T entity);
    /**
     * 返回影响记录条数
     * @param entityList
     * @return
     */
    public int updateBatchByPrimaryKey(List<T> entityList);
    /**
     * 
     * @param entity
     * @return 返回对象主键
     */
    public Long insertOrUpdate(T entity);
    /**
     * 
     * @param id
     * @return 返回影响记录条数
     */
    public int delete(Long id);
    /**
     * 
     * @param id
     * @return 返回对象
     */
    public T getById(Long id);
    /**
     * 
     * @param param
     * @return 返回对象集合
     */
	public List<T> queryByParamMap(Map<String,Object>  param);
	/**
	 * 
	 * @param pager
	 * @param params
	 * @return 返回Pagination<T>队形
	 */
    public  Pagination<T> queryPageByParamMap(PageModel pager, Map<String,Object> params);
}
