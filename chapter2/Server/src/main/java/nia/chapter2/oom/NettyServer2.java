package nia.chapter2.oom;

/**
 * create on 24/08/2018
 *
 * @author JASON.TAO
 */

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class NettyServer2 {


//    @Value("${server.websocket.port}")
    public Integer port = 8099;


    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
//
//    @Autowired
//    private FotaChannelHandler fotaChannelHandler;

    private void startServer(){
        //服务端需要2个线程组  boss处理客户端连接  work进行客服端连接之后的处理
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup work = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            //服务器 配置
            bootstrap.group(boss,work).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // HttpServerCodec：将请求和应答消息解码为HTTP消息
                            socketChannel.pipeline().addLast("http-codec",new HttpServerCodec());
                            // HttpObjectAggregator：将HTTP消息的多个部分合成一条完整的HTTP消息
                            socketChannel.pipeline().addLast("aggregator",new HttpObjectAggregator(65536));
                            // ChunkedWriteHandler：向客户端发送HTML5文件
                            socketChannel.pipeline().addLast("http-chunked",new ChunkedWriteHandler());
                            // 进行设置心跳检测
                            socketChannel.pipeline().addLast(new IdleStateHandler(120,120,60*30, TimeUnit.SECONDS));
                            // 配置通道处理  来进行业务处理
                            socketChannel.pipeline().addLast(new FotaChannelHandler());
                        }
                    }).option(ChannelOption.SO_BACKLOG,1024).childOption(ChannelOption.SO_KEEPALIVE,true)
            .childOption(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(10 * 1024 * 1024,10 * 1024 * 1024));
            //  Socket参数，服务端接受连接的队列长度，如果队列已满，客户端连接将被拒绝。默认值，Windows为200，其他为128。
            //  Socket参数，连接保活，默认值为False。启用该功能时，TCP会主动探测空闲连接的有效性。可以将此功能视为TCP的心跳机制，需要注意的是：默认的心跳间隔是7200s即2小时。Netty默认关闭该功能。

            //绑定端口  开启事件驱动
            System.out.println("ws>>【服务器启动成功========端口："+port+"】");
            Channel channel = bootstrap.bind(port).sync().channel();
            channel.closeFuture().sync();
        }catch (Exception e){
            System.out.println("ws>>【服务器启动失败========端口："+port+"】");
        }finally {
            //关闭资源
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        NettyServer2 nettyServer2 = new NettyServer2();
        nettyServer2.startServer();

    }
}