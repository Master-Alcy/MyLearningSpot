package course.netty.protobuf.simplecase.client;

import course.protobuf.InfoData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

public class TestClientHandler extends SimpleChannelInboundHandler<InfoData.InfoWrapper> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, InfoData.InfoWrapper msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int randomInt = new Random().nextInt(4);
        InfoData.InfoWrapper myMsg = null;
        switch (randomInt) {
            case 0:
                myMsg = InfoData.InfoWrapper.newBuilder()
                        .setDataType(InfoData.InfoWrapper.DataType.PersonType)
                        .setPerson(InfoData.Person.newBuilder()
                                .setName("aPerson").setAge(111).setAddress("Beijing").build())
                        .build();
                break;
            case 1:
                myMsg = InfoData.InfoWrapper.newBuilder()
                        .setDataType(InfoData.InfoWrapper.DataType.DogType)
                        .setDog(InfoData.Dog.newBuilder()
                                .setName("aDog").setAge(22).build())
                        .build();
                break;
            case 2:
                myMsg = InfoData.InfoWrapper.newBuilder()
                        .setDataType(InfoData.InfoWrapper.DataType.CatType)
                        .setCat(InfoData.Cat.newBuilder()
                                .setName("aCat").setCity("Jilin").build())
                        .build();
                break;
            case 3:
                myMsg = InfoData.InfoWrapper.newBuilder()
                        .setDataType(InfoData.InfoWrapper.DataType.AddressType)
                        .setAddress(InfoData.Address.newBuilder()
                                .setAddressName("aAddress").build())
                        .build();
                break;
        }

        ctx.channel().writeAndFlush(myMsg);
    }
}
