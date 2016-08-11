/*
 * OrdersService.java
 * 北京名雅轩有限公司
 * 
 */
package com.weiwork.catering.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.weiwork.catering.mapper.IOrdersMapper;
import com.weiwork.catering.model.Orders;
import com.weiwork.catering.service.IOrdersService;
import cn.vko.common.base.impl.BaseServiceImpl;

/**
 * service接口实现.订单表
 * @author 微作
 */
@Service
public class OrdersServiceImpl  extends BaseServiceImpl<Orders,IOrdersMapper> implements IOrdersService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
	private IOrdersMapper ordersMapper;
	
	@Override
	public IOrdersMapper getDefaulteMapper() {
		return ordersMapper;
	}
 
}
