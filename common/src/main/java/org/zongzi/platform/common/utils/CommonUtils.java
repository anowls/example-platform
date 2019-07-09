package org.zongzi.platform.common.utils;

import org.springframework.util.CollectionUtils;
import org.zongzi.platform.common.domain.BaseDomain;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

public final class CommonUtils {

    public static String id() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String loginUserId() {
        return "7b5aeb0ec9684238a57252ff4f4dd92d";
    }

    public static <T extends BaseDomain> void setBeanInfo(T object, boolean modifier) {
        if (object != null) {
            String loginUserId = loginUserId();
            LocalDateTime nowDateTime = LocalDateTime.now();
            if (modifier) {
                object.setModifierId(loginUserId);
                object.setModifyTime(nowDateTime);
                return;
            }
            object.setId(id());
            object.setCreatorId(loginUserId);
            object.setCreateTime(nowDateTime);
        }
    }

    public static <T extends BaseDomain> void setBeanInfo(Collection<T> objects, boolean modifier) {
        if (CollectionUtils.isEmpty(objects)) return;
        LocalDateTime nowDateTime = LocalDateTime.now();
        String loginUserId = loginUserId();
        for (T object : objects) {
            if (object == null) continue;
            if (modifier) {
                object.setModifierId(loginUserId);
                object.setModifyTime(nowDateTime);
                return;
            }
            object.setId(id());
            object.setCreatorId(loginUserId);
            object.setCreateTime(nowDateTime);
        }
    }
}
