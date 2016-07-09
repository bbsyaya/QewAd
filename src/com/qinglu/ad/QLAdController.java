package com.qinglu.ad;

import com.guang.client.ClientService;
import com.guang.client.GCommon;
import com.guang.client.GuangClient;
import com.guang.client.tools.GLog;
import com.guang.client.tools.GTools;
import com.qinglu.ad.impl.qinglu.QLAdManagerQingLu;
import com.qinglu.ad.impl.qinglu.QLSpotManagerQingLu;
import com.qinglu.ad.impl.youmi.QLAdManagerYouMi;
import com.qinglu.ad.impl.youmi.QLSpotManagerYouMi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;


public class QLAdController {
	private static QLAdController controller;
	public static QLSpotManager spotManager;
	public static QLAdManager adManager;
	private Context context;
	
	private QLAdController()
	{

	}
	
	public static QLAdController getInstance()
	{
		if(controller == null)
		{
			controller = new QLAdController();					
		}			
		return controller;
	}
	
	public static QLAdManager getAdManager()
	{
		if(adManager == null)
		{
			if(GCommon.CurrPlatform == GCommon.QingLu)
			{
				adManager = new QLAdManagerQingLu(GuangClient.getContext());
			}
			else if(GCommon.CurrPlatform == GCommon.YouMi)
			{
				adManager = new QLAdManagerYouMi(GuangClient.getContext());
			}
			adManager.init(GTools.getSharedPreferences().getBoolean(GCommon.SHARED_KEY_TESTMODEL, false));
		}
			
		return adManager;
	}
	
	public static QLSpotManager getSpotManager()
	{
		if(spotManager == null)
		{
			if(GCommon.CurrPlatform == GCommon.QingLu)
			{
				spotManager = new QLSpotManagerQingLu(GuangClient.getContext());
			}
			else if(GCommon.CurrPlatform == GCommon.YouMi)
			{
				spotManager = new QLSpotManagerYouMi(GuangClient.getContext());
			}
		}
			
		spotManager.updateContext(GuangClient.getContext());
		return spotManager;
	}
		
	public void init(Context context,String appId,String appSecret,boolean isTestModel)
	{
		this.context = context;
		GTools.saveSharedData(GCommon.SHARED_KEY_APP_ID,appId);
		GTools.saveSharedData(GCommon.SHARED_KEY_APP_SECRET,appSecret);		
		GTools.saveSharedData(GCommon.SHARED_KEY_TESTMODEL,isTestModel);
		
		startService();
	}
		
	@SuppressLint("NewApi")
	public void startService()
	{
		Intent intent = new Intent(this.context, ClientService.class);
		this.context.startService(intent);
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
	
	
	public void revAdPlatfrom(Object ob,Object rev)
	{
		int platform = Integer.parseInt(rev.toString());
		if(GCommon.CurrPlatform != platform)
		{
			GCommon.CurrPlatform = platform;
			spotManager = null;
			adManager = null;
			getAdManager();
			getSpotManager();
		}
	}
}
