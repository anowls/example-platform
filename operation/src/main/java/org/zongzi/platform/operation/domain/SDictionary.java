package org.zongzi.platform.operation.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zongzi.platform.common.domain.BaseDomain;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "S_DICTIONARY")
public class SDictionary extends BaseDomain implements Serializable {

    private static final long serialVersionUID = 815180404826627879L;

    @Column(name = "SD_CODE", nullable = false)
    private String sdCode;

    @Column(name = "SD_NAME", nullable = false)
    private String sdName;

    @Column(name = "SD_DESC", nullable = false)
    private String sdDesc = "";

    @Column(name = "STATUS", nullable = false)
    private Integer status;

}
