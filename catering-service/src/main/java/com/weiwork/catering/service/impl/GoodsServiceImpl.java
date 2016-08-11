/*
 * GoodsService.java
 * 北京名雅轩有限公司
 * 
 */
package com.weiwork.catering.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.weiwork.catering.mapper.IGoodsMapper;
import com.weiwork.catering.model.Goods;
import com.weiwork.catering.service.IGoodsService;
import cn.vko.common.base.impl.BaseServiceImpl;

/**
 * service接口实现.商品表
 * @author 微作
 */
@Service
public class GoodsServiceImpl  extends BaseServiceImpl<Goods,IGoodsMapper> implements IGoodsService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
	private IGoodsMapper goodsMapper;
	
	@Override
	public IGoodsMapper getDefaulteMapper() {
		return goodsMapper;
	}
 
}
