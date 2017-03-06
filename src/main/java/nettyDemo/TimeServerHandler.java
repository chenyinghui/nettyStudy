package nettyDemo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

public class TimeServerHandler extends ChannelHandlerAdapter{
	
	public TimeServerHandler() {
	
	}

	AttributeKey<Long> loginTime = AttributeKey.valueOf("loginTime");
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf)msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req,"UTF-8");
		
		System.err.println("recieve : " + body);
		long millis = System.currentTimeMillis();
		Attribute<Long> attribute = ctx.attr(loginTime);
		Long loginTime = attribute.get();
		if(loginTime == null || loginTime == 0){
			attribute.set(millis);
		}
		if(millis - attribute.get() > 15000){
			ctx.close();
		}
//		ByteBuf resp = Unpooled.copiedBuffer(req);
//		ctx.write(resp);
		
		//发送201协议
		ByteBuf resp = Unpooled.copiedBuffer(new byte[]{2, 0, -55, 0, 0, 0, -50, 123, 34, 111, 119, 110, 73, 110, 102, 111, 34, 58, 123, 34, 103, 111, 108, 100, 34, 58, 49, 48, 48, 48, 48, 44, 34, 119, 101, 105, 122, 104, 105, 34, 58, 49, 44, 34, 105, 109, 103, 34, 58, 34, 104, 116, 116, 112, 115, 58, 47, 47, 115, 101, 114, 46, 99, 112, 109, 114, 106, 46, 99, 111, 109, 47, 116, 111, 117, 120, 105, 97, 110, 103, 47, 49, 52, 48, 47, 49, 49, 52, 48, 46, 112, 110, 103, 34, 44, 34, 108, 111, 103, 105, 110, 73, 112, 34, 58, 34, 49, 48, 46, 48, 46, 49, 46, 49, 55, 50, 34, 44, 34, 110, 97, 109, 101, 34, 58, 34, 106, 107, 102, 103, 115, 107, 106, 34, 44, 34, 105, 115, 79, 110, 108, 105, 110, 101, 34, 58, 116, 114, 117, 101, 44, 34, 117, 115, 101, 114, 73, 100, 34, 58, 34, 49, 34, 44, 34, 112, 108, 97, 121, 101, 114, 73, 100, 34, 58, 49, 49, 52, 48, 125, 44, 34, 105, 115, 79, 112, 101, 110, 34, 58, 102, 97, 108, 115, 101, 44, 34, 105, 115, 83, 117, 99, 99, 101, 115, 115, 34, 58, 116, 114, 117, 101, 125, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
		ctx.write(resp);
		
		
//		System.err.println("Time Server receive order : " + body);
//		String currentTime = "Query time".equalsIgnoreCase(body) ? new Date().toString() : "Bad order";
//		
//		ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
//		ctx.write(resp);
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
}
