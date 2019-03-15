package com.loujie.www.dwz.dao;

import com.loujie.www.dwz.model.MenuModulePermission;

import java.util.List;
import java.util.Map;

/**
 * @name loujie
 * @date 2019/3/7
 */
public interface MenuModulePermissionDao {

    MenuModulePermission get(Map<String,Object> conditionMap);

    List<MenuModulePermission> getList(Map<String,Object> conditionMap);
}
