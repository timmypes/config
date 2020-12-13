package com.tim.configtest.controller;

import com.tim.config.annotation.Conf;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: li si
 */

@RestController
public class TestController {

    @Conf(name = "test.prop1")
    private String prop1;

    @Conf(name = "test.prop2")
    private Integer prop2;

    @Conf(name = "test.prop3")
    private Map<String, Object> prop3;

    @Conf(name = "test.prop4")
    private Entity prop4;

    @ResponseBody
    @RequestMapping("/test")
    public Map<String, Object> test(){
        Map<String, Object> map = new HashMap<>();
        map.put("prop1", prop1);
        map.put("prop2", prop2);
        map.put("prop3", prop3);
        map.put("prop4", prop4);
        return map;
    }
}
