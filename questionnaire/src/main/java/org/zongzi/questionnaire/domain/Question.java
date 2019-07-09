package org.zongzi.questionnaire.domain;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author haopeisong
 */
@Data
@Table(name = "EX_QUESTION")
@ApiModel(description = "问卷问题")
public class Question implements Serializable {

    @Id
    @Column(name = "ID")
    @ApiModelProperty(value = "主键ID")
    private String id;

    @Column(name = "QN_ID")
    @ApiModelProperty(value = "问卷ID")
    private String qnId;

    @Column(name = "QT_TITLE")
    @ApiModelProperty(value = "问题名称")
    private String title;

    @Column(name = "QT_DESCRIPTION")
    @ApiModelProperty(value = "问题描述")
    private String description;

    @Column(name = "QT_SEQUENCE")
    @ApiModelProperty(value = "问题序号")
    private Integer sequence;

    @Column(name = "QT_REQUIRED")
    @ApiModelProperty(value = "是否必答（0-否 1-是）")
    private Byte required;

    @Column(name = "QT_MIN_VALUE")
    @ApiModelProperty(value = "最小值（多选题-选项下限； 问答题-字数下限 评分题-最低分）")
    private String minValue;

    @Column(name = "QT_MAX_VALUE")
    @ApiModelProperty(value = "最大值（多选题-选项上限；问答题-字数上限；评分题-最高分）")
    private String maxValue;

    @Column(name = "QT_TYPE")
    @ApiModelProperty(value = "问题类型（0-单选 1-多选提 2-量表题 3-评分题 4-问答题）")
    private Byte type;

    @Column(name = "QT_DEFAULT_VALUE")
    @ApiModelProperty(value = "默认值（评分题）")
    private Integer defaultValue;

    @Column(name = "QT_MIN_DESC")
    @ApiModelProperty(value = "最低分描述（评分题）")
    private String minDesc;

    @Column(name = "QT_MAX_DESC")
    @ApiModelProperty(value = "最高分描述（评分题）")
    private String maxDesc;

    @Getter(onMethod_ = {@JsonAnyGetter})
    private transient Map<String, Object> extraMap = new HashMap<>();

    @JsonAnySetter
    public void setExtraMap(String key, Object value) {
        extraMap.put(key, value);
    }

    public void get(String key) {
        extraMap.get(key);
    }
}
