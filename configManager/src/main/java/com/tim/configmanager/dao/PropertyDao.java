package com.tim.configmanager.dao;

import com.tim.configmanager.entity.Property;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyDao {
    List<Property> findPropertyListByAppName(@Param("appName") String appName);

    int addProperty(@Param("property") Property property);

    int updateProperty(@Param("property") Property property);

    int deleteProperty(@Param("propId") String propId);

    Property getPropertyByAppNameAndPropName(@Param("appName") String appName, @Param("propName") String propName);

    List<Property> findPropertyList(@Param("pageStart") Integer pageStart, @Param("pageSize") Integer pageSize);

    Long countPropertySums();

    Property getPropertyByPropId(@Param("propId") String propId);
}
