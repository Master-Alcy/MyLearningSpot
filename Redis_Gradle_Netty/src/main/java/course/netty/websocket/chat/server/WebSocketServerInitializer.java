package course.netty.websocket.chat.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast("HttpCodec", new HttpServerCodec())
                .addLast(new ChunkedWriteHandler()) // like as block
                .addLast(new HttpObjectAggregator(8192)) // aggregate to FullHttpRequest or FullHttpRespond
                .addLast(new WebSocketServerProtocolHandler("/ws")) // handshake and control close, text and binary are forwarded
                .addLast(new TextWebSocketFrameHandler());
    }
}
