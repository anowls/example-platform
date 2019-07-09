package org.zongzi.platform.operation.socket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TyServerSocket {

    private static final LengthFieldBasedFrameDecoder FRAME_DECODER = new LengthFieldBasedFrameDecoder(
            TySocketConstant.MAX_FRAME_LENGTH,
            TySocketConstant.LENGTH_FIELD_OFFSET,
            TySocketConstant.LENGTH_FIELD_LENGTH,
            TySocketConstant.LENGTH_ADJUSTMENT,
            TySocketConstant.INITIAL_BYTES_TO_STRIP,
            TySocketConstant.FAIL_FAST);

    public void run() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup bossGroup = new NioEventLoopGroup(), workerGroup = new NioEventLoopGroup();
        bootstrap.group(bossGroup, workerGroup)
                .option(ChannelOption.SO_BACKLOG, 128)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(FRAME_DECODER);
                        p.addLast(new TyEncoder());
                        p.addLast(new TyServerHandler());
                    }
                });

        try {
            ChannelFuture future = bootstrap.bind(TySocketConstant.PORT).sync();
            if (future.isSuccess()) {
                log.info("SocketServer already started！");
            } else {
                log.error("TyServerSocket startup failure.", future.cause());
            }
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("TyServerSocket startup failure.", e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
