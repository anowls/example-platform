package org.zongzi.questionnaire.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author haopeisong
 */
@Data
@Builder
@Table(name = "EX_QN_RECORD")
@ApiModel(description = "问卷记录")
public class QuestionnaireRecord implements Serializable {

    @Id
    @Column(name = "ID")
    @ApiModelProperty(value = "主键ID")
    private String id;

    @Column(name = "QN_ID")
    @ApiModelProperty(value = "问卷ID")
    private String qnId;

    @Column(name = "OPERATOR_ID")
    @ApiModelProperty(value = "操作人ID")
    private String operatorId;

    @Column(name = "OPERATE_TIME")
    @ApiModelProperty(value = "操作时间")
    private Date operateTime;

}
