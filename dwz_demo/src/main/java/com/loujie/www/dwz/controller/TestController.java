package com.loujie.www.dwz.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @name loujie
 * @date 2019/3/6
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/one")
    public Object one() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "jie哥");
        map.put("age", 190);
        return map;
    }
}
