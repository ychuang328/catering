/*
 * Goods.java
 * 北京名雅轩有限公司
 * 
 */
package com.weiwork.catering.model.base;

import cn.vko.common.base.BaseEntity;
import java.util.Date;
import java.math.BigDecimal;
/**
 * vo.商品表
 * @author 微作
 */
 @SuppressWarnings("serial")
public class GoodsBase extends BaseEntity {
    /** 商铺id */
    private Long storeId;
    /** 名称 */
    private String name;
    /** 价格 */
    private String price;
    /** 优惠价格 */
    private String sellPrice;
    /** 图片 */
    private String image;
    /** 星级 */
    private Integer startLevel;
    /** 是否删除 */
    private Integer isDelete;
    /** 商铺菜品标签id */
    private Integer goodsTypeId;
    /** 商铺菜品名称 */
    private String goodsTypeName;
 
    /** 设置 商铺id */
    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    /** 得到 商铺id */
    public Long getStoreId() {
        return storeId;
    }
    /** 设置 名称 */
    public void setName(String name) {
        this.name = name;
    }

    /** 得到 名称 */
    public String getName() {
        return name;
    }
    /** 设置 价格 */
    public void setPrice(String price) {
        this.price = price;
    }

    /** 得到 价格 */
    public String getPrice() {
        return price;
    }
    /** 设置 优惠价格 */
    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    /** 得到 优惠价格 */
    public String getSellPrice() {
        return sellPrice;
    }
    /** 设置 图片 */
    public void setImage(String image) {
        this.image = image;
    }

    /** 得到 图片 */
    public String getImage() {
        return image;
    }
    /** 设置 星级 */
    public void setStartLevel(Integer startLevel) {
        this.startLevel = startLevel;
    }

    /** 得到 星级 */
    public Integer getStartLevel() {
        return startLevel;
    }
    /** 设置 是否删除 */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    /** 得到 是否删除 */
    public Integer getIsDelete() {
        return isDelete;
    }
    /** 设置 商铺菜品标签id */
    public void setGoodsTypeId(Integer goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }

    /** 得到 商铺菜品标签id */
    public Integer getGoodsTypeId() {
        return goodsTypeId;
    }
    /** 设置 商铺菜品名称 */
    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    /** 得到 商铺菜品名称 */
    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    @Override
    public String toString() {
        StringBuilder strBuff = new StringBuilder();
        strBuff.append(this.getClass().getName() + ":{");	
        strBuff.append("id:").append(this.getId()).append(",");
        strBuff.append("storeId:").append(this.getStoreId()).append(",");
        strBuff.append("name:").append(this.getName()).append(",");
        strBuff.append("price:").append(this.getPrice()).append(",");
        strBuff.append("sellPrice:").append(this.getSellPrice()).append(",");
        strBuff.append("image:").append(this.getImage()).append(",");
        strBuff.append("startLevel:").append(this.getStartLevel()).append(",");
        strBuff.append("isDelete:").append(this.getIsDelete()).append(",");
        strBuff.append("goodsTypeId:").append(this.getGoodsTypeId()).append(",");
        strBuff.append("goodsTypeName:").append(this.getGoodsTypeName());
        strBuff.append("}");
        return strBuff.toString();
    }
}
