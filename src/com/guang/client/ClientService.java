package com.guang.client;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.PowerManager;

public class ClientService extends Service {
	private Context context;
	private PowerManager pm;
	private PowerManager.WakeLock wakeLock;
	private GuangReceiver receiver;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		context = this;
		new Thread() {
			public void run() {
				GuangClient client = new GuangClient();
				client.setContext(context);
				client.start();
			};
		}.start();
		registerListener();
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// ����PowerManager����
		pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		// ����cpuһֱ���У�������Ļ�Ƿ����
		wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
				"CPUKeepRunning");
		wakeLock.acquire();
		super.onStart(intent, startId);
	}

	@Override
	public void onDestroy() {
		wakeLock.release();
		unregisterListener();
		super.onDestroy();
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		return super.onUnbind(intent);
	}

	private void registerListener() {
		receiver = new GuangReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(GCommon.ACTION_QEW_APP_STARTUP);
        this.registerReceiver(receiver, filter);
    }
 
    private void unregisterListener() {
        this.unregisterReceiver(receiver);
    }
}
