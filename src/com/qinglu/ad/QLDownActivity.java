package com.qinglu.ad;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.guang.client.GCommon;
import com.guang.client.GuangClient;
import com.guang.client.tools.GTools;
import com.xugu.qewad.MainActivity;
import com.xugu.qewad.R;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
	QLImageTextButton textCollect; 
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
		setContentView((Integer) GTools.getResourceId("qew_down_main", "layout"));
		horizontalListView = (QLHorizontalListView) findViewById((Integer) GTools.getResourceId("horizontalListView", "id"));
		textCollect = (QLImageTextButton) findViewById((Integer) GTools.getResourceId("textView_shouc", "id"));
		expandableTextView = (QLExpandableTextView) findViewById((Integer) GTools.getResourceId("textView_app_js", "id"));
		buttonDown = (Button) findViewById((Integer) GTools.getResourceId("button_Down", "id"));
		textFengXiang = (TextView) findViewById((Integer) GTools.getResourceId("textView_fenxiang", "id"));
		imageViewTop = (ImageView) findViewById((Integer) GTools.getResourceId("imageView_app_imager", "id"));
		textAppName = (TextView) findViewById((Integer) GTools.getResourceId("textView_app_name", "id"));
		textDownNum = (TextView) findViewById((Integer) GTools.getResourceId("textView_down_num", "id"));
		relativeLayout = (RelativeLayout) findViewById((Integer) GTools.getResourceId("relativa_list", "id"));
		textAppType = (TextView) findViewById((Integer) GTools.getResourceId("textView_app_type","id"));
		textAppSize = (TextView) findViewById((Integer) GTools.getResourceId("textView_app_size", "id"));
		textViewFZ = (TextView) findViewById((Integer) GTools.getResourceId("textView_fzgs","id"));
		textAppVersion = (TextView) findViewById((Integer) GTools.getResourceId("textView_version_num", "id"));
		textAppUpdata = (TextView) findViewById((Integer) GTools.getResourceId("textView_app_updata", "id"));
		imageViewUpdata  = (ImageView) findViewById((Integer) GTools.getResourceId("imageView_app_updata", "id"));
		textViewXjj = (TextView) findViewById((Integer) GTools.getResourceId("textView_xjj", "id"));
		scrollView = (ScrollView) findViewById((Integer) GTools.getResourceId("scrollView1", "id"));
		ll  = (LinearLayout) findViewById((Integer) GTools.getResourceId("ll", "id"));
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
		
		/**
		 * ����
		 */
		Intent intent = getIntent();
		String pId = intent.getStringExtra(JSON_ARR_POSITION);
		if (pId != null && !"".equals(pId))
		{
			int type = GCommon.PUSH_TYPE_MESSAGE;
			if(GCommon.INTENT_PUSH_MESSAGE_PIC.equals(push_type)){
				type = GCommon.PUSH_TYPE_MESSAGE_PIC;
			}
			//�ϴ�ͳ����Ϣ
			try {
				GTools.uploadPushStatistics(type,GCommon.UPLOAD_PUSHTYPE_SHOWNUM,obj.getString("pushId"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
				Log.e("����", "textFengXiang");
				Toast.makeText(context, "����", Toast.LENGTH_SHORT);
			}
		});
		
		
		//�ղء�����������
		textCollect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    Intent shortcut = new Intent(  
			    "com.android.launcher.action.INSTALL_SHORTCUT");  
			    // �������ؽ�  
			    shortcut.putExtra("duplicate", false);  
			    // ��������  
			    shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, "�ӷƿ��");  
			    // ����ͼ��  
			    shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,  
			    Intent.ShortcutIconResource.fromContext(context,  
				(Integer) GTools.getResourceId("ic_launcher", "drawable")));  
			    
			    // ������ͼ�Ϳ�ݷ�ʽ��������  
			    Intent launcherIntent = new Intent(context,context.getClass());
		        launcherIntent.setAction(Intent.ACTION_MAIN);
		        launcherIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, launcherIntent);
                // ���͹㲥
                sendBroadcast(shortcut);
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
	
		
		
//		//ˢ���Ƽ�Ӧ�á���������
		
	     imageViewUpdata.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//ˢ�³���
				ArrayList<String> aName = new ArrayList<String>();
				ArrayList<String> aImage = new ArrayList<String>();
				ArrayList<JSONObject> listjson = new ArrayList<JSONObject>();
				ArrayList<String> pd = new ArrayList<String>();
				//��¼����ʱ��
			    if(t1==0){//��һ�ε�������ʼ��Ϊ���ε�����ʱ�䡢ˢ��
			         t1= (new Date()).getTime();
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
			      }else{
			         long curTime = (new Date()).getTime();//���ص�����ʱ��
			         System.out.println("���ε������ʱ�䣺"+(curTime-t1));//���㱾�غ��ϴε�ʱ���
			         if(curTime-t1<5*1000){
			           //���5�������������Ը�����Ҫ�޸ļ��ʱ��
			         t1 = curTime;//��ǰ�����¼���Ϊ�ϴ�ʱ��
			         Toast.makeText(context, "���Ĳ�������Ƶ��", Toast.LENGTH_SHORT).show();
			       }
			    }
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
		//�����ͼЯ��������
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
				(Integer) GTools.getResourceId("qew_list_item_2", "layout"), null);  
		iv=(ImageView) view.findViewById(
				(Integer) GTools.getResourceId("imageView_list_app_pic", "id"));
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

}