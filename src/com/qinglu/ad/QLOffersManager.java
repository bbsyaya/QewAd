package com.qinglu.ad;

import com.qinglu.ad.listener.QLOffersWallDialogListener;

import android.app.Activity;

public interface QLOffersManager {
	//积分墙功能初始化
	void onAppLaunch();
	//资源回收
	void onAppExit();
	//展示全屏积分墙
	void showOffersWall();
	//展示悬浮半屏积分墙
	void showOffersWallDialog(Activity activity);
	// 传入对话框关闭监听器
	void showOffersWallDialog(Activity activity, QLOffersWallDialogListener listener);
	//传入对话框宽高像素
	void showOffersWallDialog(Activity activity, int width, int height);
	//传入对话框宽高像素以及关闭监听器
	void showOffersWallDialog(Activity activity, int width, int height, QLOffersWallDialogListener listener);
	//传入对话框宽高所占屏幕比例（0~1）
	void showOffersWallDialog(Activity activity, double scaleOfScreenWidth, double scaleOfScreenHeight);
	//传入对话框宽高所占屏幕比例（0~1）以及关闭监听器
	void showOffersWallDialog(Activity activity, double scaleOfScreenWidth, double scaleOfScreenHeight, QLOffersWallDialogListener listener);
}
