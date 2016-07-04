package com.qinglu.ad.impl.youmi;


import net.youmi.android.AdManager;
import android.content.Context;

import com.qinglu.ad.QLAdManager;

public class QLAdManagerYouMi implements QLAdManager{
	
	private Context context;
	
	public QLAdManagerYouMi(Context context)
	{
		this.context = context;
	}

	@Override
	public void init(boolean isTestModel) {
					
		String appId = "1dc4b3cd67962f46";
		String appSecret = "e812c092f01b11d5";
		
		AdManager.getInstance(context).init(appId, appSecret, isTestModel);
		
	}


}
