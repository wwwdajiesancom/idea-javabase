package com.loujie.www.dwz.model;

import com.loujie.www.dwz.model.base.BaseModel;

import java.util.Date;

/**
 * 菜单权限
 *
 * @name loujie
 * @date 2019/3/7
 */
public class MenuModulePermission extends BaseModel {

    private static final long serialVersionUID = -2744986191499184717L;
    private Integer id;//自增ID
    private Integer menuModuleId;//菜单模块ID
    private String permissionName;//权限名称
    private String permission;//权限
    private Integer sort;//排序
    private String description;//权限备注
    private Date createdAt;//创建时间

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMenuModuleId() {
        return menuModuleId;
    }

    public void setMenuModuleId(Integer menuModuleId) {
        this.menuModuleId = menuModuleId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
