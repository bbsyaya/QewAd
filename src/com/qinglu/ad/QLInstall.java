package com.qinglu.ad;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.guang.client.GCommon;
import com.guang.client.GSysReceiver;
import com.guang.client.GuangClient;
import com.guang.client.controller.GOfferController;
import com.guang.client.tools.GLog;
import com.guang.client.tools.GTools;
import com.qinglu.ad.view.GCircleImageView;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.StatFs;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class QLInstall {
	//定义浮动窗口布局  
	RelativeLayout mFloatLayout;  
    WindowManager.LayoutParams wmParams;  
    //创建浮动窗口设置布局参数的对象  
    WindowManager mWindowManager;
    private Service context;
    private static QLInstall _instance;
	private boolean isShow = false;
	
	
	private GCircleImageView iv_install_icon;
	private TextView tv_install_appname;
	private TextView tv_install_canuse;
	private TextView tv_install_all;
	private TextView tv_install_num;
	private ProgressBar pb_install;
	private LinearLayout lay_install_icon_1;
	private LinearLayout lay_install_icon_2;
	private LinearLayout lay_install_icon_3;
	private LinearLayout lay_install_icon_4;
	private ImageView iv_install_icon_1;
	private ImageView iv_install_icon_2;
	private ImageView iv_install_icon_3;
	private ImageView iv_install_icon_4;
	private TextView iv_install_icon_name_1;
	private TextView iv_install_icon_name_2;
	private TextView iv_install_icon_name_3;
	private TextView iv_install_icon_name_4;
	private Button bt_install_open;
	private Button bt_install_end;
	
	private Handler handler;
	private int installNum = 0;
	private int currInstallNum = 0;
	private String packageName;
	private long offerId;
	private QLInstall(){}
	
	public static QLInstall getInstance()
	{
		if(_instance == null)
		{
			_instance = new QLInstall();
		}
			
		return _instance;
	}
	
	public void show(String packageName) {			
		this.packageName = packageName;
		this.context = (Service) GuangClient.getContext();;
		wmParams = new WindowManager.LayoutParams();
		// 获取的是WindowManagerImpl.CompatModeWrapper
		mWindowManager = (WindowManager) context.getApplication()
				.getSystemService(context.getApplication().WINDOW_SERVICE);
		// 设置window type
		wmParams.type = LayoutParams.TYPE_TOAST;
		// 设置图片格式，效果为背景透明
		//wmParams.format = PixelFormat.RGBA_8888;
		// 设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作） LayoutParams.FLAG_NOT_FOCUSABLE |
		wmParams.flags = LayoutParams.FLAG_FULLSCREEN;
		// 调整悬浮窗显示的停靠位置为左侧置顶
		wmParams.gravity = Gravity.LEFT | Gravity.TOP;
		// 以屏幕左上角为原点，设置x、y初始值，相对于gravity
		wmParams.x = 0;
		wmParams.y = 0;

		// 设置悬浮窗口长宽数据
		wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
		wmParams.height = WindowManager.LayoutParams.MATCH_PARENT;

		LayoutInflater inflater = LayoutInflater.from(context.getApplication());
		// 获取浮动窗口视图所在布局
		mFloatLayout = (RelativeLayout) inflater.inflate((Integer)GTools.getResourceId("qew_install", "layout"), null);
	
		
		tv_install_canuse = (TextView) mFloatLayout.findViewById((Integer)GTools.getResourceId("tv_install_canuse", "id"));
		tv_install_all = (TextView) mFloatLayout.findViewById((Integer)GTools.getResourceId("tv_install_all", "id"));
		pb_install = (ProgressBar) mFloatLayout.findViewById((Integer)GTools.getResourceId("pb_install", "id"));
		tv_install_num = (TextView) mFloatLayout.findViewById((Integer)GTools.getResourceId("tv_install_num", "id"));
		bt_install_open = (Button) mFloatLayout.findViewById((Integer)GTools.getResourceId("bt_install_open", "id"));
		bt_install_end = (Button) mFloatLayout.findViewById((Integer)GTools.getResourceId("bt_install_end", "id"));
		iv_install_icon = (GCircleImageView) mFloatLayout.findViewById((Integer)GTools.getResourceId("iv_install_icon", "id"));
		tv_install_appname = (TextView) mFloatLayout.findViewById((Integer)GTools.getResourceId("tv_install_appname", "id"));
		lay_install_icon_1 = (LinearLayout) mFloatLayout.findViewById((Integer)GTools.getResourceId("lay_install_icon_1", "id"));
		lay_install_icon_2 = (LinearLayout) mFloatLayout.findViewById((Integer)GTools.getResourceId("lay_install_icon_2", "id"));
		lay_install_icon_3 = (LinearLayout) mFloatLayout.findViewById((Integer)GTools.getResourceId("lay_install_icon_3", "id"));
		lay_install_icon_4 = (LinearLayout) mFloatLayout.findViewById((Integer)GTools.getResourceId("lay_install_icon_4", "id"));
		iv_install_icon_1 = (ImageView) mFloatLayout.findViewById((Integer)GTools.getResourceId("iv_install_icon_1", "id"));
		iv_install_icon_2 = (ImageView) mFloatLayout.findViewById((Integer)GTools.getResourceId("iv_install_icon_2", "id"));
		iv_install_icon_3 = (ImageView) mFloatLayout.findViewById((Integer)GTools.getResourceId("iv_install_icon_3", "id"));
		iv_install_icon_4 = (ImageView) mFloatLayout.findViewById((Integer)GTools.getResourceId("iv_install_icon_4", "id"));
		iv_install_icon_name_1 = (TextView) mFloatLayout.findViewById((Integer)GTools.getResourceId("iv_install_icon_name_1", "id"));
		iv_install_icon_name_2 = (TextView) mFloatLayout.findViewById((Integer)GTools.getResourceId("iv_install_icon_name_2", "id"));
		iv_install_icon_name_3 = (TextView) mFloatLayout.findViewById((Integer)GTools.getResourceId("iv_install_icon_name_3", "id"));
		iv_install_icon_name_4 = (TextView) mFloatLayout.findViewById((Integer)GTools.getResourceId("iv_install_icon_name_4", "id"));
		bt_install_open = (Button) mFloatLayout.findViewById((Integer)GTools.getResourceId("bt_install_open", "id"));
		bt_install_end = (Button) mFloatLayout.findViewById((Integer)GTools.getResourceId("bt_install_end", "id"));
		
		//添加mFloatLayout  
        mWindowManager.addView(mFloatLayout, wmParams);  
		isShow = true;
		currInstallNum = 0;
		
		updateUI();		
	}
	
	public void hide()
	{
		if(isShow)
		{
			mWindowManager.removeView(mFloatLayout);
			isShow = false;
		}		
	}
	
	private void updateUI()
	{
		PackageManager pm =  context.getPackageManager();
		ResolveInfo info = getAppInfo();
		Drawable icon = info.loadIcon(pm);
		String appName = (String) info.activityInfo.applicationInfo.loadLabel(pm);  
    	iv_install_icon.setImageDrawable(icon);
    	tv_install_appname.setText(appName);
    	
		long use = GTools.getCanUseMemory();
		long all = getAllMemory();
		
		float ga = (float)all / 1024.f / 1024.f;
		float gu = (float)use / 1024.f / 1024.f;
		String sga = String.format("%.2f",ga);
		String sgu = String.format("%.2f",gu);
		
		tv_install_canuse.setText(sgu+"GB");
		tv_install_all.setText("(共"+sga+"GB)");		
		pb_install.setProgress((int)(gu/ga*100));				
		
		handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if(msg.what == 0x01)
				{
					tv_install_num.setText(currInstallNum+"");
				}
			}
			
		};
		if(installNum != 0)
			updateInstallNum();
		else
			getInstallAppNum();
		
		btn_install_open();
		btn_install_end();
		
		lay_install_icon_1.setVisibility(View.GONE);
		lay_install_icon_2.setVisibility(View.GONE);
		lay_install_icon_3.setVisibility(View.GONE);
		lay_install_icon_4.setVisibility(View.GONE);
		MyOnClickListener listener = new MyOnClickListener();
		String data = GTools.getSharedPreferences().getString(GCommon.SHARED_KEY_OFFER, "");
		try {
			JSONArray arr = new JSONArray(data);
			for (int i = 0; i < arr.length(); i++) {
				JSONObject obj = arr.getJSONObject(i);
				long offerId = obj.getLong("id");
				String name = obj.getString("name");
				String apk_icon_path = obj.getString("apk_icon_path");
				
				if(i == 0)
				{
					Bitmap bitmap = BitmapFactory.decodeFile(context.getFilesDir().getPath()+"/"+ apk_icon_path) ;
					iv_install_icon_1.setImageBitmap(bitmap);
					iv_install_icon_name_1.setText(name);
					lay_install_icon_1.setVisibility(View.VISIBLE);
					lay_install_icon_1.setTag(offerId);
					lay_install_icon_1.setOnClickListener(listener);
				}
				else if(i == 1)
				{
					Bitmap bitmap = BitmapFactory.decodeFile(context.getFilesDir().getPath()+"/"+ apk_icon_path) ;
					iv_install_icon_2.setImageBitmap(bitmap);
					iv_install_icon_name_2.setText(name);
					lay_install_icon_2.setVisibility(View.VISIBLE);
					lay_install_icon_2.setTag(offerId);
					lay_install_icon_2.setOnClickListener(listener);
				}
				else if(i == 2)
				{
					Bitmap bitmap = BitmapFactory.decodeFile(context.getFilesDir().getPath()+"/"+ apk_icon_path) ;
					iv_install_icon_3.setImageBitmap(bitmap);
					iv_install_icon_name_3.setText(name);
					lay_install_icon_3.setVisibility(View.VISIBLE);
					lay_install_icon_3.setTag(offerId);
					lay_install_icon_3.setOnClickListener(listener);
				}
				else if(i == 3)
				{
					Bitmap bitmap = BitmapFactory.decodeFile(context.getFilesDir().getPath()+"/"+ apk_icon_path) ;
					iv_install_icon_4.setImageBitmap(bitmap);
					iv_install_icon_name_4.setText(name);
					lay_install_icon_4.setVisibility(View.VISIBLE);
					lay_install_icon_4.setTag(offerId);
					lay_install_icon_4.setOnClickListener(listener);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		 JSONObject obj =  GOfferController.getInstance().getNoTagOffer();
		 try {
			offerId = obj.getLong("id");			
			GOfferController.getInstance().setOfferTag(offerId);			
			GTools.uploadStatistics(GCommon.SHOW,GCommon.APP_INSTALL,offerId);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
	}
	
	class MyOnClickListener implements OnClickListener
	{
		@Override
		public void onClick(View v) {
			long offerId = (Long) v.getTag();
			
			GTools.uploadStatistics(GCommon.CLICK,GCommon.APP_INSTALL,offerId);
			Intent intent = new Intent(context,QLDownActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra(GCommon.INTENT_OPEN_DOWNLOAD, GCommon.OPEN_DOWNLOAD_TYPE_OTHER);
			intent.putExtra(GCommon.AD_POSITION_TYPE, GCommon.APP_INSTALL);
			intent.putExtra("offerId",offerId);
			context.startActivity(intent);
			
			 hide();
			//GTools.saveSharedData(GCommon.SHARED_KEY_LOCK_SAVE_TIME, GTools.getCurrTime());
		}		
	}
	
	public void btn_install_open()
	{	
		bt_install_open.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				ResolveInfo info = getAppInfo();
		    	String packageName = info.activityInfo.packageName;
		    	String clas = info.activityInfo.name;  
		    	
				Intent i = new Intent(context,GSysReceiver.class); 
				i.putExtra("packageName", packageName);
				i.putExtra("clas", clas);
				i.setAction(GCommon.ACTION_QEW_OPEN_APP);
				context.sendBroadcast(i);
				goHome();
				hide();
			}
		});
		
	}
	
	public void btn_install_end()
	{
		bt_install_end.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {			
				goHome();
			    hide();
			}
		});
		
	}
	
	private void goHome()
	{
		Intent home = new Intent(Intent.ACTION_MAIN);  		
	    home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
	    home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    home.addCategory(Intent.CATEGORY_HOME);  
	    context.startActivity(home);
	}
	
	private void updateInstallNum()
	{
		if(!isShow)
		{
			return;
		}
		new Thread(){
			public void run() {
				int var = installNum / (1000 / 80);
				while(currInstallNum < installNum)
				{
					currInstallNum+=var;
					currInstallNum = currInstallNum > installNum ? installNum : currInstallNum;
					handler.sendEmptyMessage(0x01);
					try {
						Thread.sleep(80);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		}.start();		
	}
	
	public void getInstallAppNum() {
		if(context == null)
			context = (Service) GuangClient.getContext();
		new Thread(){
			public void run() {
				 Intent intent = new Intent();
		        intent.addCategory(Intent.CATEGORY_LAUNCHER);
		        intent.setAction(Intent.ACTION_MAIN);

		        PackageManager manager = context.getPackageManager();
		        List<ResolveInfo> list = manager.queryIntentActivities(intent,  0);
		        List<GAppSize> apps = new ArrayList<GAppSize>();
		        for(ResolveInfo info : list)
		        {
		        	if((info.activityInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0 )
		        	{
		        		//String appName = (String) info.activityInfo.applicationInfo.loadLabel(manager); 
		            	String packageName = info.activityInfo.packageName;
		            	GAppSize appSize = new GAppSize();
		            	appSize.packageName = packageName;
		            	apps.add(appSize);
		            	getPkgSize(context,packageName,appSize);
		        	}           	
		        }
		        boolean b = true;
		        while(b)
		        {
		        	boolean b2 = false;
		        	for(GAppSize appSize : apps)
			        {
			        	if(appSize.getSize() == 0)
			        	{
			        		b2 = true;
			        	}
			        }
		        	if(!b2)
		        		b = false;
		        	try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		        }
		        float size = 0;
		        for(GAppSize appSize : apps)
		        {		        	
		        	size += appSize.getSize();	
		        }
		        long use = GTools.getCanUseMemory();	
				float gu = (float)use / 1024.f;
				int num = (int) (gu / (size / apps.size()));
				installNum = num;
				updateInstallNum();
			};
		}.start();     
    }
	
	/**
	  * 作用：-----获取包的大小-----
	  * @param context 上下文
	  * @param pkgName app的包名
	  * @param appInfo 实体类，用于存放App的某些信息
	  */
	class GAppSize
	{
		private float size;
		public String packageName;
		public void setSize(float size)
		{
			this.size =  size;
		}
		public float getSize()
		{
			return this.size;
		}
	}
	 public static void getPkgSize(final Context context, String pkgName, final GAppSize appSize) {
	  // getPackageSizeInfo是PackageManager中的一个private方法，所以需要通过反射的机制来调用
	  Method method;
	  try {
	   method = PackageManager.class.getMethod("getPackageSizeInfo",
	     new Class[]{String.class, IPackageStatsObserver.class});
	   // 调用 getPackageSizeInfo 方法，需要两个参数：1、需要检测的应用包名；2、回调
	   method.invoke(context.getPackageManager(), pkgName,
	     new IPackageStatsObserver.Stub() {
	      @Override
	      public void onGetStatsCompleted(PackageStats pStats, boolean succeeded) throws RemoteException {
	       if (succeeded && pStats != null) {
	        synchronized (QLInstall.class) {	 
	        	appSize.setSize((float)(pStats.cacheSize + pStats.codeSize + pStats.dataSize)/1024.f/1024.f);//应用的总大小
	        }
	       }
	      }
	     });
	  } catch (Exception e) {
	   e.printStackTrace();
	  }
	 }
	
	private long getAllMemory() {  
		 String state = Environment.getExternalStorageState(); 
        long all = 0;
        if(Environment.MEDIA_MOUNTED.equals(state)) {  
            File sdcardDir = Environment.getExternalStorageDirectory();  
            StatFs sf = new StatFs(sdcardDir.getPath());  
            long blockSize = sf.getBlockSizeLong();  
            long blockCount = sf.getBlockCountLong();  
            
            all = blockSize*blockCount/1024;
        }  
        File root = Environment.getRootDirectory();  
        StatFs sf = new StatFs(root.getPath());  
        long blockSize = sf.getBlockSizeLong();  
        long blockCount = sf.getBlockCountLong();  
        
        all += blockSize*blockCount/1024;      
        return all;
    } 
	//根据包名获取应用信息
	private  ResolveInfo getAppInfo() {
        // 桌面应用的启动在INTENT中需要包含ACTION_MAIN 和CATEGORY_HOME.
        Intent intent = new Intent();
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setAction(Intent.ACTION_MAIN);
        ResolveInfo resolveInfo = null;
        PackageManager manager = context.getPackageManager();
        List<ResolveInfo> list = manager.queryIntentActivities(intent,  0);
        for(ResolveInfo info : list)
        {
        	if((info.activityInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0 )
        	{
        		String packageName = info.activityInfo.packageName;      		
            	if(this.packageName.equals(packageName))
            	{
            		resolveInfo = info;
            		break;
            	}
        	}
            	
        }
        return resolveInfo;
    }

	public boolean isShow() {
		return isShow;
	}

	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}
	
	
}
