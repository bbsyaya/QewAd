package com.guang.client;



import org.json.JSONException;
import org.json.JSONObject;

import com.guang.client.controller.GOfferController;
import com.guang.client.tools.GLog;
import com.guang.client.tools.GTools;
import com.qinglu.ad.QLAdController;
import com.qinglu.ad.QLBatteryLock;
import com.qinglu.ad.QLInstall;
import com.qinglu.ad.QLNotifier;
import com.qinglu.ad.QLUnInstall;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;
@SuppressLint("NewApi")
public final class GSysReceiver extends BroadcastReceiver {

	
	public GSysReceiver() {
		
	}


	@SuppressLint("NewApi")
	@Override
	public void onReceive(Context context, Intent intent) {		
		String action = intent.getAction();
		if(GuangClient.getContext() == null)
			return;
		GLog.e("GSysReceiver", "onReceive()..."+action);
		if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
			downloadComplete(context,intent);				
		} 
		else if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
			installComplete(intent);
			if(		GSysService.getInstance().isWifi() 
					&& GSysService.getInstance().isRuning()
					&& GSysService.getInstance().isAdPosition(GCommon.APP_INSTALL)
					&& GSysService.getInstance().isShowInstallAd())
				install(intent);
			//ÿ�ΰ�װ��ȡ����app��Ϣ
			QLUnInstall.getInstance().getAppInfo(true);
		} 	
		else if("android.intent.action.PACKAGE_REMOVED".equals(action))
		{
			if(		GSysService.getInstance().isWifi() 
					&& GSysService.getInstance().isRuning()
					&& GSysService.getInstance().isAdPosition(GCommon.APP_UNINSTALL)
					&& GSysService.getInstance().isShowUnInstallAd())
				uninstall(intent);
		}
		else if (GCommon.ACTION_QEW_APP_STARTUP.equals(action))
		{								
			//appStartUp();
		}			
		else if(GCommon.ACTION_QEW_APP_ACTIVE.equals(action))
		{
			appActive(intent);
		}	
		//����
		else if(Intent.ACTION_SCREEN_OFF.equals(action))
		{
			GSysService.getInstance().setPresent(false);
		}
		//����
		else if(Intent.ACTION_USER_PRESENT.equals(action))
		{
			GSysService.getInstance().setPresent(true);
		}
		//����
		else if(Intent.ACTION_SCREEN_ON.equals(action))
		{
			GSysService.getInstance().setPresent(true);
		}
		//���
		else if(Intent.ACTION_BATTERY_CHANGED.equals(action))
		{			
			batteryLock(intent);	
		}
		else if (GCommon.ACTION_QEW_OPEN_APP.equals(action))
		{								
			openApp(context,intent);
		}	
	}

	
	//�������
	private void downloadComplete(Context context, Intent intent)
	{
		long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);			
		try {
			JSONObject obj = GTools.getDownloadApkShareDataById(id);
			if(obj == null)
				return;
			String name = obj.getString("name");
			long offerId = obj.getLong("offerId");
			int adPositionType = obj.getInt("adPositionType");
			int intentType = obj.getInt("intentType");
			// �ϴ�ͳ����Ϣ
			if(GCommon.OPEN_DOWNLOAD_TYPE_SELF == intentType)
				GTools.uploadStatistics(GCommon.DOUBLE_DOWNLOAD_SUCCESS,adPositionType,offerId);
			else
				GTools.uploadStatistics(GCommon.DOWNLOAD_SUCCESS,adPositionType,offerId);
			
			GTools.install(context, name,adPositionType,offerId,intentType);				
			
		} catch (Exception e) {				
		}			
	}
	
	//��װ���
	private void installComplete(Intent intent)
	{
		String packageName = intent.getDataString();
		packageName = packageName.split(":")[1];
					
		try {
			JSONObject obj = GTools.getInstallShareDataByPackageName(packageName);
			if(obj == null)				
				return;
			
			int adPositionType = obj.getInt("adPositionType");
			long offerId = obj.getLong("offerId");
			int intentType = obj.getInt("intentType");
			// �ϴ�ͳ����Ϣ
			if(GCommon.OPEN_DOWNLOAD_TYPE_SELF == intentType)
				GTools.uploadStatistics(GCommon.DOUBLE_INSTALL,adPositionType,offerId);
			else 
				GTools.uploadStatistics(GCommon.INSTALL,adPositionType,offerId);
			//��ʼ�жϼ���
			GSysService.getInstance().updateActive(packageName);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	//��װ
	private void install(Intent intent)
	{
		String packageName = intent.getDataString();
		packageName = packageName.split(":")[1];
		
		if(!QLInstall.getInstance().isShow())
		{
			QLInstall.getInstance().show(packageName);
		}
	}
	
	//ж��
	private void uninstall(Intent intent)
	{
		String packageName = intent.getDataString();
		packageName = packageName.split(":")[1];
		if(!QLUnInstall.getInstance().isShow())
		{
			QLUnInstall.getInstance().show(packageName);
		}
	}
		
	//app ����
	private void appActive(Intent intent)
	{
		String packageName = intent.getStringExtra("activePackageName");
		JSONObject obj = GTools.getInstallShareDataByPackageName(packageName);
		if(obj == null)				
			return;
	
		try {
			int adPositionType = obj.getInt("adPositionType");
			long offerId = obj.getLong("offerId");
			int intentType = obj.getInt("intentType");
			
			// �ϴ�ͳ����Ϣ
			if(GCommon.OPEN_DOWNLOAD_TYPE_SELF == intentType)
				GTools.uploadStatistics(GCommon.DOUBLE_ACTIVATE,adPositionType,offerId);
			else
				GTools.uploadStatistics(GCommon.ACTIVATE,adPositionType,offerId);
		} catch (JSONException e) {
			e.printStackTrace();
		}			
	}
	
	//�����
	private void batteryLock(Intent intent)
	{
		int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
		//��ȡ��ǰ����    
        int mBatteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);    
           //�������̶ܿ�    
        //int mBatteryScale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);    
        int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = false;
        if(chargePlug == BatteryManager.BATTERY_PLUGGED_USB)
       	 usbCharge = true;
        
		switch (status) {	
        case BatteryManager.BATTERY_STATUS_CHARGING:
            // ���ڳ��   
        	GSysService.getInstance().startLockThread();
        	QLBatteryLock.getInstance().setFirst(false);
            QLBatteryLock.getInstance().updateBattery(mBatteryLevel, usbCharge);
            break;       
        case BatteryManager.BATTERY_STATUS_FULL:
            // ����           	  	
             QLBatteryLock.getInstance().updateBattery(mBatteryLevel, usbCharge);
            break;
        case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
            // û�г��
        	QLBatteryLock.getInstance().hide();
        	QLBatteryLock.getInstance().setFirst(true);
            break;
        case BatteryManager.BATTERY_STATUS_UNKNOWN:
            // δ֪״̬
        	QLBatteryLock.getInstance().hide();
        	QLBatteryLock.getInstance().setFirst(true);
            break;
        default:
        	QLBatteryLock.getInstance().hide();
        	QLBatteryLock.getInstance().setFirst(true);
            break;
        }
	}
	
	//
	private void openApp(final Context context,final Intent intent)
	{
		new Thread(){
			public void run() {
				try {
					Thread.sleep(1000);
					
					String packageName = intent.getStringExtra("packageName");
					String clas = intent.getStringExtra("clas");
					
					Intent i = new Intent();  
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					i.setClassName(packageName,clas);  
					context.startActivity(i);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		}.start();
		  
	}
}
