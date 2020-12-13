package com.tim.config.processor;

import com.tim.config.annotation.Conf;
import com.tim.config.entity.BeanInjectionData;
import com.tim.config.entity.FieldInjectionData;
import com.tim.config.service.InjectPropertyService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 注入要配置的属性信息
 * @author: li si
 */

@Component
public class ConfInjectionBeanPostProcessor implements MergedBeanDefinitionPostProcessor, InstantiationAwareBeanPostProcessor, InitializingBean {

    private final Map<String, BeanInjectionData> beanInjectionMap = new ConcurrentHashMap<>();

    private final Set<Class<? extends Annotation>> configAnnotationTypes = new LinkedHashSet<>(4);

    @Value("${config.appName}")
    private String appName;

    @Autowired
    private InjectPropertyService injectPropertyService;

    public ConfInjectionBeanPostProcessor() {
        this.configAnnotationTypes.add(Conf.class);
    }

    /**
     * 解析bean中被@Conf注解的域
     * @param rootBeanDefinition
     * @param beanType
     * @param beanName
     */
    @Override
    public void postProcessMergedBeanDefinition(RootBeanDefinition rootBeanDefinition, Class<?> beanType, String beanName) {
        List<FieldInjectionData> fields = new ArrayList<>();
        ReflectionUtils.doWithLocalFields(beanType, field -> {
            MergedAnnotation<?> ann = findConfAnnotation(field);
            if (ann != null) {
                if (Modifier.isStatic(field.getModifiers())) {
                    return;
                }
                FieldInjectionData fieldInjectionData = new FieldInjectionData();
                fieldInjectionData.setField(field);
                fieldInjectionData.setPropName(ann.getString("name"));
                fields.add(fieldInjectionData);
            }
        });
        if(!CollectionUtils.isEmpty(fields)) {
            BeanInjectionData beanInjectionData = new BeanInjectionData(fields);
            this.beanInjectionMap.put(beanName, beanInjectionData);
        }
    }

    @Override
    public void afterPropertiesSet() {
        if(!StringUtils.isEmpty(appName)){
            injectPropertyService.start(appName, this.beanInjectionMap);
        }
    }

    /**
     * 在bean实例化后，对被@Conf注解的属性进行赋值
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        injectPropertyService.initBeanProperty(beanName, bean);
        return true;
    }

    private MergedAnnotation<?> findConfAnnotation(AccessibleObject ao) {
        MergedAnnotations annotations = MergedAnnotations.from(ao);
        for (Class<? extends Annotation> type : this.configAnnotationTypes) {
            MergedAnnotation<?> annotation = annotations.get(type);
            if (annotation.isPresent()) {
                return annotation;
            }
        }
        return null;
    }
}
