/*
 * StoreTypeService.java
 * 北京名雅轩有限公司
 * 
 */
package com.weiwork.catering.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.weiwork.catering.mapper.IStoreTypeMapper;
import com.weiwork.catering.model.StoreType;
import com.weiwork.catering.service.IStoreTypeService;
import cn.vko.common.base.impl.BaseServiceImpl;

/**
 * service接口实现.餐饮类别字典
 * @author 微作
 */
@Service
public class StoreTypeServiceImpl  extends BaseServiceImpl<StoreType,IStoreTypeMapper> implements IStoreTypeService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
	private IStoreTypeMapper storeTypeMapper;
	
	@Override
	public IStoreTypeMapper getDefaulteMapper() {
		return storeTypeMapper;
	}
 
}
