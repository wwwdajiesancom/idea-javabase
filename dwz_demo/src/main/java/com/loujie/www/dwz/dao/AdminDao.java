package com.loujie.www.dwz.dao;

import com.loujie.www.dwz.model.Admin;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @name loujie
 * @date 2019/3/7
 */
@Repository
public interface AdminDao {

    Admin get(Map<String,Object> conditionMap);

}
