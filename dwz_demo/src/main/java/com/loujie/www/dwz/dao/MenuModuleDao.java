package com.loujie.www.dwz.dao;

import com.loujie.www.dwz.model.MenuModule;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @name loujie
 * @date 2019/3/7
 */
@Repository
public interface MenuModuleDao {

    MenuModule get(Map<String,Object> conditionMap);

    List<MenuModule> getList(Map<String,Object> conditionMap);

}
