/*
 * GoodsTypeService.java
 * 北京名雅轩有限公司
 * 
 */
package com.weiwork.catering.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.weiwork.catering.mapper.IGoodsTypeMapper;
import com.weiwork.catering.model.GoodsType;
import com.weiwork.catering.service.IGoodsTypeService;
import cn.vko.common.base.impl.BaseServiceImpl;

/**
 * service接口实现.店铺商品类别
 * @author 微作
 */
@Service
public class GoodsTypeServiceImpl  extends BaseServiceImpl<GoodsType,IGoodsTypeMapper> implements IGoodsTypeService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
	private IGoodsTypeMapper goodsTypeMapper;
	
	@Override
	public IGoodsTypeMapper getDefaulteMapper() {
		return goodsTypeMapper;
	}
 
}
