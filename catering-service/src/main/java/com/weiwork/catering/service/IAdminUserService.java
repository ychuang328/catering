/*
 * AdminUserService.java
 * 北京名雅轩有限公司
 * 
 */
package com.weiwork.catering.service;

import cn.vko.common.base.BaseService;
import com.weiwork.catering.model.AdminUser;
import com.weiwork.catering.mapper.IAdminUserMapper;
/**
 * @Description:service接口.后台管理用户表
 * @author: 微作
 */
public interface IAdminUserService extends BaseService<AdminUser,IAdminUserMapper> {
}
