package nia.chapter2.echoserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Listing 2.2 EchoServer class
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args)
        throws Exception {
//        if (args.length != 1) {
//            System.err.println("Usage: " + EchoServer.class.getSimpleName() +
//                " <port>"
//            );
//            return;
//        }
//        args = {"8989"};
//        int port = Integer.parseInt(args[0]);
        new EchoServer(8989).start();
    }

    public void start() throws Exception {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            //所以你指定了 NioEventLoopGroup 来接受和处理新的连接
            //通常需要两个NioEventLoopGroup 因为服务器需要两组不同的 Channel。第一组将只包含一个 ServerChannel，代表服务 器自身的已绑定到某个本地端口的正在监听的套接字。而第二组将包含所有已创建的用来处理传 入客户端连接(对于每个服务器已经接受的连接都有一个)的 Channel
            b.group(group)
                    //并且将Channel的类型指定为NioServerSocketChannel
                    .channel(NioServerSocketChannel.class)
                    //将本地地址设置为一个具有选定端口的 InetSocketAddress 。服务器将绑定到这个地址以监听新的连接请求
                    .localAddress(new InetSocketAddress(port))
                    //当一个新的连接被接受时，一个新的子Channel将会被创建，而ChannelInitializer 将会把一个你的 EchoServerHandler 的实例添加到该 Channel 的 ChannelPipeline 中
                    //ChannelInitializer将在 ChannelPipeline 中安装一组自定义的 ChannelHandler;
                    // ChannelInitializer 将它自己从 ChannelPipeline 中移除
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(serverHandler);
                        }
                    })
                    .childOption(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(10 * 1024 * 1024,10 * 1024 * 1024));;

            //绑定了服务器   ，并等待绑定完成
            ChannelFuture f = b.bind().sync();
            System.out.println(EchoServer.class.getName() +
                " started and listening for connections on " + f.channel().localAddress());
            //该应用程序将会阻塞等待直到服务器的 Channel 关闭
            //因为在 Channel 的 CloseFuture 上调用了 sync()方法
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }
}
