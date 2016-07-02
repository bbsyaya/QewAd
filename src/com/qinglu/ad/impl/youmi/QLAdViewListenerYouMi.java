package com.qinglu.ad.impl.youmi;

import com.qinglu.ad.listener.QLAdViewListener;

import net.youmi.android.banner.AdView;
import net.youmi.android.banner.AdViewListener;


public class QLAdViewListenerYouMi implements AdViewListener{

	private QLAdViewListener adViewListener;
	
	public void setQLAdViewListener(QLAdViewListener adViewListener)
	{
		this.adViewListener = adViewListener;
	}

	@Override
	public void onFailedToReceivedAd(AdView arg0) {
		this.adViewListener.onFailedToReceivedAd(arg0);
	}

	@Override
	public void onReceivedAd(AdView arg0) {
		this.adViewListener.onReceivedAd(arg0);
	}

	@Override
	public void onSwitchedAd(AdView arg0) {
		this.adViewListener.onSwitchedAd(arg0);
	}
	

}
