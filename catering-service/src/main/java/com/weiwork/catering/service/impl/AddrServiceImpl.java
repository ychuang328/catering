/*
 * AddrService.java
 * 北京名雅轩有限公司
 * 
 */
package com.weiwork.catering.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.weiwork.catering.mapper.IAddrMapper;
import com.weiwork.catering.model.Addr;
import com.weiwork.catering.service.IAddrService;
import cn.vko.common.base.impl.BaseServiceImpl;

/**
 * service接口实现.收货地址
 * @author 微作
 */
@Service
public class AddrServiceImpl  extends BaseServiceImpl<Addr,IAddrMapper> implements IAddrService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
	private IAddrMapper addrMapper;
	
	@Override
	public IAddrMapper getDefaulteMapper() {
		return addrMapper;
	}
 
}
