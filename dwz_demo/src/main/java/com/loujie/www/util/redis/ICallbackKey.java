package com.loujie.www.util.redis;


import com.loujie.www.dwz.service.impl.PojoToObjectServiceImpl;
import com.loujie.www.util.common.ArgsUtils;

public abstract class ICallbackKey<T> {

    // 成员变脸,最为前缀
    private String prefix;

    private PojoToObjectServiceImpl pojoToObjectService;

    private Integer seq;// 序号

    /**
     * 设置前最
     *
     * @param prefix
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * 获取前缀
     */
    public String getPrefix() {
        return this.prefix;
    }

    public PojoToObjectServiceImpl getPojoToObjectService() {
        return pojoToObjectService;
    }

    public void setPojoToObjectService(PojoToObjectServiceImpl pojoToObjectService) {
        this.pojoToObjectService = pojoToObjectService;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    /**
     * 获取键
     *
     * @param t 外部的对象
     * @return
     */
    public String getKey(T t) {
        String key = this.createKey(t);
        if (!ArgsUtils.isEmpty(this.prefix)) {
            return String.format(this.prefix, key);
        } else {
            return key;
        }
    }

    /**
     * 创建键
     *
     * @param t 外部的对象
     * @return
     */
    public abstract String createKey(T t);

}
