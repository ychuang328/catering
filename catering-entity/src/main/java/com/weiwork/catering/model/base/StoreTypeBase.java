/*
 * StoreType.java
 * 北京名雅轩有限公司
 * 
 */
package com.weiwork.catering.model.base;

import cn.vko.common.base.BaseEntity;
import java.util.Date;
import java.math.BigDecimal;
/**
 * vo.餐饮类别字典
 * @author 微作
 */
 @SuppressWarnings("serial")
public class StoreTypeBase extends BaseEntity {
    /** 名称 */
    private String name;
    /** 排序 */
    private Integer orderNum;
    /** 类型 */
    private Integer type;
 
    /** 设置 名称 */
    public void setName(String name) {
        this.name = name;
    }

    /** 得到 名称 */
    public String getName() {
        return name;
    }
    /** 设置 排序 */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    /** 得到 排序 */
    public Integer getOrderNum() {
        return orderNum;
    }
    /** 设置 类型 */
    public void setType(Integer type) {
        this.type = type;
    }

    /** 得到 类型 */
    public Integer getType() {
        return type;
    }

    @Override
    public String toString() {
        StringBuilder strBuff = new StringBuilder();
        strBuff.append(this.getClass().getName() + ":{");	
        strBuff.append("id:").append(this.getId()).append(",");
        strBuff.append("name:").append(this.getName()).append(",");
        strBuff.append("orderNum:").append(this.getOrderNum()).append(",");
        strBuff.append("type:").append(this.getType());
        strBuff.append("}");
        return strBuff.toString();
    }
}
