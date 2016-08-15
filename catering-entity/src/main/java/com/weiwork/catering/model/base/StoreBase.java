/*
 * Store.java
 * 北京名雅轩有限公司
 * 
 */
package com.weiwork.catering.model.base;

import cn.vko.common.base.BaseEntity;
import java.util.Date;
import java.math.BigDecimal;
/**
 * vo.商铺表
 * @author 微作
 */
 @SuppressWarnings("serial")
public class StoreBase extends BaseEntity {
    /** 管理员帐号id */
    private Long adminUserId;
    /** 商铺名称 */
    private String storeName;
    /** 商铺类型 */
    private Integer storeTypeId;
    /** 商铺封面 */
    private String stroreCover;
    /** 商铺简介 */
    private String storeInfo;
    /** 营业开始时间 */
    private java.util.Date startTime;
    /** 营业结束时间 */
    private java.util.Date endTine;
    /** 起送价 */
    private String deliveryAmount;
    /** 订餐电话 */
    private String phone;
    /** 星级 */
    private Integer startLevel;
    /** 服务距离 */
    private String ranges;
    /** 配送区域 */
    private String distributionArea;
 
    /** 设置 管理员帐号id */
    public void setAdminUserId(Long adminUserId) {
        this.adminUserId = adminUserId;
    }

    /** 得到 管理员帐号id */
    public Long getAdminUserId() {
        return adminUserId;
    }
    /** 设置 商铺名称 */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    /** 得到 商铺名称 */
    public String getStoreName() {
        return storeName;
    }
    /** 设置 商铺类型 */
    public void setStoreTypeId(Integer storeTypeId) {
        this.storeTypeId = storeTypeId;
    }

    /** 得到 商铺类型 */
    public Integer getStoreTypeId() {
        return storeTypeId;
    }
    /** 设置 商铺封面 */
    public void setStroreCover(String stroreCover) {
        this.stroreCover = stroreCover;
    }

    /** 得到 商铺封面 */
    public String getStroreCover() {
        return stroreCover;
    }
    /** 设置 商铺简介 */
    public void setStoreInfo(String storeInfo) {
        this.storeInfo = storeInfo;
    }

    /** 得到 商铺简介 */
    public String getStoreInfo() {
        return storeInfo;
    }
    /** 设置 营业开始时间 */
    public void setStartTime(java.util.Date startTime) {
        this.startTime = startTime;
    }

    /** 得到 营业开始时间 */
    public java.util.Date getStartTime() {
        return startTime;
    }
    /** 设置 营业结束时间 */
    public void setEndTine(java.util.Date endTine) {
        this.endTine = endTine;
    }

    /** 得到 营业结束时间 */
    public java.util.Date getEndTine() {
        return endTine;
    }
    /** 设置 起送价 */
    public void setDeliveryAmount(String deliveryAmount) {
        this.deliveryAmount = deliveryAmount;
    }

    /** 得到 起送价 */
    public String getDeliveryAmount() {
        return deliveryAmount;
    }
    /** 设置 订餐电话 */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /** 得到 订餐电话 */
    public String getPhone() {
        return phone;
    }
    /** 设置 星级 */
    public void setStartLevel(Integer startLevel) {
        this.startLevel = startLevel;
    }

    /** 得到 星级 */
    public Integer getStartLevel() {
        return startLevel;
    }
    /** 设置 服务距离 */
    public void setRanges(String ranges) {
        this.ranges = ranges;
    }

    /** 得到 服务距离 */
    public String getRanges() {
        return ranges;
    }
    /** 设置 配送区域 */
    public void setDistributionArea(String distributionArea) {
        this.distributionArea = distributionArea;
    }

    /** 得到 配送区域 */
    public String getDistributionArea() {
        return distributionArea;
    }

    @Override
    public String toString() {
        StringBuilder strBuff = new StringBuilder();
        strBuff.append(this.getClass().getName() + ":{");	
        strBuff.append("id:").append(this.getId()).append(",");
        strBuff.append("adminUserId:").append(this.getAdminUserId()).append(",");
        strBuff.append("storeName:").append(this.getStoreName()).append(",");
        strBuff.append("storeTypeId:").append(this.getStoreTypeId()).append(",");
        strBuff.append("stroreCover:").append(this.getStroreCover()).append(",");
        strBuff.append("storeInfo:").append(this.getStoreInfo()).append(",");
        strBuff.append("startTime:").append(this.getStartTime()).append(",");
        strBuff.append("endTine:").append(this.getEndTine()).append(",");
        strBuff.append("deliveryAmount:").append(this.getDeliveryAmount()).append(",");
        strBuff.append("phone:").append(this.getPhone()).append(",");
        strBuff.append("startLevel:").append(this.getStartLevel()).append(",");
        strBuff.append("ranges:").append(this.getRanges()).append(",");
        strBuff.append("distributionArea:").append(this.getDistributionArea());
        strBuff.append("}");
        return strBuff.toString();
    }
}
