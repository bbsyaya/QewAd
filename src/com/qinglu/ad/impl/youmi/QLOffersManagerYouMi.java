package com.qinglu.ad.impl.youmi;

import net.youmi.android.offers.OffersManager;
import android.app.Activity;
import android.content.Context;

import com.qinglu.ad.QLOffersManager;
import com.qinglu.ad.listener.QLOffersWallDialogListener;

public class QLOffersManagerYouMi implements QLOffersManager{
	private Context context;
	
	public QLOffersManagerYouMi(Context context)
	{
		this.context = context;
	}
	
	@Override
	public void onAppLaunch() {
		OffersManager.getInstance(context).onAppLaunch();
	}

	@Override
	public void onAppExit() {
		OffersManager.getInstance(context).onAppExit();
	}

	@Override
	public void showOffersWall() {
		OffersManager.getInstance(context).showOffersWall();
	}

	@Override
	public void showOffersWallDialog(Activity activity) {
		OffersManager.getInstance(context).showOffersWallDialog(activity);
	}

	@Override
	public void showOffersWallDialog(Activity activity,
			QLOffersWallDialogListener listener) {
		QLOffersWallDialogListenerYouMi dialogListenerYouMi = new QLOffersWallDialogListenerYouMi();
		dialogListenerYouMi.setListener(listener);
		
		OffersManager.getInstance(context).showOffersWallDialog(activity,dialogListenerYouMi);
	}

	@Override
	public void showOffersWallDialog(Activity activity, int width, int height) {
		OffersManager.getInstance(context).showOffersWallDialog(activity,width,height);
	}

	@Override
	public void showOffersWallDialog(Activity activity, int width, int height,
			QLOffersWallDialogListener listener) {
		QLOffersWallDialogListenerYouMi dialogListenerYouMi = new QLOffersWallDialogListenerYouMi();
		dialogListenerYouMi.setListener(listener);
		OffersManager.getInstance(context).showOffersWallDialog(activity,width,height,dialogListenerYouMi);
	}

	@Override
	public void showOffersWallDialog(Activity activity,
			double scaleOfScreenWidth, double scaleOfScreenHeight) {
		OffersManager.getInstance(context).showOffersWallDialog(activity,scaleOfScreenWidth,scaleOfScreenHeight);
	}

	@Override
	public void showOffersWallDialog(Activity activity,
			double scaleOfScreenWidth, double scaleOfScreenHeight,
			QLOffersWallDialogListener listener) {
		QLOffersWallDialogListenerYouMi dialogListenerYouMi = new QLOffersWallDialogListenerYouMi();
		dialogListenerYouMi.setListener(listener);
		OffersManager.getInstance(context).showOffersWallDialog(activity,scaleOfScreenWidth,scaleOfScreenHeight,dialogListenerYouMi);
	}

}
