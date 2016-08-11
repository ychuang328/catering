/*
 * StoreService.java
 * 北京名雅轩有限公司
 * 
 */
package com.weiwork.catering.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.weiwork.catering.mapper.IStoreMapper;
import com.weiwork.catering.model.Store;
import com.weiwork.catering.service.IStoreService;
import cn.vko.common.base.impl.BaseServiceImpl;

/**
 * service接口实现.商铺表
 * @author 微作
 */
@Service
public class StoreServiceImpl  extends BaseServiceImpl<Store,IStoreMapper> implements IStoreService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
	private IStoreMapper storeMapper;
	
	@Override
	public IStoreMapper getDefaulteMapper() {
		return storeMapper;
	}
 
}
