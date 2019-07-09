package org.zongzi.platform.operation.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.zongzi.platform.common.domain.BaseDomain;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

@Value
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "S_DICTIONARY_ITEM")
public class SDictionaryItem extends BaseDomain implements Serializable {

    private static final long serialVersionUID = 815180404826627879L;

    @Column(name = "SD_ID", nullable = false)
    private String sdId;

    @Column(name = "SD_CODE", nullable = false)
    private String sdCode;

    @Column(name = "SDI_PARENT_ID", nullable = false)
    private String sdiParentId;

    @Column(name = "SDI_PARENT_CODE", nullable = false)
    private String sdiParentCode;

    @Column(name = "SDI_CODE", nullable = false)
    private String sdiCode;

    @Column(name = "SDI_NAME", nullable = false)
    private String sdiName;

    @Column(name = "SDI_DESC")
    private String sdiDesc;

    @Column(name = "SDI_SEQUENCE", nullable = false)
    private Integer sdiSequence;

    @Column(name = "STATUS", nullable = false)
    private Integer status;
}
