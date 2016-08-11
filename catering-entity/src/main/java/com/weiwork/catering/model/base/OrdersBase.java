/*
 * Orders.java
 * 北京名雅轩有限公司
 * 
 */
package com.weiwork.catering.model.base;

import cn.vko.common.base.BaseEntity;
import java.util.Date;
import java.math.BigDecimal;
/**
 * vo.订单表
 * @author 微作
 */
 @SuppressWarnings("serial")
public class OrdersBase extends BaseEntity {
    /** 商铺id */
    private Long storeId;
    /** 订单号 */
    private String orderNo;
    /** 订单状态 */
    private Integer status;
    /** 用户id */
    private Long userId;
    /** 订单金额 */
    private String amount;
    /** 第三方流水号 */
    private String tradeNo;
    /** 支付时间 */
    private java.util.Date payTime;
    /** 支付账号 */
    private String accountId;
    /** 创建时间 */
    private java.util.Date createTime;
    /** 备注 */
    private String remark;
 
    /** 设置 商铺id */
    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    /** 得到 商铺id */
    public Long getStoreId() {
        return storeId;
    }
    /** 设置 订单号 */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /** 得到 订单号 */
    public String getOrderNo() {
        return orderNo;
    }
    /** 设置 订单状态 */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /** 得到 订单状态 */
    public Integer getStatus() {
        return status;
    }
    /** 设置 用户id */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /** 得到 用户id */
    public Long getUserId() {
        return userId;
    }
    /** 设置 订单金额 */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /** 得到 订单金额 */
    public String getAmount() {
        return amount;
    }
    /** 设置 第三方流水号 */
    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    /** 得到 第三方流水号 */
    public String getTradeNo() {
        return tradeNo;
    }
    /** 设置 支付时间 */
    public void setPayTime(java.util.Date payTime) {
        this.payTime = payTime;
    }

    /** 得到 支付时间 */
    public java.util.Date getPayTime() {
        return payTime;
    }
    /** 设置 支付账号 */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /** 得到 支付账号 */
    public String getAccountId() {
        return accountId;
    }
    /** 设置 创建时间 */
    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    /** 得到 创建时间 */
    public java.util.Date getCreateTime() {
        return createTime;
    }
    /** 设置 备注 */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /** 得到 备注 */
    public String getRemark() {
        return remark;
    }

    @Override
    public String toString() {
        StringBuilder strBuff = new StringBuilder();
        strBuff.append(this.getClass().getName() + ":{");	
        strBuff.append("id:").append(this.getId()).append(",");
        strBuff.append("storeId:").append(this.getStoreId()).append(",");
        strBuff.append("orderNo:").append(this.getOrderNo()).append(",");
        strBuff.append("status:").append(this.getStatus()).append(",");
        strBuff.append("userId:").append(this.getUserId()).append(",");
        strBuff.append("amount:").append(this.getAmount()).append(",");
        strBuff.append("tradeNo:").append(this.getTradeNo()).append(",");
        strBuff.append("payTime:").append(this.getPayTime()).append(",");
        strBuff.append("accountId:").append(this.getAccountId()).append(",");
        strBuff.append("createTime:").append(this.getCreateTime()).append(",");
        strBuff.append("remark:").append(this.getRemark());
        strBuff.append("}");
        return strBuff.toString();
    }
}
