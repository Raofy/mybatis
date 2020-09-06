package com.ryan.dynamicsql.utils;

import java.util.UUID;

public class IDUtils {

    /**
     * 自动生成UUID
     *
     * @return
     */
    public static String getID() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
