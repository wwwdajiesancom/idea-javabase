package com.loujie.www.dwz.dao;

import com.loujie.www.dwz.model.WhiteListMobile;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @name loujie
 * @date 2019/3/6
 */
@Repository
public interface WhiteListMobileDao {

    WhiteListMobile get(Map<String,Object> conditionMap);

}
