/*
 * GoodsType.java
 * 北京名雅轩有限公司
 * 
 */
package com.weiwork.catering.model.base;

import cn.vko.common.base.BaseEntity;
import java.util.Date;
import java.math.BigDecimal;
/**
 * vo.店铺商品类别
 * @author 微作
 */
 @SuppressWarnings("serial")
public class GoodsTypeBase extends BaseEntity {
    /** 商铺id */
    private Long storeId;
    /** 名称 */
    private String name;
    /** 是否删除 */
    private Integer isDelete;
 
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
    /** 设置 是否删除 */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    /** 得到 是否删除 */
    public Integer getIsDelete() {
        return isDelete;
    }

    @Override
    public String toString() {
        StringBuilder strBuff = new StringBuilder();
        strBuff.append(this.getClass().getName() + ":{");	
        strBuff.append("id:").append(this.getId()).append(",");
        strBuff.append("storeId:").append(this.getStoreId()).append(",");
        strBuff.append("name:").append(this.getName()).append(",");
        strBuff.append("isDelete:").append(this.getIsDelete());
        strBuff.append("}");
        return strBuff.toString();
    }
}
