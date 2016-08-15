/*
 * Addr.java
 * 北京名雅轩有限公司
 * 
 */
package com.weiwork.catering.model.base;

import cn.vko.common.base.BaseEntity;
import java.util.Date;
import java.math.BigDecimal;
/**
 * vo.收货地址
 * @author 微作
 */
 @SuppressWarnings("serial")
public class AddrBase extends BaseEntity {
    /** 用户id */
    private Long userId;
    /** 收货人 */
    private String recipient;
    /** 电话 */
    private Integer mobile;
    /** 地址 */
    private String address;
    /** 是否默认地址 */
    private Integer isDefault;
 
    /** 设置 用户id */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /** 得到 用户id */
    public Long getUserId() {
        return userId;
    }
    /** 设置 收货人 */
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    /** 得到 收货人 */
    public String getRecipient() {
        return recipient;
    }
    /** 设置 电话 */
    public void setMobile(Integer mobile) {
        this.mobile = mobile;
    }

    /** 得到 电话 */
    public Integer getMobile() {
        return mobile;
    }
    /** 设置 地址 */
    public void setAddress(String address) {
        this.address = address;
    }

    /** 得到 地址 */
    public String getAddress() {
        return address;
    }
    /** 设置 是否默认地址 */
    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    /** 得到 是否默认地址 */
    public Integer getIsDefault() {
        return isDefault;
    }

    @Override
    public String toString() {
        StringBuilder strBuff = new StringBuilder();
        strBuff.append(this.getClass().getName() + ":{");	
        strBuff.append("id:").append(this.getId()).append(",");
        strBuff.append("userId:").append(this.getUserId()).append(",");
        strBuff.append("recipient:").append(this.getRecipient()).append(",");
        strBuff.append("mobile:").append(this.getMobile()).append(",");
        strBuff.append("address:").append(this.getAddress()).append(",");
        strBuff.append("isDefault:").append(this.getIsDefault());
        strBuff.append("}");
        return strBuff.toString();
    }
}
