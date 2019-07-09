package org.zongzi.platform.common.utils;

import org.springframework.util.StringUtils;

import java.util.UUID;

public final class IDUtils {

    /**
     * 生成并返回主键ID
     *
     * @return 主键ID
     */
    public static String id() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取当前登录用户ID
     *
     * @return 当前登录用户ID
     */
    public static String loginUserId() {
        return "00000000000000000000000000000000";
    }

    /**
     * 获取当前登录用户ID
     *
     * @return 当前登录用户ID
     */
    public static String loginUserId(String userId) {
        if (StringUtils.hasLength(userId)) {
            return userId;
        }
        return "00000000000000000000000000000000";
    }
}
