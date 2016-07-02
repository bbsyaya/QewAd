package com.qinglu.ad.impl.youmi;

import com.qinglu.ad.listener.QLSpotDialogListener;

import net.youmi.android.spot.SpotDialogListener;


public class QLSpotDialogListenerYouMi implements SpotDialogListener{

	private QLSpotDialogListener spotDialogListener;
	
	public void setQLSpotDialogListener(QLSpotDialogListener spotDialogListener)
	{
		this.spotDialogListener = spotDialogListener;
	}
	
	@Override
	public void onShowFailed() {
		this.spotDialogListener.onShowFailed();
	}

	@Override
	public void onShowSuccess() {
		this.spotDialogListener.onShowSuccess();
	}

	@Override
	public void onSpotClick(boolean arg0) {
		this.spotDialogListener.onSpotClick(arg0);
	}

	@Override
	public void onSpotClosed() {
		this.spotDialogListener.onSpotClosed();
	}

}
