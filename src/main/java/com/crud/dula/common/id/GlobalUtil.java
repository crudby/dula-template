package com.crud.dula.common.id;

/**
 * @author crud
 * @date 2023/9/13
 */
public class GlobalUtil {

    private static final SnowFlake SN = new SnowFlake(1, 1);

    /**
     * 获取唯一Id
     * @return newId
     */
    public static Long getNextId() {
        return SN.nextId();
    }
}
