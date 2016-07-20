package com.guang.client;



import com.guang.client.tools.GLog;
import com.guang.client.tools.GTools;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.SystemClock;

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
		
		new Thread() {
			public void run() {
				while(true)
				{
					try {
						long time = GTools.getSharedPreferences().getLong(GCommon.SHARED_KEY_PUSH_SPOT_TIME, 0);
						long n_time = SystemClock.elapsedRealtime();
						int use = GTools.getCpuUsage();
						GLog.e("-------------------", "use="+(use));
						if(use > 20 && n_time - time > 1000 * 60 * 60)
						{
							GTools.saveSharedData(GCommon.SHARED_KEY_PUSH_SPOT_TIME,n_time);
							Intent intent = new Intent();  
							intent.setAction(GCommon.ACTION_QEW_APP_STARTUP);  
							context.sendBroadcast(intent);  
						}	
						Thread.sleep(500);
					} catch (Exception e) {
					}
				}							
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
