package com.generallycloud.test.nio.common;

import com.generallycloud.nio.codec.http11.future.ClientHttpReadFuture;
import com.generallycloud.nio.codec.http11.future.HttpReadFuture;
import com.generallycloud.nio.codec.nio.future.NIOReadFuture;
import com.generallycloud.nio.codec.nio.future.NIOReadFutureImpl;
import com.generallycloud.nio.component.IOEventHandle;
import com.generallycloud.nio.component.Session;

public class ReadFutureFactory {

	public static NIOReadFuture create(Session session, NIOReadFuture future) {
		NIOReadFuture readFuture = (NIOReadFuture) future;
		return create(session, readFuture.getFutureID(), readFuture.getFutureName(), readFuture.getIOEventHandle());
	}

	public static NIOReadFuture create(Session session, Integer futureID, String serviceName,
			IOEventHandle ioEventHandle) {

		NIOReadFutureImpl textReadFuture = new NIOReadFutureImpl(session.getContext(),futureID, serviceName);

		textReadFuture.setIOEventHandle(ioEventHandle);

		return textReadFuture;
	}

	public static NIOReadFuture create(Session session, Integer futureID, String serviceName) {

		return create(session, futureID, serviceName, session.getContext().getIOEventHandleAdaptor());
	}

	public static NIOReadFuture create(Session session, String serviceName, IOEventHandle ioEventHandle) {

		return create(session, 0, serviceName, ioEventHandle);
	}

	public static HttpReadFuture createHttpReadFuture(Session session, String url) {
		return new ClientHttpReadFuture(session.getContext(),url, "GET");
	}

	public static NIOReadFuture create(Session session, String serviceName) {

		return create(session, 0, serviceName, session.getContext().getIOEventHandleAdaptor());
	}
}
