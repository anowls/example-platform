package org.zongzi.platform.operation.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.zongzi.platform.common.domain.BaseDomain;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author haopeisong
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "S_MENU")
public class SMenu extends BaseDomain implements Serializable {

    @Column(name = "parent_id")
    private String parentId;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_desc")
    private String menuDesc;
}
