/*
 * HomeImageService.java
 * 北京名雅轩有限公司
 * 
 */
package com.weiwork.catering.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.weiwork.catering.mapper.IHomeImageMapper;
import com.weiwork.catering.model.HomeImage;
import com.weiwork.catering.service.IHomeImageService;
import cn.vko.common.base.impl.BaseServiceImpl;

/**
 * service接口实现.轮播图
 * @author 微作
 */
@Service
public class HomeImageServiceImpl  extends BaseServiceImpl<HomeImage,IHomeImageMapper> implements IHomeImageService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
	private IHomeImageMapper homeImageMapper;
	
	@Override
	public IHomeImageMapper getDefaulteMapper() {
		return homeImageMapper;
	}
 
}
