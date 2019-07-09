/*
 *
 */
package org.zongzi.platform.operation.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
@EqualsAndHashCode(callSuper = true)
@Table(name = "S_ACCOUNT")
public class SAccount extends BaseDomain implements Serializable {

    private static final long serialVersionUID = -6105836421325730081L;

    @Column(name = "USERNAME", length = 20, nullable = false)
    private String username;

    @Column(name = "PASSWORD", length = 50, nullable = false)
    private String password;

    @Column(name = "REAL_NAME", length = 30, nullable = false)
    private String realName;

    @Column(name = "ACCOUNT_AVATAR", length = 50)
    private String accountAvatar;

    /**
     * 用户状态（1-正常 2-锁定）
     */
    @Column(name = "STATUS", nullable = false)
    private Byte status = 1;

}
