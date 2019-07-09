package org.zongzi.platform.operation.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zongzi.platform.common.ApiResponse;
import org.zongzi.platform.operation.domain.SDictionaryItem;
import org.zongzi.platform.operation.service.DictionaryService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
public class DictionaryController {

    private final DictionaryService dictionaryService;

    @Autowired
    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @GetMapping(value = "/findItemByDictionaryCode")
    ApiResponse findItemByDictionaryCode(@RequestParam String sdCodes, @RequestParam(required = false) Boolean requireMap) {
        List<String> sdCodeList = null;
        if (StringUtils.hasLength(sdCodes)) {
            sdCodeList = Arrays.asList(sdCodes.split(","));
        }
        if (!CollectionUtils.isEmpty(sdCodeList)) {
            List<SDictionaryItem> itemByDictionaryCode = dictionaryService.findItemByDictionaryCode(new HashSet<>(sdCodeList));
            if (requireMap != null && requireMap) {
                Map<String, SDictionaryItem> sdCodeMap = itemByDictionaryCode.stream().collect(Collectors.toMap(SDictionaryItem::getSdCode, Function.identity()));
                return ApiResponse.success(sdCodeMap);
            }
            return ApiResponse.success(itemByDictionaryCode);
        }
        return ApiResponse.warning("请指定字典编码");
    }
}
