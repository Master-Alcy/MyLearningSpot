package netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

    private int port;

    public NettyServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        /**
         * @param EventLoopGroup deal with I/O operation with multi-threading
         * event loop. Netty has different EventLoopGroup for different transfer
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup(); /** Receive connection */
        EventLoopGroup workerGroup = new NioEventLoopGroup(); /** Handle received connection */

        try {
            ServerBootstrap b = new ServerBootstrap(); /** Helper function to start NIO service */
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) /** new channel receive connection */
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        /**
                         * used to operate recently received Channel.
                         * ChannelInitializer is used to help build a new Channel
                         */
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new NettyServerHandler());
                        }
                    })
                    /** Options for Channel in NioServerSocketChannel */
                    .option(ChannelOption.SO_BACKLOG, 128)
                    /** Connection for parent pipeline ServerChannel.
                     * Still NioServerSocketChannel in this case
                     */
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(port).sync();

            // wait server socket close
            // for this case, nay, but we can close it either
            f.channel().closeFuture().sync();

        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 7777;
        }

        new NettyServer(port).run();
    }
}
