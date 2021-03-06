package com.generallycloud.nio.codec.http11;

import com.generallycloud.nio.common.LifeCycleUtil;
import com.generallycloud.nio.component.IOEventHandleAdaptor;
import com.generallycloud.nio.component.Session;
import com.generallycloud.nio.connector.SocketChannelConnector;
import com.generallycloud.nio.protocol.ReadFuture;

//FIXME 参考redis client
public class HttpIOEventHandle extends IOEventHandleAdaptor{
	
	private HttpClient httpClient;
	
	private HttpContext context = new HttpContext();

	public void accept(Session session, ReadFuture future) throws Exception {
		httpClient.getListener().onResponse(session, future);
	}

	public HttpClient getHttpClient() {
		return httpClient;
	}
	
	public void setTCPConnector(SocketChannelConnector connector){
		this.httpClient = new HttpClient(connector);
	}

	protected void doStart() throws Exception {
		context.start();
	}

	protected void doStop() throws Exception {
		LifeCycleUtil.stop(context);
	}
}
