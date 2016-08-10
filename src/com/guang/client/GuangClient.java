package com.guang.client;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.guang.client.handler.GCoreHandler;

import android.content.Context;
import android.util.Log;

public class GuangClient {
	private static final String LOG = "GuangClient";
	private final static String HOST = GCommon.SERVER_IP;
	private final static int PORT = 9123;
	private static IoConnector connector;
	private static IoSession session;
	private static Context context;
	private static GuangClient _instance;

	private GuangClient() {
		connector = new NioSocketConnector();
	}
	
	public static GuangClient getInstance()
	{
		if(_instance == null)
			_instance = new GuangClient();
		return _instance;
	}

	public static IoConnector getConnector() {
		if (connector == null) {
			Log.e(LOG, "�ͻ��˻�δ����...");
		}
		return connector;
	}
	
	public static IoSession getSession()
	{
		return session;
	}
	
	public static Context getContext()
	{
		return context;
	}
	
	public void setContext(Context context)
	{
		this.context = context;
	}

	public void start() {
		// �������ӳ�ʱʱ��
		connector.setConnectTimeoutMillis(30000);
		// ��ӹ�����
		connector.getFilterChain().addLast(
				"codec",
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset
						.forName("UTF-8"))));
		connector.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, 60 );
		// ���ҵ���߼���������
		connector.setHandler(new GCoreHandler());
		try {
			ConnectFuture future = connector.connect(new InetSocketAddress(
					HOST, PORT));// ��������
			future.awaitUninterruptibly();// �ȴ����Ӵ������
			session = future.getSession();// ���session
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(LOG, "�ͻ�������ʧ�ܣ�"+e.getMessage());
			reConnec();
		}
	}
	
	public static void reConnec()
	{
		new Thread(){		
			public void run() {
				while(session == null || (session != null && !session.isConnected()))
				{
					try {
						ConnectFuture future = connector.connect(new InetSocketAddress(
								HOST, PORT));// ��������
						future.awaitUninterruptibly();// �ȴ����Ӵ������
						session = future.getSession();// ���session						
					} catch (Exception e) {
						Log.e(LOG, "�ͻ�������ʧ�ܣ�"+e.getMessage());
					}	
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};			
		}.start();
		
	}
}
