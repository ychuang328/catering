/*
 * AdminUserService.java
 * 北京名雅轩有限公司
 * 
 */
package com.weiwork.catering.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.weiwork.catering.mapper.IAdminUserMapper;
import com.weiwork.catering.model.AdminUser;
import com.weiwork.catering.service.IAdminUserService;
import cn.vko.common.base.impl.BaseServiceImpl;

/**
 * service接口实现.后台管理用户表
 * @author 微作
 */
@Service
public class AdminUserServiceImpl  extends BaseServiceImpl<AdminUser,IAdminUserMapper> implements IAdminUserService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
	private IAdminUserMapper adminUserMapper;
	
	@Override
	public IAdminUserMapper getDefaulteMapper() {
		return adminUserMapper;
	}
 
}
