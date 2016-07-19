package com.qinglu.ad;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.guang.client.GCommon;
import com.guang.client.tools.GLog;
import com.guang.client.tools.GTools;
import com.qinglu.ad.listener.QLSpotDialogListener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class QLSpotView extends RelativeLayout{
	private int id;
	private ImageView close;
	private Bitmap viewBm;
	private Bitmap closeBm;
	private QLSpotDialogListener dialogListener;
	private QLSize size;
	private int type;//0���������Ͳ��� 1������һ������
	
	public QLSpotView(Context context) {
		super(context);
	}
	
	public QLSpotView(Context context,int animationType) {
		super(context);
		this.type = GCommon.SPOT_TYPE_NORMAL;
		this.init(context, animationType);
	}
	
	public QLSpotView(Context context,int animationType,int type,QLSpotDialogListener dialogListener) {
		super(context);
		this.type = type;
		this.dialogListener = dialogListener;
		this.init(context, animationType);
	}
	
	

	public QLSize getSize() {
		return size;
	}

	public void setSize(QLSize size) {
		this.size = size;
	}

	public QLSpotDialogListener getDialogListener() {
		return dialogListener;
	}

	public void setDialogListener(QLSpotDialogListener dialogListener) {
		this.dialogListener = dialogListener;
	}
	
	private void init(final Context context,int animationType)
	{
		SharedPreferences mySharedPreferences = GTools.getSharedPreferences();
		try {
			JSONObject obj = null;
			if(this.type == GCommon.SPOT_TYPE_NORMAL)
			{
				JSONArray arr = new JSONArray(mySharedPreferences.getString(GCommon.SHARED_KEY_PUSHTYPE_SPOT, ""));
				obj = arr.getJSONObject((int)(Math.random()*10%arr.length()));
			}
			else
			{
				obj = GTools.getPushShareData(GCommon.SHARED_KEY_PUSHTYPE_SPOT, -1);	
				//obj = new JSONObject(mySharedPreferences.getString(GCommon.SHARED_KEY_PUSHTYPE_SPOT, ""));
			}
			getSpotView(context,animationType,obj);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		
	}

	@SuppressLint("NewApi")
	private void getSpotView(final Context context,int animationType,final JSONObject obj)
	{
		ImageView view = new ImageView(context);
		try {			
			viewBm = BitmapFactory.decodeFile(context.getFilesDir().getPath()+"/"+ obj.getString("picPath")) ;
			view.setImageBitmap(viewBm);
			//�ײ�����			
			final QLSpotView layout = this;
			
			//����
			LinearLayout.LayoutParams layoutGrayParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
			layoutGrayParams.gravity = Gravity.CENTER;
			
			LinearLayout layoutGray = new LinearLayout(context);
			layoutGray.setBackgroundColor(Color.BLACK);
			layoutGray.setAlpha(0.6f);
			layoutGray.setLayoutParams(layoutGrayParams);
			layout.addView(layoutGray);	
			//���
			QLSize ss = GTools.getScreenSize(context);
			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			// ���ù����������λ��
			layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
			float width = viewBm.getWidth();
			float height = viewBm.getHeight();
			int dir = context.getResources().getConfiguration().orientation;
			if(dir == 1)
			{
				layoutParams.width = (int) (ss.width*0.9);
				layoutParams.height = (int) (ss.width*0.9/width*height);
			}
			else
			{
				layoutParams.height = (int) (ss.height*0.8);
				layoutParams.width = (int) (ss.height*0.8/height*width);
				
			}
			this.setSize(new QLSize(layoutParams.width, layoutParams.height));
			view.setId(1);
			view.setScaleType(ScaleType.FIT_XY);

			layout.addView(view, layoutParams);		
			
			//�رհ�ť		
			RelativeLayout.LayoutParams paramsClose = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
			
			close = new ImageView(context);
			closeBm = BitmapFactory.decodeFile(context.getFilesDir().getPath()+"/images/close.png");
			close.setImageBitmap(closeBm);
			close.setVisibility(View.GONE);
			
			if(dir == 1)
			{
				paramsClose.width = (int) (ss.width*0.05);
				paramsClose.height = (int) (ss.width*0.05);
			}
			else
			{
				paramsClose.width = (int) (ss.height*0.05);
				paramsClose.height = (int) (ss.height*0.05);
			}
			
			paramsClose.addRule(RelativeLayout.ALIGN_TOP, 1);
			paramsClose.addRule(RelativeLayout.ALIGN_RIGHT, 1);
			
			layout.addView(close,paramsClose);
			
			//���ö���
			if(animationType == GCommon.ANIM_SIMPLE)
			{
				AnimationSet animaSet = new AnimationSet(true);
				AlphaAnimation anima = new AlphaAnimation((float) 0.5, 1);
				anima.setDuration(500);
				animaSet.addAnimation(anima);
				animaSet.setAnimationListener(new QLAnimationListener());
				view.startAnimation(animaSet);
			}
			else if(animationType == GCommon.ANIM_ADVANCE)
			{
				AnimationSet animaSet = new AnimationSet(true);
																
				ScaleAnimation sca1 = new ScaleAnimation(-0.8f, 1.f, 1.f, 1.f, layoutParams.width/2, layoutParams.height/2);
				sca1.setDuration(600);
				ScaleAnimation sca2 = new ScaleAnimation(1.2f, 1.f, 1.f, 1.f, layoutParams.width/2, layoutParams.height/2);
				sca2.setDuration(400);
							
				animaSet.addAnimation(sca1);
				animaSet.addAnimation(sca2);
				animaSet.setAnimationListener(new QLAnimationListener());
				view.startAnimation(animaSet);
			}
			else
			{
				close.setVisibility(View.VISIBLE);
			}
			view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					layout.removeAllViews();
					ViewGroup parent = ( ViewGroup )layout.getParent();
					parent.removeView(layout);
					//Toast.makeText(context, "��ʼΪ������Ӧ��...", 0).show();
					if(dialogListener != null)
					{
						dialogListener.onSpotClick(true);
					}
					if(viewBm != null && !viewBm.isRecycled())
					{
						viewBm.recycle();
						viewBm = null;
					}
					if(closeBm != null && !closeBm.isRecycled())
					{
						closeBm.recycle();
						closeBm = null;
					}
					System.gc();
					if(dialogListener != null)
					{
						dialogListener.onSpotClosed();
					}
				}
			});
			
			close.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					layout.removeAllViews();
					ViewGroup parent = ( ViewGroup )layout.getParent();
					parent.removeView(layout);
					
					if(viewBm != null && !viewBm.isRecycled())
					{
						viewBm.recycle();
						viewBm = null;
					}
					if(closeBm != null && !closeBm.isRecycled())
					{
						closeBm.recycle();
						closeBm = null;
					}
					System.gc();
					
					if(dialogListener != null)
					{
						dialogListener.onSpotClosed();
					}
				}
			});
			if(dialogListener != null)
			{
				dialogListener.onShowSuccess();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	class QLAnimationListener implements AnimationListener
	{

		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			if(close != null)
			{
				close.setVisibility(View.VISIBLE);
			}
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
