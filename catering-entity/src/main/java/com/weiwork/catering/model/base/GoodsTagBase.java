/*
 * GoodsTag.java
 * 北京名雅轩有限公司
 * 
 */
package com.weiwork.catering.model.base;

import cn.vko.common.base.BaseEntity;
import java.util.Date;
import java.math.BigDecimal;
/**
 * vo.商品标签表
 * @author 微作
 */
 @SuppressWarnings("serial")
public class GoodsTagBase extends BaseEntity {
    /** 商品id */
    private Long goodsId;
    /** 标签id */
    private Long tagId;
    /** 标签名称 */
    private String tagName;
 
    /** 设置 商品id */
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    /** 得到 商品id */
    public Long getGoodsId() {
        return goodsId;
    }
    /** 设置 标签id */
    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    /** 得到 标签id */
    public Long getTagId() {
        return tagId;
    }
    /** 设置 标签名称 */
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    /** 得到 标签名称 */
    public String getTagName() {
        return tagName;
    }

    @Override
    public String toString() {
        StringBuilder strBuff = new StringBuilder();
        strBuff.append(this.getClass().getName() + ":{");	
        strBuff.append("id:").append(this.getId()).append(",");
        strBuff.append("goodsId:").append(this.getGoodsId()).append(",");
        strBuff.append("tagId:").append(this.getTagId()).append(",");
        strBuff.append("tagName:").append(this.getTagName());
        strBuff.append("}");
        return strBuff.toString();
    }
}
