/*
 * HomeImage.java
 * 北京名雅轩有限公司
 * 
 */
package com.weiwork.catering.model.base;

import cn.vko.common.base.BaseEntity;
import java.util.Date;
import java.math.BigDecimal;
/**
 * vo.轮播图
 * @author 微作
 */
 @SuppressWarnings("serial")
public class HomeImageBase extends BaseEntity {
    /** 商铺id */
    private Long storeId;
    /** 名称 */
    private String name;
    /** 图片 */
    private String image;
    /** 是否删除 */
    private Integer isDelete;
    /** 链接地址 */
    private String link;
 
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
    /** 设置 图片 */
    public void setImage(String image) {
        this.image = image;
    }

    /** 得到 图片 */
    public String getImage() {
        return image;
    }
    /** 设置 是否删除 */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    /** 得到 是否删除 */
    public Integer getIsDelete() {
        return isDelete;
    }
    /** 设置 链接地址 */
    public void setLink(String link) {
        this.link = link;
    }

    /** 得到 链接地址 */
    public String getLink() {
        return link;
    }

    @Override
    public String toString() {
        StringBuilder strBuff = new StringBuilder();
        strBuff.append(this.getClass().getName() + ":{");	
        strBuff.append("id:").append(this.getId()).append(",");
        strBuff.append("storeId:").append(this.getStoreId()).append(",");
        strBuff.append("name:").append(this.getName()).append(",");
        strBuff.append("image:").append(this.getImage()).append(",");
        strBuff.append("isDelete:").append(this.getIsDelete()).append(",");
        strBuff.append("link:").append(this.getLink());
        strBuff.append("}");
        return strBuff.toString();
    }
}
