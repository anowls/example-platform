package org.zongzi.platform.operation.socket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;

class TyEncoder extends MessageToByteEncoder<TyPayload> {

    @Override
    protected void encode(ChannelHandlerContext ctx, TyPayload customMsg, ByteBuf out) throws Exception {
        if (null == customMsg) {
            throw new Exception("customMsg is null");
        }
        byte[] bodyBytes = customMsg.getBody().getBytes(StandardCharsets.UTF_8);

        //NSG:|1|1|4|BODY|
        out.writeByte(customMsg.getType());      //系统编号
        out.writeByte(customMsg.getFlag());      //信息标志
        out.writeInt(bodyBytes.length);   //消息长度
        out.writeBytes(bodyBytes);         //消息正文
    }
}