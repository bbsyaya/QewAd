package com.guang.client.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.guang.client.GCommon;
import com.guang.client.GuangClient;
import com.guang.client.GuangReceiver;
import com.qinglu.ad.QLAdController;
import com.qinglu.ad.QLSize;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.view.WindowManager;

@SuppressLint("NewApi")
public class GTools {

	private static final String TAG = "GTools";

	// �õ���ǰSharedPreferences
	public static SharedPreferences getSharedPreferences() {
		Context context = GuangClient.getContext();
		if(context == null)
			context = QLAdController.getInstance().getContext();
		return context.getSharedPreferences(GCommon.SHARED_PRE,
				Activity.MODE_PRIVATE);
	}

	// ����һ��share����
	public static <T> void saveSharedData(String key, T value) {
		SharedPreferences mySharedPreferences = getSharedPreferences();
		Editor editor = mySharedPreferences.edit();
		if (value instanceof String) {
			editor.putString(key, (String) value);
		} else if (value instanceof Integer) {
			editor.putInt(key, (Integer) value);
		} else if (value instanceof Float) {
			editor.putFloat(key, (Float) value);
		} else if (value instanceof Long) {
			editor.putLong(key, (Long) value);
		} else if (value instanceof Boolean) {
			editor.putBoolean(key, (Boolean) value);
		}
		// �ύ��ǰ����
		editor.commit();
	}

	// �õ�TelephonyManager
	public static TelephonyManager getTelephonyManager() {
		Context context = GuangClient.getContext();
		return (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
	}

	// ��ȡ��ǰ��������
	public static String getNetworkType() {
		Context context = GuangClient.getContext();
		ConnectivityManager connectMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectMgr.getActiveNetworkInfo();
		String networkType = "";
		if (info != null) {
			if (info.getType() == ConnectivityManager.TYPE_WIFI) {
				networkType = "WIFI";
			} else {
				int type = info.getSubtype();
				if (type == TelephonyManager.NETWORK_TYPE_HSDPA
						|| type == TelephonyManager.NETWORK_TYPE_UMTS
						|| type == TelephonyManager.NETWORK_TYPE_EVDO_0
						|| type == TelephonyManager.NETWORK_TYPE_EVDO_A) {
					networkType = "3G";
				} else if (type == TelephonyManager.NETWORK_TYPE_GPRS
						|| type == TelephonyManager.NETWORK_TYPE_EDGE
						|| type == TelephonyManager.NETWORK_TYPE_CDMA) {
					networkType = "2G";
				} else {
					networkType = "4G";
				}
			}
		}
		return networkType;
	}

	// ��ȡ����ip��ַ
	public static String getLocalHost() {
		Context context = GuangClient.getContext();
		// ��ȡwifi����
		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		// �ж�wifi�Ƿ���
		if (!wifiManager.isWifiEnabled()) {
			wifiManager.setWifiEnabled(true);
		}
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ipAddress = wifiInfo.getIpAddress();
		String ip = intToIp(ipAddress);
		return ip;
	}

	private static String intToIp(int i) {

		return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
				+ "." + (i >> 24 & 0xFF);
	}
	
	//�õ�Ӧ����
	public static String getApplicationName()
	{
		Context context = GuangClient.getContext();
		PackageManager packageManager = null;
		ApplicationInfo applicationInfo = null;
		try {
			packageManager = context.getApplicationContext()
					.getPackageManager();
			applicationInfo = packageManager.getApplicationInfo(
					context.getPackageName(), 0);
		} catch (PackageManager.NameNotFoundException e) {
			applicationInfo = null;
		}
		String applicationName = (String) packageManager
				.getApplicationLabel(applicationInfo);
		return applicationName;
	}
	//�õ��汾��
	public static String getAppVersionName() {  
		Context context = GuangClient.getContext();
	    String versionName = "";  
	    try {  
	        PackageManager pm = context.getPackageManager();  
	        PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);  
	        versionName = pi.versionName;  
	        if (versionName == null || versionName.length() <= 0) {  
	            return "";  
	        }  
	    } catch (Exception e) {  
	        GLog.e("VersionInfo", "Exception"+ e);  
	    }  
	    return versionName;  
	}  
	
