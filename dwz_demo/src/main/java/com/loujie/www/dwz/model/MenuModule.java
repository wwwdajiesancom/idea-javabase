package com.loujie.www.dwz.model;

import com.loujie.www.dwz.model.base.BaseModel;

import java.util.Date;

/**
 * 菜单
 *
 * @name loujie
 * @date 2019/3/7
 */
public class MenuModule extends BaseModel {

    private static final long serialVersionUID = 9198019439064802013L;
    private Integer id;//自增ID
    private String name;//模块名称
    private Integer parentId;//上一级ID,顶级的为0
    private Integer sort;//排序
    private Integer status;//状态:0=无效;1=正常
    private String className;//样式名称(和模块一块展示)
    private String icon;//样式(和模块一块展示)
    private String url;//完整的url,如果没有为#
    private String module;//模块的名称,这个可以作为权限来用的,不过需要结合另外具体操作权限表用
    private String moduleBak;//备份,占时用不到
    private String permissionSets;//权限列表,用逗号隔开如果多个;它与module是互斥的,目前设计的复杂,所以兼容了两种,是任选一种
    private Date createdAt;//创建时间
    private Date udpatedAt;//更新时间

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getModuleBak() {
        return moduleBak;
    }

    public void setModuleBak(String moduleBak) {
        this.moduleBak = moduleBak;
    }

    public String getPermissionSets() {
        return permissionSets;
    }

    public void setPermissionSets(String permissionSets) {
        this.permissionSets = permissionSets;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUdpatedAt() {
        return udpatedAt;
    }

    public void setUdpatedAt(Date udpatedAt) {
        this.udpatedAt = udpatedAt;
    }
}
