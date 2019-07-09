package org.zongzi.questionnaire.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zongzi.platform.common.domain.BaseDomain;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author haopeisong
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "EX_QUESTIONNAIRE")
@ApiModel(description = "问卷表")
public class Questionnaire extends BaseDomain implements Serializable {

    private static final long serialVersionUID = -8848556025758881257L;

    @Column(name = "QN_TITLE", length = 100)
    @ApiModelProperty(value = "问卷标题")
    private String title;

    @Column(name = "QN_DESCRIPTION")
    @ApiModelProperty(value = "问卷描述")
    private String description;

    @Column(name = "QN_TYPE")
    @ApiModelProperty(value = "问卷类型（0-实名 1-匿名）")
    private Byte type = 0;

    @Column(name = "QN_STATUS")
    @ApiModelProperty(value = "问卷状态（0-未发布 1-发布 2-已结束）")
    private Short status;

    @Column(name = "QN_START_TIME")
    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @Column(name = "QN_END_TIME")
    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @Column(name = "QN_BUSINESS_ID")
    @ApiModelProperty(value = "问卷业务ID")
    private String businessId;

}
