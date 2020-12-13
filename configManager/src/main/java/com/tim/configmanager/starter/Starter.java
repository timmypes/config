package com.tim.configmanager.starter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description: 启动类
 * @author: li si
 */
@SpringBootApplication
@MapperScan("com.tim.configmanager.dao")
@ComponentScan("com.tim.configmanager")
public class Starter {
    public static void main(String[] args){
        SpringApplication.run(Starter.class, args);
    }
}
