package com.weiwork.common.mongo.base;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.vko.component.pageframework.pagination.PageModel;
import cn.vko.component.pageframework.pagination.Pagination;

import com.weiwork.common.base.BaseEntity;
import com.weiwork.common.base.CommonPageModel;
import com.weiwork.common.exception.ServiceException;

public abstract class BaseServiceImpl<T extends BaseEntity,DAO extends IBaseDao<T>> implements IBaseService<T,DAO> {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Override
    @Transactional(propagation=Propagation.REQUIRED)
	public Long insert(T entity) {
		if(entity == null){
			String msg = "操作数据对象不可以为空";
			logger.error(msg);
			throw new ServiceException(1,msg);
		}
		logger.debug("插入对象：{}",entity);
		getDefaultDao().insert(entity);
		return entity.getId();
	}

	@Override
    @Transactional(propagation=Propagation.REQUIRED)
	public void insertBatch(List<T> entityList) {
		if(entityList == null || entityList.size() < 1){
			String msg = "操作数据集合不可以为空";
			logger.error(msg);
			throw new ServiceException(1,msg);
		}
		logger.debug("批量插入对象：{} 条",entityList.size());
		getDefaultDao().insertBatch(entityList);
	}

	@Override
    @Transactional(propagation=Propagation.REQUIRED)
	public int updateByPrimaryKey(T entity) {
		if(entity == null){
			String msg = "操作数据不可以为空";
			logger.error(msg);
			throw new ServiceException(1,msg);
		}
		logger.info("更新对象：{}",entity);
		return getDefaultDao().updateByPrimaryKey(entity);
	}

	@Override
    @Transactional(propagation=Propagation.REQUIRED)
	public int updateBatchByPrimaryKey(List<T> entityList) {
		if(entityList == null || entityList.size() < 1){
			String msg = "操作数据集合不可以为空";
			logger.error(msg);
			throw new ServiceException(1,msg);
		}
		logger.debug("批量更新对象：{} 条",entityList.size());
		return getDefaultDao().updateBatchByPrimaryKey(entityList);
	}

	@Override
    @Transactional(propagation=Propagation.REQUIRED)
	public Long insertOrUpdate(T entity) {
		if(entity == null){
			String msg = "操作数据对象不可以为空";
			logger.error(msg);
			throw new ServiceException(1,msg);
		}
		if(entity.getId() != null){
			logger.debug("id 不为空，执行更新");
			this.updateByPrimaryKey(entity);
		}else{
			logger.debug("id 为空，执行插入");
			this.insert(entity);
		}
		return entity.getId();
	}

	@Override
    @Transactional(propagation=Propagation.REQUIRED)
	public int delete(Long id) {
		if(id == null ){
			String msg = "id不可以为空";
			logger.error(msg);
			throw new ServiceException(1,msg);
		}
		return getDefaultDao().deleteByPrimaryKey(id);
	}

	@Override
	public T getById(Long id) {
		if(id == null){
			String msg = "id不可以为空";
			logger.error(msg);
			throw new ServiceException(1,msg);
		}
		return getDefaultDao().selectByPrimaryKey(id);
	}

	@Override
	public List<T> queryByParamMap(Map<String, Object> param) {
		if(param == null){
			String msg = "参数Map不可以为空";
			logger.error(msg);
			throw new ServiceException(1,msg);
		}
		return getDefaultDao().queryByParamMap(param);
	}

	@Override
	public Pagination<T> queryPageByParamMap(PageModel pager, Map<String, Object> params) {
		if(pager == null){
			String msg = "参数pager不可以为空";
			logger.error(msg);
			throw new ServiceException(1,msg);
		}
		if(params == null){
			String msg = "参数params不可以为空";
			logger.error(msg);
			throw new ServiceException(1,msg);
		}
		Pagination<T> pn = new Pagination<T>();
		CommonPageModel commonPageParam = new CommonPageModel(pager);
        params.put("pParam", commonPageParam);
        List<T> listBean = getDefaultDao().queryByParamMap(params);
        Integer count = getDefaultDao().countByParamMap(params);
        pn.setRows(listBean);
        pn.setTotal(count);
        pn.setPageIndex( pager.getPage() );
        pn.setPageRow( pager.getRows() );
		return pn;
	}
}
