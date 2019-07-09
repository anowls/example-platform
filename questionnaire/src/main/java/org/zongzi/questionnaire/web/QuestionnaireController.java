package org.zongzi.questionnaire.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zongzi.platform.common.ApiResponse;
import org.zongzi.questionnaire.domain.Question;
import org.zongzi.questionnaire.domain.Questionnaire;
import org.zongzi.questionnaire.service.QuestionnaireService;

import java.util.List;
import java.util.Map;


/**
 * @author haopeisong
 */
@RestController
@RequestMapping("/paper")
@Api(tags = "问卷调查")
public class QuestionnaireController {

    @Autowired
    private QuestionnaireService questionnaireService;


    @ApiOperation(value = "单张问卷", response = ApiResponse.class)
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userIdentity", dataTypeClass = String.class, paramType = "query")
    })
    public ApiResponse findOne(@ApiParam(name = "问卷ID") @PathVariable String id,
                                                          @ApiParam(hidden = true) @RequestParam Map<String, Object> filterMap) {
        return questionnaireService.selectById(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "获取问卷列表", response = ApiResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userIdentity", dataTypeClass = String.class, paramType = "query")
    })
    public ApiResponse findAll(@ApiParam(hidden = true) @RequestParam Map<String, String> filterMap) {
        return questionnaireService.selectAll(filterMap);
    }

    @ApiOperation(value = "新增/修改问卷", response = Questionnaire.class)
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public ApiResponse add(@ApiParam(value = "新增/修改问卷", required = true) @RequestBody Questionnaire questionnaire) {
        return questionnaireService.insert(questionnaire);
    }

    @ApiOperation(value = "删除问卷", response = ApiResponse.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ApiResponse delete(@PathVariable String id) {
        return questionnaireService.deleteByPrimaryKey(id);
    }


    @ApiOperation(value = "发布问卷", response = ApiResponse.class)
    @RequestMapping(value = "/upper/{id}", method = RequestMethod.PUT)
    public ApiResponse upper(@PathVariable String id) {
        return questionnaireService.upper(id);

    }

    @ApiOperation(value = "结束问卷", response = ApiResponse.class)
    @RequestMapping(value = "/stop/{id}", method = RequestMethod.PUT)
    public ApiResponse stop(@PathVariable String id) {
        return questionnaireService.stop(id);
    }


    @ApiOperation(value = "撤销问卷", response = ApiResponse.class)
    @RequestMapping(value = "/back/{id}", method = RequestMethod.PUT)
    public ApiResponse back(@PathVariable String id) {
        return questionnaireService.back(id);
    }

    @ApiOperation(value = "保存问卷", response = ApiResponse.class)
    @RequestMapping(value = "/save/{id}", method = RequestMethod.POST)
    public ApiResponse save(@PathVariable String id, @RequestBody List<Question> questions) {
        return questionnaireService.save(id, questions);
    }
}
