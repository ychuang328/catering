/*
 * CommentsService.java
 * 北京名雅轩有限公司
 * 
 */
package com.weiwork.catering.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.weiwork.catering.mapper.ICommentsMapper;
import com.weiwork.catering.model.Comments;
import com.weiwork.catering.service.ICommentsService;
import cn.vko.common.base.impl.BaseServiceImpl;

/**
 * service接口实现.评论
 * @author 微作
 */
@Service
public class CommentsServiceImpl  extends BaseServiceImpl<Comments,ICommentsMapper> implements ICommentsService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
	private ICommentsMapper commentsMapper;
	
	@Override
	public ICommentsMapper getDefaulteMapper() {
		return commentsMapper;
	}
 
}
