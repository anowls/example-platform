package org.zongzi.platform.operation.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.zongzi.platform.operation.socket.service.CompositeExecuteService;

import java.nio.charset.StandardCharsets;

@Slf4j
public class TyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private ObjectMapper objMapper = new ObjectMapper();

    private CompositeExecuteService defaultExecuteService = new CompositeExecuteService();

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {
        byte type = byteBuf.readByte(), flag = byteBuf.readByte();
        byteBuf.readInt();
        byte[] req = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(req);
        Object response = defaultExecuteService.apply(flag, new String(req, StandardCharsets.UTF_8));
        String responseString = objMapper.writeValueAsString(response);
        TyPayload tyPayload = new TyPayload(type, flag, responseString.length(), responseString);
        ctx.channel().writeAndFlush(tyPayload);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel(); // 其实相当于一个connection
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // 验证一下每次客户端断开连接，连接自动地从channelGroup中删除调。
        log.debug("channelGroup.size() = {}", channelGroup.size());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        log.debug("{} 上线了！", channel.remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        log.debug("{} 下线了！", channel.remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}