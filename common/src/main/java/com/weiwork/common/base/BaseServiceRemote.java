package com.weiwork.common.base;

import java.util.List;
import java.util.Map;

import cn.vko.component.pageframework.pagination.PageModel;
import cn.vko.component.pageframework.pagination.Pagination;

public interface BaseServiceRemote<T extends BaseEntity> {
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
    public Long insertBatch(List<T> entityList);
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
     * 更新或保存对象
     * @param entity
     * @return 返回对象主键
     */
    public Long insertOrUpdate(T entity);
    
    /**
     * 删除对象
     * @param id
     * @return 返回影响记录条数
     */
    public int delete(Long id);
    

    /**
     * 批量删除对象
     * @param id
     * @return 返回影响记录条数
     */
    public int deleteBatch(Long... id);
    
    /**
     * 根据主键查询对象
     * @param id
     * @return 返回对象
     */
    public T getById(Long id);
    
    /**
     * 按条件查询对象列表
     * @param param
     * @return 返回对象集合
     */
	public List<T> queryByParamMap(Map<String,Object>  param);
	
	/**
	 * 分页查询对象
	 * @param pager 
	 * @param params
	 * @return 返回Pagination<T>队形
	 */
    public  Pagination<T> queryPageByParamMap(PageModel pager, Map<String,Object> params);
}
