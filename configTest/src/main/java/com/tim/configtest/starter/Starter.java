package com.tim.configtest.starter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description:
 * @author: li si
 */

@SpringBootApplication
@ComponentScan("com.tim")
@MapperScan("com.tim.config.dao")
public class Starter {
    public static void main(String[] args){
        SpringApplication.run(Starter.class, args);
    }
}
