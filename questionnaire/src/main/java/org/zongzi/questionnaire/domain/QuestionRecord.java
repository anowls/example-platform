package org.zongzi.questionnaire.domain;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;

/**
 * @author haopeisong
 */
@Data @Builder
@Table(name = "EX_QT_RECORD")
@ApiModel(description = "问卷问题记录表")
public class QuestionRecord {

    @Id
    @Column(name = "ID")
    @ApiModelProperty(value = "主键ID")
    private String id;

    @Column(name = "QN_RC_ID")
    @ApiModelProperty(value = "问题记录ID")
    private String qnRcId;

    @Column(name = "QT_ID")
    @ApiModelProperty(value = "问题ID")
    private String qtId;

    @Column(name = "QT_TEXT")
    @ApiModelProperty(value = "问答题答案")
    private String text;

    @Column(name = "QT_GRADE")
    @ApiModelProperty(value = "评分题分数")
    private Integer grade;

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
