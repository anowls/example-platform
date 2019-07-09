package org.zongzi.platform.common.utils;

import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.zongzi.platform.common.domain.BaseDomain;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendCriteria;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public final class ExampleUtils {

    public static <T> Weekend<T> example(Class<T> cls, Consumer<WeekendCriteria<T, Object>> criteriaConsumer) {
        Weekend<T> of = Weekend.of(cls);
        if (criteriaConsumer != null) {
            criteriaConsumer.accept(of.weekendCriteria());
        }
        return of;
    }

    /**
     * 构造 Example ，过滤掉删除状态的数据
     *
     * @param cls
     * @param criteriaConsumer
     * @param <T>
     * @return
     */
    public static <T extends BaseDomain> Weekend<T> exampleNoDeleted(Class<T> cls, Consumer<WeekendCriteria<T, Object>> criteriaConsumer) {
        Weekend<T> of = Weekend.of(cls);
        WeekendCriteria<T, Object> tObjectWeekendCriteria = of.weekendCriteria().andEqualTo(T::getDeleted, false);
        if (criteriaConsumer != null) {
            criteriaConsumer.accept(tObjectWeekendCriteria);
        }
        return of;
    }

    /**
     * Weekend + WeekendCriteria
     *
     * @param map              待处理参数
     * @param cls              实体域Class
     * @param criteriaFunction 自定义查询条件
     * @param excludeKeys      排除的参数
     * @param <T>              实体类型
     * @return 查询对象
     */
    public static <T> Example selectExample(Map<String, String> map, Class<T> cls, BiFunction<WeekendCriteria<T, Object>, Map.Entry<String, String>, Boolean> criteriaFunction, String... excludeKeys) {
        Weekend<T> weekend = Weekend.of(cls);
        WeekendCriteria<T, Object> weekendCriteria = weekend.weekendCriteria();
        Set<Map.Entry<String, String>> entries = map.entrySet();
        String likeIdentity = "_%";
        for (Map.Entry<String, String> entry : entries) {
            String key = entry.getKey(), value = entry.getValue();
            boolean isLike = (key != null && key.endsWith(likeIdentity));
            Field field = ReflectionUtils.findField(cls, key = (isLike ? key.replace(likeIdentity, "") : key));
            if (field == null || StringUtils.isEmpty(value)) {
                continue;
            }

            if (!Arrays.asList(excludeKeys).contains(key)) {
                boolean isContinue = false;
                if (criteriaFunction != null) {
                    isContinue = criteriaFunction.apply(weekendCriteria, entry);
                }
                if (!isContinue) {
                    // 模糊查询
                    if (isLike && StringUtils.hasLength(value)) {
                        weekendCriteria.andLike(key, MessageFormat.format("%{0}%", value));
                        continue;
                    }
                    // 数组查询
                    if (value.contains(",")) {
                        String[] valueArray = StringUtils.commaDelimitedListToStringArray(StringUtils.trimAllWhitespace(value));
                        if (valueArray != null) {
                            weekendCriteria.andIn(key, Arrays.stream(valueArray).collect(Collectors.toSet()));
                        }
                        continue;
                    }
                    weekendCriteria.andEqualTo(key, value);
                }
            }
        }

        return weekend;
    }
}
