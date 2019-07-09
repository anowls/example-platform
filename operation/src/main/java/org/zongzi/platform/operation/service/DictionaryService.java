package org.zongzi.platform.operation.service;

import org.zongzi.platform.common.ApiResponse;
import org.zongzi.platform.operation.domain.SDictionary;
import org.zongzi.platform.operation.domain.SDictionaryItem;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DictionaryService {

    SDictionary findOne(String id);

    List<SDictionary> findAll(Map<String, String> filterMap);

    ApiResponse save(SDictionary dictionary);

    ApiResponse delete(Set<String> ids);

    SDictionaryItem findOneItem(String id);

    List<SDictionaryItem> findAllItem(Map<String, String> filterMap);

    ApiResponse saveItem(List<SDictionaryItem> dictionaryItems);

    ApiResponse deleteItem(Set<String> ids);

    // ===接口

    /**
     * 根据字段代码获取字典项集合
     *
     * @param sdCodes 字典代码集合
     * @return 字典项列表
     */
    List<SDictionaryItem> findItemByDictionaryCode(Set<String> sdCodes);
}
