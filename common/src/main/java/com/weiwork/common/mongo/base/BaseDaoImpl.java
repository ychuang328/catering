package com.weiwork.common.mongo.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.weiwork.common.base.BaseEntity;

public class BaseDaoImpl<T  extends BaseEntity> implements  IBaseDao<T>{
	MongoTemplate mongoTemplate;
	private Class<T> entityClass;  
	
	@SuppressWarnings("unchecked")
	public  BaseDaoImpl(){
		Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();  
        entityClass = (Class<T>) params[0]; 
	}
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return mongoTemplate.remove(new Query(Criteria.where("id").is(id)), entityClass).getN();
	}

	@Override
	public Long insert(T record) {
		// TODO 需要先生成主键，返回主键
		mongoTemplate.insert(record);
		return null;
	}

	@Override
	public void insertBatch(List<T> list) {
		// TODO 需要先生成主键，需要返回主键
		mongoTemplate.insert(list, entityClass);
	}

	@Override
	public T selectByPrimaryKey(Long id) {
		return (T) mongoTemplate.findOne(new Query(Criteria.where("id").is(id)),
        		entityClass);
	}

	@Override
	public int updateByPrimaryKey(T record) {
		// TODO 这里尚未完全实现
		Long id = record.getId() ;
		return mongoTemplate.updateFirst(
                new Query(Criteria.where("id").is(id)),
                Update.update("name", ""), entityClass).getN();
	}

	@Override
	public int updateBatchByPrimaryKey(List<T> list) {
		int count = 0;
		for(T t : list){
			count += updateByPrimaryKey(t);
		}
		return count;
	}

	@Override
	public List<T> queryByParamMap(Map<String, Object> record) {
		 // TODO 这里尚未完全实现
		 return mongoTemplate.findAll(entityClass);
	}

	@Override
	public int countByParamMap(Map<String, Object> record) {
		return 0;
	}
}
