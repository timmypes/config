package com.tim.configmanager.utils;

import java.util.UUID;

/**
 * @description:
 * @author: li si
 */
public class UUIDUtils {
    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
