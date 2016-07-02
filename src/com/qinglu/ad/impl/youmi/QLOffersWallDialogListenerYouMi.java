package com.qinglu.ad.impl.youmi;

import com.qinglu.ad.listener.QLOffersWallDialogListener;

import net.youmi.android.offers.OffersWallDialogListener;

public class QLOffersWallDialogListenerYouMi implements OffersWallDialogListener{
	private QLOffersWallDialogListener listener;
	
	public void setListener(QLOffersWallDialogListener listener) {
		this.listener = listener;
	}

	@Override
	public void onDialogClose() {
		listener.onDialogClose();
	}

}
