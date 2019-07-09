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
@Table(name = "EX_OPTION")
@ApiModel(description = "问题选项表")
public class Option implements Serializable {

    @Id
    @Column(name = "ID")
    @ApiModelProperty(value = "主键ID")
    private String id;

    @Column(name = "QT_ID")
    @ApiModelProperty(value = "问题ID")
    private String qtId;

    @Column(name = "OP_TITLE")
    @ApiModelProperty(value = "选项名称")
    private String title;

    @Column(name = "OP_SEQUENCE")
    @ApiModelProperty(value = "选项序号")
    private Integer sequence;

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
