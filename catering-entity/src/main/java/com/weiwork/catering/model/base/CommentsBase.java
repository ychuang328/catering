/*
 * Comments.java
 * 北京名雅轩有限公司
 * 
 */
package com.weiwork.catering.model.base;

import cn.vko.common.base.BaseEntity;
import java.util.Date;
import java.math.BigDecimal;
/**
 * vo.评论
 * @author 微作
 */
 @SuppressWarnings("serial")
public class CommentsBase extends BaseEntity {
    /** 评论对象id */
    private Long objId;
    /** 评论类型 */
    private Integer objType;
    /** 评论信息 */
    private String info;
    /** 星级 */
    private Integer startLevel;
    /** 评论时间 */
    private java.util.Date createTime;
 
    /** 设置 评论对象id */
    public void setObjId(Long objId) {
        this.objId = objId;
    }

    /** 得到 评论对象id */
    public Long getObjId() {
        return objId;
    }
    /** 设置 评论类型 */
    public void setObjType(Integer objType) {
        this.objType = objType;
    }

    /** 得到 评论类型 */
    public Integer getObjType() {
        return objType;
    }
    /** 设置 评论信息 */
    public void setInfo(String info) {
        this.info = info;
    }

    /** 得到 评论信息 */
    public String getInfo() {
        return info;
    }
    /** 设置 星级 */
    public void setStartLevel(Integer startLevel) {
        this.startLevel = startLevel;
    }

    /** 得到 星级 */
    public Integer getStartLevel() {
        return startLevel;
    }
    /** 设置 评论时间 */
    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    /** 得到 评论时间 */
    public java.util.Date getCreateTime() {
        return createTime;
    }

    @Override
    public String toString() {
        StringBuilder strBuff = new StringBuilder();
        strBuff.append(this.getClass().getName() + ":{");	
        strBuff.append("id:").append(this.getId()).append(",");
        strBuff.append("objId:").append(this.getObjId()).append(",");
        strBuff.append("objType:").append(this.getObjType()).append(",");
        strBuff.append("info:").append(this.getInfo()).append(",");
        strBuff.append("startLevel:").append(this.getStartLevel()).append(",");
        strBuff.append("createTime:").append(this.getCreateTime());
        strBuff.append("}");
        return strBuff.toString();
    }
}
