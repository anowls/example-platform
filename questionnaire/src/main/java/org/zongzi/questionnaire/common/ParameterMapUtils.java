package org.zongzi.questionnaire.common;

import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

import java.util.Map;

@UtilityClass
public class ParameterMapUtils {

    private final String DELIMITER = ",";

    public Map<String, Object> convertParameter(Map<String, String> filter) {
        Map<String, Object> returnMap = Maps.newHashMap();
        if (filter != null) {

            filter.forEach((k, v) -> {
                // 分页过滤
                String pageString = filter.get("page"), sizeString = filter.get("size");
                if (StringUtils.hasLength(pageString) && StringUtils.hasLength(sizeString)) {
                    Integer page = parseInteger(pageString, 1);
                    Integer size = parseInteger(sizeString, 10);
                    PageHelper.startPage(page, size);
                }

                // 时间段过滤
                String[] daterangeArray;
                String daterange = filter.get("daterange");
                if (StringUtils.hasLength(daterange) && (daterangeArray = daterange.split(DELIMITER)).length == 2) {
                    returnMap.put("startDate", Long.parseLong(daterangeArray[0]));
                    returnMap.put("endDate", Long.parseLong(daterangeArray[1]));
                }

            });
        }
        return returnMap;
    }

    private Integer parseInteger(String stringValue, Integer defaultValue) {
        if (StringUtils.hasLength(stringValue)) {
            int i = Integer.parseInt(stringValue);
            if (i > 0) {
                return i;
            }
        }
        return defaultValue;

    }
}