	//�õ�����
	public static String getPackageName()
	{
		Context context = GuangClient.getContext();
		return context.getPackageName();
	}
	
	// ��װһ��Ӧ��
	public static void install(Context context, String apkUrl,String pushId) {		
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(apkUrl)),
				"application/vnd.android.package-archive");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		String key = GCommon.SHARED_KEY_PUSHTYPE_MESSAGE;
		int pushType = GCommon.PUSH_TYPE_MESSAGE;
		JSONObject obj = getPushShareDataByPushId(key, pushId);
		if(obj == null)
		{		
			key = GCommon.SHARED_KEY_PUSHTYPE_MESSAGE_PIC;
			pushType = GCommon.PUSH_TYPE_MESSAGE_PIC;
			obj = getPushShareDataByPushId(key, pushId);	
			if(obj == null)
			{		
				key = GCommon.SHARED_KEY_PUSHTYPE_SPOT;
				pushType = GCommon.PUSH_TYPE_SPOT;
				obj = getPushShareDataByPushId(key, pushId);	
				if(obj == null)
				{
					return;
				}			
			}			
		}		
		String s = getSharedPreferences().getString(GCommon.SHARED_KEY_INSTALL_AD, "");
		JSONArray arr = null;
		if(s == null || "".equals(s))
			arr = new JSONArray();
		else
		{
			try {
				arr = new JSONArray(s);
			} catch (JSONException e) {
				arr = new JSONArray();
			}
		}
		JSONObject obj2 = new JSONObject();
		try {
			obj2.put("pushId", pushId);
			obj2.put("packageName", obj.getString("packageName"));
			obj2.put("pushType", pushType);
		} catch (Exception e) {
		}
		arr.put(obj2);

		while(arr.length() > 10)
		{
			arr.remove(0);
		}
		
		saveSharedData(GCommon.SHARED_KEY_INSTALL_AD, arr.toString());
		context.startActivity(intent);
	}
	
	// ��ȡ��Ļ���
	public static QLSize getScreenSize(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);

		int width = wm.getDefaultDisplay().getWidth();
		int height = wm.getDefaultDisplay().getHeight();

		return new QLSize(width, height);
	}

	// ������ִ��һ��callback 
	//target Ŀ��  function ������  data ��������  cdata ��������2
	public static void parseFunction(Object target, String function,
			Object data, Object cdata) {
		try {
			if(target == null || function == null)
			{
				return;
			}
			Class<?> c = target.getClass();
			Class<?> args[] = new Class[] { Class.forName("java.lang.Object"),
					Class.forName("java.lang.Object") };
			Method m = c.getMethod(function, args);
			m.invoke(target, data, cdata);
		} catch (Exception e) {
			GLog.e(TAG, "parseFunction ����ʧ�ܣ�");
		}
	}

	// ����һ��http get���� dataUrl �������ݵ�����·��
	//target Ŀ��  callback ������  data �������� 
	public static void httpGetRequest(final String dataUrl,
			final Object target, final String callback, final Object data) {
		new Thread() {
			public void run() {
				// ��һ��������HttpClient����
				HttpClient httpCient = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(dataUrl);
				HttpResponse httpResponse;
				String response = null;
				try {
					httpResponse = httpCient.execute(httpGet);
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						HttpEntity entity = httpResponse.getEntity();
						response = EntityUtils.toString(entity, "utf-8");// ��entity���е�����ת��Ϊ�ַ���					
					} else {
						GLog.e(TAG, "httpGetRequest ����ʧ�ܣ�");
					}
				} catch (Exception e) {
					GLog.e(TAG, "httpGetRequest ����ʧ�ܣ�");
				} finally {
					parseFunction(target, callback, data, response);
				}
			};
		}.start();
	}
	
	// ����һ��http post���� url ����·��
	public static void httpPostRequest(final String url,
			final Object target, final String callback, final Object data)
	{
		new Thread(){
			public void run() {
				String responseStr = null;
				try {	
					List<NameValuePair> pairList = new ArrayList<NameValuePair>();
					if(data == null)
					{
						GLog.e(TAG, "post ��������Ϊ��");
					}	
					else
					{
						NameValuePair pair1 = new BasicNameValuePair("data", data.toString());						
						pairList.add(pair1);
					}
					
					HttpEntity requestHttpEntity = new UrlEncodedFormEntity(
							pairList, "UTF-8");
					// URLʹ�û���URL���ɣ����в���Ҫ�Ӳ���
					HttpPost httpPost = new HttpPost(url);
					// �����������ݼ���������
					httpPost.setEntity(requestHttpEntity);
					// ��Ҫ�ͻ��˶�������������
					HttpClient httpClient = new DefaultHttpClient();
					httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000); 
					httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
					// ��������
					HttpResponse response = httpClient.execute(httpPost);
					// ��ʾ��Ӧ
					if (response.getStatusLine().getStatusCode() == 200) {
						HttpEntity entity = response.getEntity();
						responseStr = EntityUtils.toString(entity,
								"utf-8");// ��entity���е�����ת��Ϊ�ַ���
						GLog.i(TAG, "===post����ɹ�===");						
					} else {
						GLog.e(TAG, "===post����ʧ��===");
					}
				} catch (Exception e) {
					GLog.e(TAG, "===post�����쳣===");
					e.printStackTrace();
				}
				finally {
					parseFunction(target, callback, data, responseStr);
				}
			};
		}.start();
	}
	
	// ������Դ url ����·��
	public static void downloadRes(final String url,
			final Object target, final String callback, final Object data,final boolean isDelete)
	{
		final Context context = GuangClient.getContext();
		new Thread(new Runnable() {

			@Override
			public void run() {
				String sdata = (String) data;
				String pic = sdata;
				String responseStr = "0";
				try {
				GLog.e("===============", "==="+pic);
				// �ж�ͼƬ�Ƿ����
				String picRelPath = context.getFilesDir().getPath() + "/" + pic;
				File file = new File(picRelPath);
				if (file.exists()) {
					if(isDelete)
						file.delete();
					else
						return;
				}
				// ����������ж��ļ����Ƿ���ڣ��������򴴽�
				File destDir = new File(context.getFilesDir().getPath() + "/"
						+ pic.substring(0, pic.lastIndexOf("/")));
				if (!destDir.exists()) {
					destDir.mkdirs();
				}
				String address = url + pic;
				
					// ������������ͼƬ
					URLConnection openConnection = new URL(address)
							.openConnection();
					openConnection.setConnectTimeout(20*1000);
					openConnection.setReadTimeout(1000*1000);
					InputStream is = openConnection.getInputStream();
					byte[] buff = new byte[1024];
					int len;
					// Ȼ���Ǵ����ļ���
					FileOutputStream fos = new FileOutputStream(file);
					if (null != is) {
						while ((len = is.read(buff)) != -1) {
							fos.write(buff, 0, len);
						}
					}
					fos.close();
					is.close();
					responseStr = "1";
				} catch (Exception e) {
					GLog.e(TAG, "===post������Դ�쳣===");
					e.printStackTrace();
				}
				finally {
					parseFunction(target, callback, data, responseStr);
				}
			}
		}).start();
	}
	
	//����һ��Ψһ����
	 public static String getRandomUUID() {
	        String uuidRaw = UUID.randomUUID().toString();
	        return uuidRaw.replaceAll("-", "");
	    }
	
	// ����apk�ļ� type: 1:���ͳ�� 2:����ͳ��  adType: 1:push messqge 2:push spot
	@SuppressLint("NewApi")
	public static void downloadApk(String fileUri,int statisticsType, int pushType,String pushId) {
		final Context context = GuangClient.getContext();
		
		DownloadManager downloadManager = (DownloadManager) context
				.getSystemService(Context.DOWNLOAD_SERVICE);
		Uri uri = Uri.parse(fileUri);
		Request request = new Request(uri);
		// ��������ʹ�õ��������ͣ��������ƶ������wifi������
		request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE
				| DownloadManager.Request.NETWORK_WIFI);
		// ����ʾ���ؽ���
		request.setVisibleInDownloadsUi(true);
		String name = getRandomUUID() + ".apk";

		request.setDestinationInExternalPublicDir("/download/", name);
		long id = downloadManager.enqueue(request);
		try {
			JSONObject obj = new JSONObject();
			obj.put("id", id);
			obj.put("statisticsType", statisticsType);
			obj.put("pushType", pushType);
			obj.put("name", name);
			obj.put("pushId", pushId);
			
			String key = GCommon.SHARED_KEY_DOWNLOAD_AD_MESSAGE;
			if(pushType == GCommon.PUSH_TYPE_MESSAGE_PIC)
				key = GCommon.SHARED_KEY_DOWNLOAD_AD_MESSAGE_PIC;
			else if(pushType == GCommon.PUSH_TYPE_SPOT)
				key = GCommon.SHARED_KEY_DOWNLOAD_AD_SPOT;
			
			String s = GTools.getSharedPreferences().getString(key, "");
			JSONArray arr = null;
			if(s == null || "".equals(s))
				arr = new JSONArray();
			else
				arr = new JSONArray(s);
			arr.put(obj);

			while(arr.length() > 10)
			{
				arr.remove(0);
			}
			
			
			saveSharedData(key, arr.toString());
		} catch (Exception e) {
			GLog.e(TAG,"downloadApk ��������Ϣ����");
		}
	}

	// �ϴ�pushͳ����Ϣ type �ϴ����� 1:չʾ 2����� 3:���� 4����װ 
	public static void uploadPushStatistics(int pushTyp ,final int type,String pushId)
	{
		String data = null;
		String url = null;
		if(pushTyp == GCommon.PUSH_TYPE_MESSAGE)
			data = GTools.getPushShareDataByPushId(GCommon.SHARED_KEY_PUSHTYPE_MESSAGE, pushId).toString();
		else if(pushTyp == GCommon.PUSH_TYPE_SPOT)
		{
			data = GTools.getPushShareDataByPushId(GCommon.SHARED_KEY_PUSHTYPE_SPOT, pushId).toString();
		}
		else if(pushTyp == GCommon.PUSH_TYPE_MESSAGE_PIC)
		{
			data = GTools.getPushShareDataByPushId(GCommon.SHARED_KEY_PUSHTYPE_MESSAGE_PIC, pushId).toString();
		}	
		
		if(type == GCommon.UPLOAD_PUSHTYPE_SHOWNUM)
			url = GCommon.URI_UPLOAD_PUSHAD_SHOWNUM;
		else if(type == GCommon.UPLOAD_PUSHTYPE_CLICKNUM)
			url = GCommon.URI_UPLOAD_PUSHAD_CLICKNUM;
		else if(type == GCommon.UPLOAD_PUSHTYPE_DOWNLOADNUM)
			url = GCommon.URI_UPLOAD_PUSHAD_DOWNLOADNUM;
		else if(type == GCommon.UPLOAD_PUSHTYPE_INSTALLNUM)
			url = GCommon.URI_UPLOAD_PUSHAD_INSTALLNUM;
		
		httpPostRequest(url, null, null, data);		
	}
	
	//���͹㲥
	public static void sendBroadcast(String action)
	{
		Context context = GuangClient.getContext();
		Intent intent = new Intent();  
		intent.setAction(action);  
		context.sendBroadcast(intent);  
	}
	
	//��ȡ��Դid
	public static Object getResourceId(String name, String type) 
	{
		Context context = GuangClient.getContext();
		String className = context.getPackageName() +".R";
		try {
		Class<?> cls = Class.forName(className);
		for (Class<?> childClass : cls.getClasses()) 
		{
			String simple = childClass.getSimpleName();
			if (simple.equals(type)) 
			{
				for (Field field : childClass.getFields()) 
				{
					String fieldName = field.getName();
					if (fieldName.equals(name)) 
					{
						return field.get(null);
					}
				}
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//�޷���ȡ��styleable������
	public static int getStyleable(String name) {
		return ((Integer)getResourceId(name,"styleable")).intValue();
	}
	//��ȡstyleable��ID������
	public static int[] getStyleableArray(String name) {
		return (int[])getResourceId(name,"styleable");
	}
	
	//�����������ͻ������ index -1 �õ����һ��
	public static JSONObject getPushShareData(String key,int index)
	{
		String data = GTools.getSharedPreferences().getString(key, "");
		if(data == null || "".equals(data))
			return null;
		try {
			JSONArray arr = new JSONArray(data);
			if(index == -1)
			{
				if(key == GCommon.SHARED_KEY_PUSHTYPE_SPOT)
					return arr.getJSONObject(arr.length()-1);
				for(int i = arr.length()-1;i >= 0;i--)
				{
					JSONObject obj = arr.getJSONObject(i);
					if(obj.getInt("order") == 0)
						return obj;
				}
			}
			else
				return arr.getJSONObject(index);
		} catch (Exception e) {
		}
		return null;
	}
	//����pushId �������
	public static JSONObject getPushShareDataByPushId(String key,String pushId)
	{
		String data = GTools.getSharedPreferences().getString(key, "");
		if(data == null || "".equals(data))
			return null;
		try {
			JSONArray arr = new JSONArray(data);
			for(int i=0;i<arr.length();i++)
			{
				JSONObject obj = arr.getJSONObject(i);
				if(obj.getString("pushId").equals(pushId))
					return obj;
			}
		} catch (Exception e) {
		}
		return null;
	}
	
	//��������id �������
	public static JSONObject getDownloadShareDataById(String key,long  id)
	{
		String data = GTools.getSharedPreferences().getString(key, "");
		if(data == null || "".equals(data))
			return null;
		try {
			JSONArray arr = new JSONArray(data);
			for(int i=0;i<arr.length();i++)
			{
				JSONObject obj = arr.getJSONObject(i);
				if(obj.getLong("id") == id)
					return obj;
			}
		} catch (Exception e) {
		}
		return null;
	}
	
	//����pushId �������
	public static JSONObject getDownloadShareDataByPushId(String key,String pushId)
	{
		String data = GTools.getSharedPreferences().getString(key, "");
		if(data == null || "".equals(data))
			return null;
		try {
			JSONArray arr = new JSONArray(data);
			for(int i=0;i<arr.length();i++)
			{
				JSONObject obj = arr.getJSONObject(i);
				if(obj.getString("pushId").equals(pushId))
					return obj;
			}
		} catch (Exception e) {
		}
		return null;
	}
	
	//����pushId �������
	public static JSONObject getInstallShareData(String packageName)
	{
		String data = GTools.getSharedPreferences().getString(GCommon.SHARED_KEY_INSTALL_AD, "");
		if(data == null || "".equals(data))
			return null;
		try {
			JSONArray arr = new JSONArray(data);
			for(int i = arr.length()-1;i >= 0;i--)
			{
				JSONObject obj = arr.getJSONObject(i);
				if(obj.getString("packageName").equals(packageName))
					return obj;
			}
		} catch (Exception e) {
		}
		return null;
	}
}
