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
@Table(name = "EX_OP_RECORD")
@ApiModel(description = "问题选项记录表")
public class OptionRecord {

    @Id
    @Column(name = "ID")
    @ApiModelProperty(value = "主键ID")
    private String id;

    @Column(name = "QN_RC_ID")
    @ApiModelProperty(value = "问题记录ID")
    private String qnRcId;

    @Column(name = "OP_ID")
    @ApiModelProperty(value = "选项ID")
    private String opId;

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
