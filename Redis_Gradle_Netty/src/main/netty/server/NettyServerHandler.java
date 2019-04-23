package netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**
 * @apiNote  ChannelInboundHandlerAdapter extends the ChannelInboundHandler which
 * provides many event handling interfaces. ChannelInboundHandlerAdapter is a Abstract
 * base class.
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter  {

    /**
     * This method is called whenever received new data from client
     * Normally this should use try {...} finally {....release(msg)}
     */
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        LookReceivedData(msg);
//        echo(ctx, msg);
//    }

    /**
     * TIME protocol. Without request it would send 32 bit Integer, then close
     * the connection.
     * <br>
     * channelActive is called when connection has been established
     */
    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        final ByteBuf time = ctx.alloc().buffer(4);
        time.writeInt((int)(System.currentTimeMillis() / 1000L + 2208988800L));

        final ChannelFuture f = ctx.writeAndFlush(time); // async
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                assert f == future;
                ctx.close();
            }
        });
//        f.addListener(ChannelFutureListener.CLOSE); // same as this
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // close connect when exception
        System.out.println(cause.getMessage());
        cause.printStackTrace();
        ctx.close();
    }

    private void LookReceivedData(Object msg) {
        ByteBuf in = (ByteBuf) msg;
        try {
            while(in.isReadable()) {
                System.out.print((char)in.readByte());
                System.out.flush();
            }
            System.out.print(in.toString(CharsetUtil.US_ASCII)); // works the same
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    private void echo(ChannelHandlerContext ctx, Object msg) {
        ctx.write(msg); // auto released by Netty, then cached inside
        ctx.flush();  // output the data in cache
//        ctx.writeAndFlush(msg); // Or this.
    }
}
