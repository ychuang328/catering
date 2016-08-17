/*
 * Users.java
 * 北京名雅轩有限公司
 * 
 */
package com.weiwork.catering.model.base;

import cn.vko.common.base.BaseEntity;
import java.util.Date;
import java.math.BigDecimal;
/**
 * vo.用户表
 * @author 微作
 */
 @SuppressWarnings("serial")
public class UsersBase extends BaseEntity {
    /** 微信openId */
    private String openId;
    /** 名称 */
    private String name;
    /** 头像 */
    private String icon;
    /** 性别 */
    private Integer sex;
    /** 年龄 */
    private Integer age;
 
    /** 设置 微信openId */
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    /** 得到 微信openId */
    public String getOpenId() {
        return openId;
    }
    /** 设置 名称 */
    public void setName(String name) {
        this.name = name;
    }

    /** 得到 名称 */
    public String getName() {
        return name;
    }
    /** 设置 头像 */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /** 得到 头像 */
    public String getIcon() {
        return icon;
    }
    /** 设置 性别 */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /** 得到 性别 */
    public Integer getSex() {
        return sex;
    }
    /** 设置 年龄 */
    public void setAge(Integer age) {
        this.age = age;
    }

    /** 得到 年龄 */
    public Integer getAge() {
        return age;
    }

    @Override
    public String toString() {
        StringBuilder strBuff = new StringBuilder();
        strBuff.append(this.getClass().getName() + ":{");	
        strBuff.append("id:").append(this.getId()).append(",");
        strBuff.append("openId:").append(this.getOpenId()).append(",");
        strBuff.append("name:").append(this.getName()).append(",");
        strBuff.append("icon:").append(this.getIcon()).append(",");
        strBuff.append("sex:").append(this.getSex()).append(",");
        strBuff.append("age:").append(this.getAge());
        strBuff.append("}");
        return strBuff.toString();
    }
}
