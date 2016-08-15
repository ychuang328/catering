package com.weiwork.common.mongo.base;

import java.util.List;
import java.util.Map;

import com.weiwork.common.base.BaseEntity;

public interface IBaseDao<T extends BaseEntity> {
	public int deleteByPrimaryKey(Long id);
    public Long insert(T record);
    public void insertBatch(List<T> list);
    public T selectByPrimaryKey(Long id);
    public int updateByPrimaryKey(T record);
    public int updateBatchByPrimaryKey(List<T> list);
    public List<T> queryByParamMap(Map<String,Object> record);
    public int countByParamMap(Map<String,Object> record);
}
