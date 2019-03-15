package com.loujie.www.dwz.model;

import com.loujie.www.dwz.model.base.BaseModel;

import java.util.Date;

/**
 * 后台管理员
 *
 * @name loujie
 * @date 2019/3/7
 */
public class Admin extends BaseModel {

    private static final long serialVersionUID = -4703810366793564840L;
    private Integer id;//自增主键
    private String loginName;//后台登录帐号
    private String password;//登录密码
    private Integer isSuper;//是否为超级管理员;0=否;1=是;默认0=否
    private String name;//帐号名称
    private Integer status;//帐号状态:0=无效;1=有效
    private Date createdAt;//创建时间
    private Date updatedAt;//最新更新时间

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getIsSuper() {
        return isSuper;
    }

    public void setIsSuper(Integer isSuper) {
        this.isSuper = isSuper;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public Date getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
