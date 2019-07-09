package org.zongzi.platform.operation.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.zongzi.platform.common.ApiResponse;
import org.zongzi.platform.common.utils.CommonUtils;
import org.zongzi.platform.common.utils.ExampleUtils;
import org.zongzi.platform.operation.domain.SDictionary;
import org.zongzi.platform.operation.domain.SDictionaryItem;
import org.zongzi.platform.operation.mapper.DictionaryItemMapper;
import org.zongzi.platform.operation.mapper.DictionaryMapper;
import org.zongzi.platform.operation.service.DictionaryService;
import tk.mybatis.mapper.weekend.Weekend;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Service
public class DictionaryServiceImpl implements DictionaryService {

    private final DictionaryMapper dictionaryMapper;

    private final DictionaryItemMapper dictionaryItemMapper;

    public DictionaryServiceImpl(DictionaryMapper dictionaryMapper, DictionaryItemMapper dictionaryItemMapper) {
        this.dictionaryMapper = dictionaryMapper;
        this.dictionaryItemMapper = dictionaryItemMapper;
    }

    // ===字典===
    @Override
    public SDictionary findOne(String id) {
        SDictionary dictionary = null;
        if (StringUtils.hasLength(id)) {
            dictionary = dictionaryMapper.selectByPrimaryKey(id);
        }
        if (dictionary == null) {
            log.info("根据ID[{}]未查询到任何字典记录。", id);
        }
        return dictionary;
    }

    @Override
    public List<SDictionary> findAll(Map<String, String> filterMap) {
        Weekend<SDictionary> example = ExampleUtils.example(SDictionary.class, null);
        return dictionaryMapper.selectByExample(example);
    }

    @Override
    public ApiResponse save(SDictionary dictionary) {
        if (Objects.nonNull(dictionary)) {
            // 验证字典代码是在数据库中已存在
            SDictionary template = new SDictionary();
            template.setId(dictionary.getId());
            template.setSdCode(dictionary.getSdCode());
            int i = dictionaryMapper.selectCount(template);
            if (i > 0) {
                String warningMsg = MessageFormat.format("字典代码[{0}]在系统中已存在", dictionary.getSdCode());
                return ApiResponse.warning(warningMsg);
            }
            // 执行新增或者修改操作
            if (StringUtils.isEmpty(dictionary.getId())) {
                CommonUtils.setBeanInfo(dictionary, false);
                dictionary.setDeleted(false);
                dictionaryMapper.insertSelective(dictionary);
                return ApiResponse.success("");
            }
            CommonUtils.setBeanInfo(dictionary, true);
            dictionary.setDeleted(false);
            return ApiResponse.success("");
        }
        return ApiResponse.warning("没有可保存的字典信息");
    }

    @Override
    public ApiResponse delete(Set<String> ids) {
        return null;
    }

    // ===字典项===
    @Override
    public SDictionaryItem findOneItem(String id) {
        SDictionaryItem dictionaryItem = null;
        if (StringUtils.hasLength(id)) {
            dictionaryItem = dictionaryItemMapper.selectByPrimaryKey(id);
        }
        if (dictionaryItem == null) {
            log.info("根据ID[{}]未查询到任何字典项记录。", id);
        }
        return dictionaryItem;
    }

    @Override
    public List<SDictionaryItem> findAllItem(Map<String, String> filterMap) {
        return null;
    }

    @Override
    public ApiResponse saveItem(List<SDictionaryItem> SDictionaryItems) {
        return null;
    }

    @Override
    public ApiResponse deleteItem(Set<String> ids) {
        return null;
    }

    @Override
    public List<SDictionaryItem> findItemByDictionaryCode(Set<String> sdCodes) {
        if (CollectionUtils.isEmpty(sdCodes)) {
            return Collections.emptyList();
        }
        Weekend<SDictionaryItem> dictionaryItemWeekend = ExampleUtils.example(SDictionaryItem.class,
                criteriaConsumer -> criteriaConsumer.andIn(o -> o.getSdCode(), sdCodes));
        dictionaryItemWeekend.orderBy("sdiSequence").asc();
        return dictionaryItemMapper.selectByExample(dictionaryItemWeekend);
    }
}
