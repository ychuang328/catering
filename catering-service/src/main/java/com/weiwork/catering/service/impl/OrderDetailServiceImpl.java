/*
 * OrderDetailService.java
 * 北京名雅轩有限公司
 * 
 */
package com.weiwork.catering.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.weiwork.catering.mapper.IOrderDetailMapper;
import com.weiwork.catering.model.OrderDetail;
import com.weiwork.catering.service.IOrderDetailService;
import cn.vko.common.base.impl.BaseServiceImpl;

/**
 * service接口实现.订单明细表
 * @author 微作
 */
@Service
public class OrderDetailServiceImpl  extends BaseServiceImpl<OrderDetail,IOrderDetailMapper> implements IOrderDetailService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
	private IOrderDetailMapper orderDetailMapper;
	
	@Override
	public IOrderDetailMapper getDefaulteMapper() {
		return orderDetailMapper;
	}
 
}
