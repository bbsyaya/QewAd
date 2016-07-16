package com.qinglu.ad;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.guang.client.ClientService;
import com.guang.client.GCommon;
import com.guang.client.GuangClient;
import com.guang.client.tools.GTools;
import com.xugu.qewad.MainActivity;
import com.xugu.qewad.R;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author by С��
 *	app���ؽ���
 */
public class QLDownActivity extends Activity {
	private static final String APP_SC_PUSHID = "app_name";
	private static final String PUSH_TYPE = "push_type";
	private ListView lv;  
	 private List<ImageView> imageViewList;  
	 private LinearLayout ll;  

	
	RelativeLayout relativeLayout;
	static Context context;

	public static String JSON_ARR_POSITION  = "json_arr_position";
	QLHorizontalListView horizontalListView;
//	QLHorizontalListView horizontalListViewPic;
	private ArrayList<String> appName ;
	private ArrayList<String> image;
	private QLExpandableTextView expandableTextView; 
	//�ղ�
	TextView textCollect; 
	ScrollView scrollView;
	//����
	Button buttonDown;
	TextView textFengXiang;
	ImageView imageViewTop;
	TextView textAppName;
	TextView textDownNum;
	TextView textAppSize;
	TextView textAppVersion;
	TextView textAppType;
	TextView textViewFZ;
	TextView textAppUpdata;
	TextView textViewXjj;
	ArrayList<String> picList = new ArrayList<String>();
	ArrayList<String> pushIds = new ArrayList<String>();
	//����ˢ��
	ImageView imageViewUpdata;
	
	 QLHorizontalListViewAdapter adapter;
	//���� �ײ�����ʾ
	JSONObject showJsonObj;
	String push_type = GCommon.INTENT_PUSH_MESSAGE;
//	private String pushId;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this; 
		//��������	
		String appId = GTools.getSharedPreferences().getString(GCommon.SHARED_KEY_APP_ID, ""); 
		String appSecret = GTools.getSharedPreferences().getString(GCommon.SHARED_KEY_APP_SECRET, "");
		
		QLAdController.getInstance().init(this,appId,appSecret, true);
		
		setContentView((Integer)mGetResourceId("qew_down_main", "layout",context));
		horizontalListView = (QLHorizontalListView) findViewById((Integer) mGetResourceId("horizontalListView", "id",context));
		textCollect = (TextView) findViewById((Integer) mGetResourceId("textView_shouc", "id",context));
		expandableTextView = (QLExpandableTextView) findViewById((Integer) mGetResourceId("textView_app_js", "id",context));
		buttonDown = (Button) findViewById((Integer) mGetResourceId("button_Down", "id",context));
		textFengXiang = (TextView) findViewById((Integer) mGetResourceId("textView_fenxiang", "id",context));
		imageViewTop = (ImageView) findViewById((Integer) mGetResourceId("imageView_app_imager", "id",context));
		textAppName = (TextView) findViewById((Integer) mGetResourceId("textView_app_name", "id",context));
		textDownNum = (TextView) findViewById((Integer) mGetResourceId("textView_down_num", "id",context));
		relativeLayout = (RelativeLayout) findViewById((Integer) mGetResourceId("relativa_list", "id",context));
		textAppType = (TextView) findViewById((Integer) mGetResourceId("textView_app_type","id",context));
		textAppSize = (TextView) findViewById((Integer) mGetResourceId("textView_app_size", "id",context));
		textViewFZ = (TextView) findViewById((Integer) mGetResourceId("textView_fzgs","id",context));
		textAppVersion = (TextView) findViewById((Integer) mGetResourceId("textView_version_num", "id",context));
		textAppUpdata = (TextView) findViewById((Integer) mGetResourceId("textView_app_updata", "id",context));
		imageViewUpdata  = (ImageView) findViewById((Integer) mGetResourceId("imageView_app_updata", "id",context));
		textViewXjj = (TextView) findViewById((Integer) mGetResourceId("textView_xjj", "id",context));
		scrollView = (ScrollView) findViewById((Integer) mGetResourceId("scrollView1", "id",context));
		ll  = (LinearLayout) findViewById((Integer) mGetResourceId("ll", "id",context));
		
