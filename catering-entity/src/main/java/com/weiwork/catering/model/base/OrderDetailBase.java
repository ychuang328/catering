/*
 * OrderDetail.java
 * 北京名雅轩有限公司
 * 
 */
package com.weiwork.catering.model.base;

import cn.vko.common.base.BaseEntity;
import java.util.Date;
import java.math.BigDecimal;
/**
 * vo.订单明细表
 * @author 微作
 */
 @SuppressWarnings("serial")
public class OrderDetailBase extends BaseEntity {
    /** 订单id */
    private Long orderId;
    /** 商品id */
    private Long goodsId;
    /** 用户id */
    private Long userId;
    /** 商品名称 */
    private String goodsName;
    /** 商品价格 */
    private String goodsPrice;
    /** 商品数量 */
    private Integer num;
    /** 创建时间 */
    private java.util.Date createTime;
 
    /** 设置 订单id */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /** 得到 订单id */
    public Long getOrderId() {
        return orderId;
    }
    /** 设置 商品id */
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    /** 得到 商品id */
    public Long getGoodsId() {
        return goodsId;
    }
    /** 设置 用户id */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /** 得到 用户id */
    public Long getUserId() {
        return userId;
    }
    /** 设置 商品名称 */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /** 得到 商品名称 */
    public String getGoodsName() {
        return goodsName;
    }
    /** 设置 商品价格 */
    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    /** 得到 商品价格 */
    public String getGoodsPrice() {
        return goodsPrice;
    }
    /** 设置 商品数量 */
    public void setNum(Integer num) {
        this.num = num;
    }

    /** 得到 商品数量 */
    public Integer getNum() {
        return num;
    }
    /** 设置 创建时间 */
    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    /** 得到 创建时间 */
    public java.util.Date getCreateTime() {
        return createTime;
    }

    @Override
    public String toString() {
        StringBuilder strBuff = new StringBuilder();
        strBuff.append(this.getClass().getName() + ":{");	
        strBuff.append("id:").append(this.getId()).append(",");
        strBuff.append("orderId:").append(this.getOrderId()).append(",");
        strBuff.append("goodsId:").append(this.getGoodsId()).append(",");
        strBuff.append("userId:").append(this.getUserId()).append(",");
        strBuff.append("goodsName:").append(this.getGoodsName()).append(",");
        strBuff.append("goodsPrice:").append(this.getGoodsPrice()).append(",");
        strBuff.append("num:").append(this.getNum()).append(",");
        strBuff.append("createTime:").append(this.getCreateTime());
        strBuff.append("}");
        return strBuff.toString();
    }
}
