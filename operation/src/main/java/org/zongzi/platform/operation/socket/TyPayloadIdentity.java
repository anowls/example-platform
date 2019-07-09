package org.zongzi.platform.operation.socket;

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
 * <li>0xBA(186)-打卡包</li>
 * <li>0xBB(187)-</li>
 * <li>0xBC(188)-</li>
 * <li>0xBD(189)-</li>
 * </ol>
 *
 * @author haopeisong
 */
public interface TyPayloadIdentity {

    /**
     * 心跳包
     */
    byte FLAG_HEART = (byte) 0xAA;

    /**
     * 超时包
     */
    byte FLAG_TIMEOUT = (byte) 0xAB;

    /**
     * 登录包
     */
    byte FLAG_LOGIN = (byte) 0xAC;

    /**
     * 退出包
     */
    byte FLAG_LOGOUT = (byte) 0xAD;

    /**
     * 打卡包
     */
    byte FLAG_CLOCK = (byte) 0xBA;
}
