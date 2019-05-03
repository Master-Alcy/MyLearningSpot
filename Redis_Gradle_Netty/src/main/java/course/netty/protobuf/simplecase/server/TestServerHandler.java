package course.netty.protobuf.simplecase.server;

import course.protobuf.InfoData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class TestServerHandler extends SimpleChannelInboundHandler<InfoData.InfoWrapper> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, InfoData.InfoWrapper msg) throws Exception {
        InfoData.InfoWrapper.DataType type = msg.getDataType();
        System.out.println(msg);

        switch(type.getNumber()) {
            case 1:
                System.out.println(msg.getPerson());
                break;
            case 2:
                System.out.println(msg.getDog());
                break;
            case 3:
                System.out.println(msg.getCat());
                break;
            case 4:
                System.out.println(msg.getAddress());
                break;
        }
    }
}
