/*
 * UsersService.java
 * 北京名雅轩有限公司
 * 
 */
package com.weiwork.catering.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.weiwork.catering.mapper.IUsersMapper;
import com.weiwork.catering.model.Users;
import com.weiwork.catering.service.IUsersService;
import cn.vko.common.base.impl.BaseServiceImpl;

/**
 * service接口实现.用户表
 * @author 微作
 */
@Service
public class UsersServiceImpl  extends BaseServiceImpl<Users,IUsersMapper> implements IUsersService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
	private IUsersMapper usersMapper;
	
	@Override
	public IUsersMapper getDefaulteMapper() {
		return usersMapper;
	}
 
}
