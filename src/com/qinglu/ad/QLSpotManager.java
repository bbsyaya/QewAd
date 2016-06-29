package com.qinglu.ad;

import com.qinglu.ad.listener.QLSpotDialogListener;

import android.app.Activity;
import android.content.Context;

public interface QLSpotManager {
	//����activity
	void setActivity(Activity activity);
	//����context
	void updateContext(Context context);
	//Ԥ���ز����������
	 void loadSpotAds();
	//���ò���������չʾ��չʾ��������
	 void setSpotOrientation(int orientation);
	//�������ֶ���Ч��
	 void setAnimationType(int animationType);
	 int getAnimationType();
	//չʾ�������
	 void showSpotAds(Context con);
	 void showSpotAds(Context con,QLSpotDialogListener spotDialogListener);
	 void showSpotAd();
	 void showSpotAd(Object obj,Object rev);
	 //��������
	 void loadSplashSpotAds();
	 //�������򵥵���
	 void showSplashSpotAds(Context context, Class<?> targetActivity);
	//�رղ岥���
	 boolean disMiss();
	//�����˳�
	 void onDestroy();
	 
}
