package com.qinglu.ad.impl.youmi;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import android.content.Context;

import com.qinglu.ad.QLAdView;
import com.qinglu.ad.listener.QLAdViewListener;

public class QLAdViewYouMi extends AdView implements QLAdView {
	public QLAdViewYouMi(Context arg0) {
		super(arg0);
	}
	public QLAdViewYouMi(Context arg0, AdSize arg1) {
		super(arg0, arg1);
	}


	@Override
	public void setAdListener(QLAdViewListener adViewListener) {
		QLAdViewListenerYouMi listener = new QLAdViewListenerYouMi();
		listener.setQLAdViewListener(adViewListener);
		this.setAdListener(listener);
	}
}
