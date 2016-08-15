package com.weiwork.common.base;

import java.util.List;
import java.util.Map;

public interface BaseMapper<T extends BaseEntity> {
	/**
	 * 根据主键删除
	 * 默认物理删除，逻辑删除请修改mapping.xml里面的sql
	 * @param id
	 * @return 影响条数
	 */
    public int deleteByPrimaryKey(Long id);
    
    /**
	 * 根据主键批量删除
	 * 默认关闭此功能，如果需要，请修改mapping.xml
	 * @param ids
	 * @return 影响条数
	 */
    public int deleteByPrimaryKeyBatch(Long... id);
    /**
     * 插入
     * @param record
     * @return 此方法返回的id不一定是主键，请在插入对象后使用对象主键
     */
    public Long insert(T record);
    /**
     * 批量插入
     * @param list
     * @return
     */
    public Long insertBatch(List<T> list);
    /**
     * 按主键查询
     * @param id
     * @return
     */
    public T selectByPrimaryKey(Long id);
    /**
     * 按主键更新对象
     * @param record
     * @return
     */
    public int updateByPrimaryKey(T record);
    /**
     * 按主键批量更新
     * @param list
     * @return
     */
    public int updateBatchByPrimaryKey(List<T> list);
    /**
     * 按条件查询
     * @param record
     * @return
     */
    public List<T> queryByParamMap(Map<String,Object> record);
    /**
     * 条件统计条数
     * @param record
     * @return
     */
    public int countByParamMap(Map<String,Object> record);
}
