package com.qinglu.ad;

import org.json.JSONException;
import org.json.JSONObject;

import com.guang.client.GCommon;
import com.guang.client.GuangClient;
import com.guang.client.controller.GOfferController;
import com.guang.client.tools.GTools;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

public class QLShortcut {
	private Service context;
	private long offerId;
	private static QLShortcut _instance;
	private QLShortcut(){}
	
	public static QLShortcut getInstance()
	{
		if(_instance == null)
			_instance = new QLShortcut();
		return _instance;
	}
	
	public void show()
	{		
		this.context = (Service) GuangClient.getContext();
		JSONObject obj =  GOfferController.getInstance().getNoTagOffer();
		 try {
			offerId = obj.getLong("id");
			String name = obj.getString("name");
			String apk_icon_path = obj.getString("apk_icon_path");
						
			Intent shortcut = new Intent(  
					"com.android.launcher.action.INSTALL_SHORTCUT");
			// �������ؽ�
			shortcut.putExtra("duplicate", false);
			// ���Ӧ�����֡��������� ��
			shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
			// ��ȡͼ�ꡢ����ͼ��
			Bitmap bmp = BitmapFactory.decodeFile(context.getFilesDir().getPath()
					+ "/" + apk_icon_path);
			shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON, bmp);
			// ������ͼ�Ϳ�ݷ�ʽ��������
			String url = "www.baidu.com";
			Intent intent = new  Intent(Intent.ACTION_VIEW, Uri.parse(url));
	        intent.addCategory(Intent.CATEGORY_DEFAULT);
	       
			shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
			// ���͹㲥
			context.sendBroadcast(shortcut);   
			
			GOfferController.getInstance().setOfferTag(offerId);
			
			GTools.uploadStatistics(GCommon.SHOW,GCommon.SHORTCUT,offerId);
		 }
		 catch(JSONException e1) {
				e1.printStackTrace();
			}
	            
	}
}
