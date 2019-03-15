package com.loujie.www.dwz.model.base;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

/**
 * 基础
 *
 * @name loujie
 * @date 2019/3/7
 */
public class BaseModel implements Serializable {

    private static final long serialVersionUID = -1223625958334117644L;
    protected Integer id; // 自增Id
    protected Date createdAt;// 创建时间
    protected Date updatedAt;// 更新时间
    protected HashMap<String, Object> inc = new HashMap<String, Object>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public HashMap<String, Object> getInc() {
        return inc;
    }

    public void setInc(HashMap<String, Object> inc) {
        this.inc = inc;
    }
}
