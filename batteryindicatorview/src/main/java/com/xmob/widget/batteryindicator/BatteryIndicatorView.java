package com.xmob.widget.batteryindicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by renjun on 12/9/16.
 */

public class BatteryIndicatorView extends View {

    private int mMaxLevel = 8;
    private int mCurLevel;

    private int mLayoutWidth, mLayoutHeight;
    private int mTickWidth, mTickHeight;
    private int mTickMarginH;
    private int mFirstTickX, mFirstTickY;

    private Paint mPaint;
    private RectF borderRect;

    public BatteryIndicatorView(Context context) {
        this(context, null);
    }

    public BatteryIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setLevel(int value) {
        mCurLevel = value;
        invalidate();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mLayoutWidth = w;
        mLayoutHeight = h;
        int ww = mLayoutWidth - getPaddingLeft() - getPaddingRight();
        int hh = mLayoutHeight - getPaddingTop() - getPaddingBottom();
        borderRect = new RectF(0, 0, ww, hh);
        borderRect.offset(getPaddingLeft(), getPaddingTop());

        mTickHeight = hh * 3 / 5;
        mTickWidth = (int)(ww/(mMaxLevel + (mMaxLevel +1) * 0.8));
        mTickMarginH = mTickWidth*4/5;

        mFirstTickX = getPaddingLeft() + (ww - mTickWidth * mMaxLevel - mTickMarginH*(mMaxLevel -1))/2;
        mFirstTickY = getPaddingTop() + (hh-mTickHeight)/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(borderRect, mFirstTickY, mFirstTickY, mPaint);
        int x;
        for (int i = 0; i < mMaxLevel; i++ ) {
            x = mFirstTickX + (mTickMarginH + mTickWidth)*i;

            if ( i < mCurLevel) {
                mPaint.setColor(Color.BLACK);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawRect(x, mFirstTickY, x + mTickWidth, mFirstTickY + mTickHeight, mPaint);
            }

            mPaint.setColor(Color.BLACK);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(x, mFirstTickY, x + mTickWidth, mFirstTickY + mTickHeight, mPaint);
        }
    }
}