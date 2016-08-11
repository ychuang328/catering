/*
 * AdminUser.java
 * 北京名雅轩有限公司
 * 
 */
package com.weiwork.catering.model.base;

import cn.vko.common.base.BaseEntity;
import java.util.Date;
import java.math.BigDecimal;
/**
 * vo.后台管理用户表
 * @author 微作
 */
 @SuppressWarnings("serial")
public class AdminUserBase extends BaseEntity {
    /** 登录名 */
    private String loginName;
    /** 密码 */
    private String password;
    /** 帐号类型 */
    private Integer type;
 
    /** 设置 登录名 */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /** 得到 登录名 */
    public String getLoginName() {
        return loginName;
    }
    /** 设置 密码 */
    public void setPassword(String password) {
        this.password = password;
    }

    /** 得到 密码 */
    public String getPassword() {
        return password;
    }
    /** 设置 帐号类型 */
    public void setType(Integer type) {
        this.type = type;
    }

    /** 得到 帐号类型 */
    public Integer getType() {
        return type;
    }

    @Override
    public String toString() {
        StringBuilder strBuff = new StringBuilder();
        strBuff.append(this.getClass().getName() + ":{");	
        strBuff.append("id:").append(this.getId()).append(",");
        strBuff.append("loginName:").append(this.getLoginName()).append(",");
        strBuff.append("password:").append(this.getPassword()).append(",");
        strBuff.append("type:").append(this.getType());
        strBuff.append("}");
        return strBuff.toString();
    }
}
