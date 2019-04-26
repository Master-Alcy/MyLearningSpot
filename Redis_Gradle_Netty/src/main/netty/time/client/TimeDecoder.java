package netty.time.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import netty.time.UnixTime;

import java.util.List;

public class TimeDecoder extends ByteToMessageDecoder { // used to deal with fragmentation
    @Override // ByteBuf is an internally maintained cumulative buffer
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        if (in.readableBytes() < 4) {
            return; // return for not enough data. when more data received this would be called again
        }

//        out.add(in.readBytes(4)); // would auto discard read part of ByteBuf
        // this would keep being called for further ByteBuf received
        out.add(new UnixTime(in.readUnsignedInt()));
    }
}