		//��������
		initData();
		//���������
		adapter =new QLHorizontalListViewAdapter(context, appName, image,showJsonObj);
		horizontalListView.setAdapter(adapter);
		
		
		//����λ��
		setViewXY();
	
		setAppContext();
		//�ղ�
		doCollect();
		
		/**����**/
		Intent intent = getIntent();
		
		String pId = intent.getStringExtra(JSON_ARR_POSITION);
		if (pId != null && !"".equals(pId))
		{
			int type = GCommon.PUSH_TYPE_MESSAGE;
			if(GCommon.INTENT_PUSH_MESSAGE_PIC.equals(push_type)){
				type = GCommon.PUSH_TYPE_MESSAGE_PIC;
			}
//			//�ϴ�ͳ����Ϣ
			GTools.uploadPushStatistics(type,GCommon.UPLOAD_PUSHTYPE_SHOWNUM,pId);
			
		}
	}
	/**
	 * �ղأ�����QQ��΢�ţ�����Ȧ��
	 */
	long t1=0;
	private void doCollect() {
		// TODO Auto-generated method stub
		textFengXiang.setText("����");
		textFengXiang.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(context,"�˹�����δ���ţ������ڴ�", Toast.LENGTH_SHORT).show();
			}
		});
		
		
		//�ղء�����������
		textCollect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				try {
			    Intent shortcut = new Intent(  
			    "com.android.launcher.action.INSTALL_SHORTCUT"); 
			    // �������ؽ�  
			    shortcut.putExtra("duplicate", false);  
			    // ���Ӧ�����֡���������  ����ȡӦ��pushId
			    String p = obj.getString("pushId");
			    String name = showJsonObj.getString("name");
			    shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME,name);
			    // ��ȡͼ�ꡢ����ͼ��  
			    Bitmap bmp = BitmapFactory.decodeFile(context.getFilesDir().getPath()+"/"+showJsonObj.getString("icon_path"));
			    shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON, bmp);
			    // ������ͼ�Ϳ�ݷ�ʽ��������  
			    Intent launcherIntent = new Intent();
		        launcherIntent.setAction(Intent.ACTION_MAIN);
		        //��ͼЯ������
		        launcherIntent.putExtra(APP_SC_PUSHID, p);
		        launcherIntent.putExtra(PUSH_TYPE, push_type);
		        launcherIntent.setClass(context, QLDownActivity.class);
		        launcherIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, launcherIntent);
                // ���͹㲥
                sendBroadcast(shortcut);                
                Toast.makeText(context,"�ղسɹ���", Toast.LENGTH_SHORT).show();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}});
	
		//����Ӧ��
		buttonDown.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String pushId = null;
				try {
					pushId = obj.getString("pushId");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent intent = new Intent(context,QLActivity.class);
				intent.putExtra(GCommon.INTENT_TYPE,push_type);
				intent.putExtra("pushId", pushId);
				startActivity(intent);
				
			}
		});
	
		//ˢ���Ƽ�Ӧ�á���������
	     imageViewUpdata.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//ˢ�³���
				ArrayList<String> aName = new ArrayList<String>();
				ArrayList<String> aImage = new ArrayList<String>();
				ArrayList<JSONObject> listjson = new ArrayList<JSONObject>();
				ArrayList<String> pd = new ArrayList<String>();
				//��¼����ʱ��
			     Toast.makeText(context, "ˢ�³ɹ�", Toast.LENGTH_SHORT).show();
			     if (appName.size()>0) {
			      //����˳�򣬴ﵽˢ�³����Ŀ��
				for(int i = appName.size()-1;i>=0;i--){
					aName.add(appName.get(i));
					aImage.add(image.get(i));
					listjson.add(listJson.get(i));
					pd.add(pushIds.get(i));
				}
						listJson=listjson;
						appName = aName;
						image = aImage;
						pushIds	= pd;
				}
				horizontalListView.setAdapter(
				new QLHorizontalListViewAdapter(context, appName, image,showJsonObj));
			  }
		});
	}
	
	/**
	 * 设置�?���??�?????
	 */
	private void setAppContext() {
		// TODO Auto-generated method stub
	}

	/**
	 * 	��ȡ��Ļ��ߣ�����������������λ��
	 */
	private void setViewXY() {
		// TODO Auto-generated method stub
		switch (appName.size()) {
		case 0:
			break;
		case 1:
			setPosistion();
			break;
		case 2:
			setPosistionTwo();
			break;
		case 3:
			setPosistionThree();
			break;
		default:
			break;
		}
		
	}
	/**
	 * ��ȡ��Ļ��ߣ�����������������λ��
	 */
	private void setPosistionThree() {
		// TODO Auto-generated method stub
				WindowManager wm = (WindowManager) getContext()
						.getSystemService(Context.WINDOW_SERVICE);
				int width = wm.getDefaultDisplay().getWidth();
				LayoutParams layout =  horizontalListView.getLayoutParams();
				layout.width = width/5*3 + width/60*2*5 ;
				//������ͼλ��
				horizontalListView.setX(width/2 - layout.width/2);
	}


	/**
	 * ��ȡ��Ļ��ߣ�����������������λ��
	 */
	private void setPosistionTwo() {
		// TODO Auto-generated method stub
		WindowManager wm = (WindowManager) getContext()
				.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		int height = wm.getDefaultDisplay().getHeight();	
		LayoutParams layout =  horizontalListView.getLayoutParams();
		layout.width = width/5*2 + width/60*2*3 ;
		//������ͼλ��
		horizontalListView.setX(width/2 - layout.width/2);
	}


	/**
	 * ��ȡ��Ļ��ߣ�����������������λ��
	 */
	private void setPosistion() {
		// TODO Auto-generated method stub
		WindowManager wm = (WindowManager) getContext()
				.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		
		LayoutParams layout =  horizontalListView.getLayoutParams();
		layout.width = width/5 + width/60*2;
		//������ͼλ��
		horizontalListView.setX(width/2-layout.width/2);
	}
	/**
	 * ��������
	 */
	//��Ҫ��ʾ��Ӧ��
	//�����Ҫ��ʾ��Ӧ��
	ArrayList<JSONObject> listJson = new ArrayList<JSONObject>();
	JSONObject obj = null;
	String data = null;
	JSONArray array = null;
	
	private void initData() {
		JSONArray arr = null;
		//���
		Intent intent = getIntent();
		push_type = intent.getStringExtra(GCommon.INTENT_TYPE);
		
		//�����Ҫ��ʾ��Ӧ�õ�����
		if (GCommon.INTENT_PUSH_MESSAGE.equals(push_type)) {
			obj = GTools.getPushShareData(GCommon.SHARED_KEY_PUSHTYPE_MESSAGE, -1);
			data = GTools.getSharedPreferences().getString(GCommon.SHARED_KEY_PUSHTYPE_MESSAGE, "");
		}else{
			obj = GTools.getPushShareData(GCommon.SHARED_KEY_PUSHTYPE_MESSAGE_PIC, -1);
			data = GTools.getSharedPreferences().getString(GCommon.SHARED_KEY_PUSHTYPE_MESSAGE_PIC, "");
		}
		
		//��������е�Ӧ����Ϣ(�������)
		String allApp = GTools.getSharedPreferences().getString(GCommon.SHARED_KEY_AD_APP_DATA, "");
		
		//��ݷ�ʽ����
		String pushId_sc = intent.getStringExtra(APP_SC_PUSHID);
		if (!"".equals(pushId_sc)&&pushId_sc!=null) {
			push_type = intent.getStringExtra(PUSH_TYPE);
			if (GCommon.INTENT_PUSH_MESSAGE.equals(push_type))
			{
				obj = GTools.getPushShareDataByPushId(GCommon.SHARED_KEY_PUSHTYPE_MESSAGE, pushId_sc);
			}else
			{
				obj = GTools.getPushShareDataByPushId(GCommon.SHARED_KEY_PUSHTYPE_MESSAGE_PIC, pushId_sc);
			}
		}
		
		//�Ƽ�Ӧ����
		appName = new ArrayList<String>();
		//�Ƽ�Ӧ��ͼƬ·��
		image = new ArrayList<String>();
		//����ײ��Ƽ�Ӧ�á���ʾ��ϸ���ݣ���һ�ν���Ӧ�ã�û�е���Ƽ���Ĭ��ֵ999
		if(allApp == null || "".equals(allApp))
		{
			arr = new JSONArray();
		}
		else
		{
			try {
				//����е��������ݣ����ϣ�
				array = new JSONArray(data);
				arr = new JSONArray(allApp);
				String pId = intent.getStringExtra(JSON_ARR_POSITION);
				if (pId != null && !"".equals(pId))
				{
					if (GCommon.INTENT_PUSH_MESSAGE.equals(push_type))
					{
						obj = GTools.getPushShareDataByPushId(GCommon.SHARED_KEY_PUSHTYPE_MESSAGE, pId);
					}else
					{
						obj = GTools.getPushShareDataByPushId(GCommon.SHARED_KEY_PUSHTYPE_MESSAGE_PIC, pId);
					}
				}
				
				//����еĹ��ID
				String title = obj.getString("adId");
				//����е�uuID
				String uuid = obj.getString("uuid");
				
				//���������uuid���ӹ���е�uuid ��ȡ������е�adId
				//��ͨ������е�ADID��ȡ��JSON��������ͬadid�Ķ��󣬶��������ʾ
				ArrayList<String> uuidArray = new ArrayList<String>();
				
				for(int i =0;i<array.length();i++){
					JSONObject json = array.getJSONObject(i);
					if(json.has("uuid") && json.getString("uuid").equals(uuid)){
						uuidArray.add(json.getString("adId"));
						String pushId = json.getString("pushId");
						if(!pushId.equals(obj.getString("pushId")))
						{
							pushIds.add(pushId);
						}
					}
				}
				for (int i = 0; i < uuidArray.size(); i++) {
					for(int j = 0;j<arr.length();j++){
						JSONObject jsonArr = arr.getJSONObject(j);
						if(jsonArr.getString("adId").equals(uuidArray.get(i))){
							listJson.add(jsonArr);
							break;
						}
					}
				}
				//������Ҫ��ʾ��Ӧ�ü���
				for(int i=0;i<listJson.size();i++){
					JSONObject showJson = listJson.get(i);
					if(obj.getString("adId").equals(showJson.getString("adId"))){
						picList = new ArrayList<String>();
						String pic_path_1 = null;
						String pic_path_2 = null;
						String pic_path_3 = null;
						String pic_path_4 = null;
						String pic_path_5 = null;
						String pic_path_6 = null;
						
						 pic_path_1 = showJson.getString("pic_path_1");
						 pic_path_2 = showJson.getString("pic_path_2");
						 pic_path_3 = showJson.getString("pic_path_3");
						 pic_path_4 = showJson.getString("pic_path_4");
						 pic_path_5 = showJson.getString("pic_path_5");
						 pic_path_6 = showJson.getString("pic_path_6");
						
						if(pic_path_1 != null && !"".equals(pic_path_1))
							picList.add(pic_path_1);					
						if(pic_path_2 != null && !"".equals(pic_path_2))
							picList.add(pic_path_2);
						if(pic_path_3 != null && !"".equals(pic_path_3))
							picList.add(pic_path_3);
						if(pic_path_4 != null && !"".equals(pic_path_4))
							picList.add(pic_path_4);
						if(pic_path_5 != null && !"".equals(pic_path_5))
							picList.add(pic_path_5);
						if(pic_path_6 != null && !"".equals(pic_path_6))
							picList.add(pic_path_6);
						
						//��ʾ��ǰӦ�õ���ϸͼƬ
						showAppPicInfo();
						
						//����UI
						updataUI(showJson);
						//��ȡ��ǰ��ʾ��Ӧ�ö���
						showJsonObj=showJson;
						continue;
					}
					//����Ҫ��ʾ��Ӧ�ã���ȡ��Ӧ������ͼƬ  �����Ƽ�
					appName.add(showJson.getString("name"));
					image.add(showJson.getString("icon_path"));
				}
			} catch (JSONException e) {
				arr = new JSONArray();
			}
		}
		
		/**
		 * �ײ�Ӧ��ListView������������Ƽ�Ӧ�ã���ȡ�±���ʾ��ϸ����
		 */
		horizontalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
            public void onItemClick(AdapterView<?> parent,
                                    View view,
                                    int position,
                                    long id) {
			    String pushId = pushIds.get(position);
				Intent intent = new Intent(context,QLDownActivity.class);
				intent.putExtra(GCommon.INTENT_TYPE, push_type);
				intent.putExtra(JSON_ARR_POSITION ,pushId);
            	startActivity(intent);
            }
        });
		
	}

	/**
	 * ����UI����ʾ��ǰӦ�õ���ϸ��Ϣ��
	 * ���ܡ�Ӧ�����ơ���С��
	 * @param showJson
	 */
	private void updataUI(JSONObject showJson) {
		// TODO Auto-generated method stub
		//���·����Ϊ�գ�����ͼƬ
		try {
			textViewFZ.setText(showJson.getString("developer"));
			textDownNum.setText("��������"+showJson.getString("downloads")+"��");
			textAppName.setText(showJson.getString("name"));
			textViewXjj.setText(showJson.getString("summary"));
			textAppSize.setText(showJson.getString("size_m"));
			textAppVersion.setText(showJson.getString("version"));
			textAppUpdata.setText(showJson.getString("updatedDate"));
			expandableTextView.setText(showJson.getString("summary")+"/\n"+showJson.getString("describe"));
			if(showJson.getString("icon_path") != null && !"".equals(showJson.getString("icon_path"))){
			   imageViewTop.setImageBitmap(
					   BitmapFactory.decodeFile(context.getFilesDir().getPath()+"/"+showJson.getString("icon_path"))
				 );
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * ��ʾ��ǰӦ�õ�ͼƬ��Ϣ
	 */
	private void showAppPicInfo() {
		// TODO Auto-generated method stub
		View view=null;  
		ImageView iv;  
		for (int k = 0; k <picList.size(); k++) {  
		view = LayoutInflater.from(context).inflate(
				(Integer) mGetResourceId("qew_list_item_2", "layout",context	), null);  
		iv=(ImageView) view.findViewById(
				(Integer) mGetResourceId("imageView_list_app_pic", "id",context));
		Bitmap bitmap= BitmapFactory.decodeFile(context.getFilesDir().getPath()+"/"+ picList.get(k)) ;
	    WindowManager wm = (WindowManager) QLDownActivity.getContext()
	    		.getSystemService(Context.WINDOW_SERVICE);
		iv.setImageBitmap(bitmap);  
		ll.addView(view);  
	   }  
	}
	/**
	 * ���������
	 * @return 
	 */
	public static Context getContext() {
		// TODO Auto-generated method stub
		//��Ҫ��ʾ��Ӧ��ͼƬ
		return context;
	}
	
	

	//��ȡ��Դid
	public static Object mGetResourceId(String name, String type,Context context) 
	{
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


}