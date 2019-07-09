package org.zongzi.questionnaire.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zongzi.platform.common.ApiResponse;
import org.zongzi.platform.common.utils.IDUtils;
import org.zongzi.questionnaire.service.QuestionAnswerService;

import java.util.List;
import java.util.Map;

/**
 * @author haopeisong
 */
@RestController
@RequestMapping("/answer")
@Api(tags = "问卷答案")
public class QuestionController {

    @Autowired
    private QuestionAnswerService questionAnswerService;

    @ApiOperation(value = "问卷答案", response = ApiResponse.class)
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ApiResponse selectAll(@RequestParam Map<String, String> filter) {
        filter.put("operatorId", IDUtils.loginUserId(filter.remove("operatorId")));
        return questionAnswerService.selectAll(filter);
    }

    @ApiOperation(value = "单张问卷", response = ApiResponse.class)
    @RequestMapping(value = "/{id}/{operatorId}", method = RequestMethod.GET)
    public ApiResponse selectById(@PathVariable String id, @PathVariable String operatorId) {
        return questionAnswerService.selectAnsById(id, IDUtils.loginUserId(operatorId));
    }


    @ApiOperation(value = "保存答案", response = ApiResponse.class)
    @RequestMapping(value = "/{id}/{operatorId}", method = RequestMethod.POST)
    public ApiResponse save(@PathVariable String id, @PathVariable String operatorId,
                            @RequestBody List<Map<String, Object>> questions) {
        return questionAnswerService.save(id, IDUtils.loginUserId(operatorId), questions);
    }
}
