package com.generallycloud.nio.codec.nio;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.generallycloud.nio.buffer.ByteBuf;
import com.generallycloud.nio.codec.nio.future.NIOReadFutureImpl;
import com.generallycloud.nio.component.IOSession;
import com.generallycloud.nio.protocol.IOReadFuture;
import com.generallycloud.nio.protocol.ProtocolDecoder;

/**
 * <pre>
 *  B0 - B10:
 * 
 *  B0：
 *  +-----------------------------------+
 *  |                 B0                |
 *  +   -   -   -   -   -   -   -   -   +
 *  |   0   1   2   3   4   5   6   7   | 
 *  |   -   -   -   -   -   -   -   -   + 
 *  |  T Y P E|      Service  Name      |
 *  +-----------------------------------+
 *  
 *  Type:高两位，类型 [0=RESERVED，1=RESERVED，2=BEAT.PING, 3=BEAT.PONG]
 *  ServiceName:低六位，service name的长度
 *  
 *  B1  - B4 ：future id
 *  B5  - B8 : hash code
 *  B9  - B10 ：text content的长度
 *  B11 - B14 ：binary content的长度
 * 
 * </pre>
 */
public class NIOProtocolDecoder implements ProtocolDecoder {

	public static final byte	PROTOCOL_PING				= 2;
	public static final byte	PROTOCOL_PONG				= 3;
	public static final int	PROTOCOL_HADER			= 15;
	public static final int	FUTURE_ID_BEGIN_INDEX		= 1;
	public static final int	BINARY_BEGIN_INDEX		= 11;
	public static final int	TEXT_BEGIN_INDEX			= 9;
	public static final int	HASH_BEGIN_INDEX			= 5;

	public IOReadFuture decode(IOSession session, ByteBuffer buffer) throws IOException {
		
		ByteBuf buf = session.getContext().getHeapByteBufferPool().allocate(PROTOCOL_HADER);
		
		buf.read(buffer);
		
		byte _type = buffer.get(0);

		int type = (_type & 0xff) >> 6;

		if (type == PROTOCOL_PING) {
			return new NIOReadFutureImpl(session.getContext()).setPING();
		} else if (type == PROTOCOL_PONG) {
			return new NIOReadFutureImpl(session.getContext()).setPONG();
		}

		return new NIOReadFutureImpl(session, buf);
	}

}
