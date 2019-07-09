package org.zongzi.platform.operation.socket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 消息协议数据包
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TyPayload implements Serializable {

	private static final long serialVersionUID = 7140499873159136903L;

	/**
     * 类型  系统编号 1 表示A系统，2 表示B系统
     */
    private byte type;

    /**
     * <h4>保留信息标志：</h4>
     * <ol>
     * <li>0xAA(170)-心跳包</li>
     * <li>0xAB(171)-超时包</li>
     * <li>0xAC(172)-登录包</li>
     * <li>0xAD(173)-退出包</li>
     * </ol>
     * <h4>扩展信息标志：</h4>
     * <ol>
     * <li>0xBA(186)-打卡</li>
     * <li>0xBB(187)-</li>
     * <li>0xBC(188)-</li>
     * <li>0xBD(189)-</li>
     * </ol>
     */
    private byte flag;

    /**
     * 主题信息的长度
     */
    private int length;

    /**
     * 主题信息
     */
    private String body;
}
