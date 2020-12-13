package com.tim.config.dao;

import com.tim.config.entity.Property;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface PropertyDao {
    List<Property> findPropertyListByAppName(@Param("appName") String appName);
}
