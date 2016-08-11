/*
 * GoodsTagService.java
 * 北京名雅轩有限公司
 * 
 */
package com.weiwork.catering.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.weiwork.catering.mapper.IGoodsTagMapper;
import com.weiwork.catering.model.GoodsTag;
import com.weiwork.catering.service.IGoodsTagService;
import cn.vko.common.base.impl.BaseServiceImpl;

/**
 * service接口实现.商品标签表
 * @author 微作
 */
@Service
public class GoodsTagServiceImpl  extends BaseServiceImpl<GoodsTag,IGoodsTagMapper> implements IGoodsTagService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
	private IGoodsTagMapper goodsTagMapper;
	
	@Override
	public IGoodsTagMapper getDefaulteMapper() {
		return goodsTagMapper;
	}
 
}
