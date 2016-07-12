package com.qinglu.ad.impl.youmi;

import net.youmi.android.spot.SpotManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.guang.client.GCommon;
import com.qinglu.ad.QLActivity;
import com.qinglu.ad.QLAdController;
import com.qinglu.ad.QLSpotManager;
import com.qinglu.ad.listener.QLSpotDialogListener;

public class QLSpotManagerYouMi implements QLSpotManager{

	private Context context;
	private int animationType;
	
	public QLSpotManagerYouMi(Context context)
	{
		this.context = context;
		loadSpotAds();
	}
	
	@Override
	public void loadSpotAds() {
		SpotManager.getInstance(context).loadSpotAds();
	}

	@Override
	public void setSpotOrientation(int orientation) {
		SpotManager.getInstance(context).setSpotOrientation(orientation);
	}

	@Override
	public void setAnimationType(int animationType) {
		this.animationType = animationType;
		SpotManager.getInstance(context).setAnimationType(animationType);
	}

	@Override
	public void showSpotAds(Context con) {
		SpotManager.getInstance(context).showSpotAds(con);
	}
	
	@Override
	public void showSpotAds(Context con,QLSpotDialogListener spotDialogListener) {
		QLSpotDialogListenerYouMi listener = new QLSpotDialogListenerYouMi();
		listener.setQLSpotDialogListener(spotDialogListener);
		SpotManager.getInstance(context).showSpotAds(con,listener);
	}

	@Override
	public boolean disMiss() {
		 return SpotManager.getInstance(context).disMiss();
	}

	@Override
	public void onDestroy() {
		SpotManager.getInstance(context).onDestroy();
	}

	@Override
	public void loadSplashSpotAds() {
		SpotManager.getInstance(context).loadSpotAds();
	}

	@Override
	public void showSplashSpotAds(Context con, Class<?> targetActivity) {
		SpotManager.getInstance(context).showSplashSpotAds(con, targetActivity);
	}

	@Override
	public int getAnimationType() {
		return this.animationType;
	}

	@Override
	public void setActivity(Activity activity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateContext(Context context) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void showSpotAd(Object obj, Object rev) {
		Intent intent = new Intent(this.context, QLActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra(GCommon.INTENT_TYPE, GCommon.INTENT_PUSH_SPOT_YM);
		this.context.startActivity(intent);
	}

	

}
