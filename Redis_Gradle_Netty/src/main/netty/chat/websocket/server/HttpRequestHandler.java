package netty.chat.websocket.server;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;

public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final String wsUri;
    private static final File INDEX;

    static {
        // I put the html at /out/production/classes/...
        URL location = HttpRequestHandler.class.getProtectionDomain().getCodeSource().getLocation();
        System.out.println(location); // added
        try {
            String path = location.toURI() + "WebsocketChatClient.html";
            System.out.println(path); // added
            path = !path.contains("file:") ? path : path.substring(5);
            INDEX = new File(path);
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Can't locate WebsocketChatClient.html");
        }
    }

    public HttpRequestHandler(String wsUri) {
        this.wsUri = wsUri;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
        if (wsUri.equalsIgnoreCase(req.uri())) { // request for websocket upgrade
            ctx.fireChannelRead(req.retain()); // keep this and pass to next handler
        } else {
            if (HttpUtil.is100ContinueExpected(req)) { // handles HTTP 1.1 "100 Continue" request
                FullHttpResponse res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
                ctx.writeAndFlush(res);
            }

            RandomAccessFile file = new RandomAccessFile(INDEX, "r");
            HttpResponse res = new DefaultHttpResponse(req.protocolVersion(), HttpResponseStatus.OK);
            res.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");

            boolean keepAlive = HttpUtil.isKeepAlive(req);
            if (keepAlive) {
                res.headers().set(HttpHeaderNames.CONTENT_LENGTH, file.length());
                res.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            }
            ctx.write(res); // write HttpResponse to Client

            if (ctx.pipeline().get(SslHandler.class) == null) {
                ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
            } else {
                ctx.write(new ChunkedNioFile(file.getChannel()));
            }
            ChannelFuture f = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT); // finished response

            if (!keepAlive) {
                f.addListener(ChannelFutureListener.CLOSE);
            }
            file.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("Client:" + ctx.channel().remoteAddress() + " Error");
        cause.printStackTrace();
        ctx.close();
    }
}
