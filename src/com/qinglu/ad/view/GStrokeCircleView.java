package com.qinglu.ad.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class GStrokeCircleView extends View {

	private final int mCircleLineStrokeWidth = 4;

	private final int mTxtStrokeWidth = 2;

	// ��Բ���ڵľ�������
	private final RectF mRectF;

	private final Paint mPaint;

	private final Context mContext;


	public GStrokeCircleView(Context context, AttributeSet attrs) {
		super(context, attrs);

		mContext = context;
		mRectF = new RectF();
		mPaint = new Paint();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int width = this.getWidth();
		int height = this.getHeight();

		if (width != height) {
			int min = Math.min(width, height);
			width = min;
			height = min;
		}

		// ���û����������
		mPaint.setAntiAlias(true);		
		mPaint.setStyle(Style.STROKE);
		// λ��
		mRectF.left = mCircleLineStrokeWidth / 2; // ���Ͻ�x
		mRectF.top = mCircleLineStrokeWidth / 2; // ���Ͻ�y
		mRectF.right = width - mCircleLineStrokeWidth / 2; // ���½�x
		mRectF.bottom = height - mCircleLineStrokeWidth / 2; // ���½�y
			
		mPaint.setStrokeWidth(mCircleLineStrokeWidth);
		mPaint.setColor(Color.WHITE);
		canvas.drawArc(mRectF, -90, 360,false, mPaint);
			
	}

}
