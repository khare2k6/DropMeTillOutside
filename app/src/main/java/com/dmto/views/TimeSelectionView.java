package com.dmto.views;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.dmto.R;

import java.util.ArrayList;

/**
 * Created by ankitkha on 01-Feb-16.
 */
public class TimeSelectionView extends View {

    private static final String TAG = TimeSelectionView.class.getSimpleName();
    private Bitmap mBitmap;
    private Paint mPaintFill,mPaintStroke,mPaintText;
    private RectF mArcRectF,mRectF;
    private int mStrokeColor;
    private Point mCentre;
    private int mRadius,mNumberOfDivision,mDivisionLenght,mMinMarking,mMinuteGap,mMarkingTextSize;
    private Float mStrokeWidth;
    private float mRotateAngler = 0;
    private ArrayList<Integer> mAngles;
    private ArrayList<IOnTouchListner> mListListenors;


    public TimeSelectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.needle);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TimeSelectionView, 0, 0);
        mRadius = a.getInt(R.styleable.TimeSelectionView_radius_tsv, 0);
        mStrokeWidth = a.getFloat(R.styleable.TimeSelectionView_strokeWidth_tsv, (float) 0.0);
        mStrokeColor = a.getColor(R.styleable.TimeSelectionView_strokeColor_tsv,Color.BLACK);
        mNumberOfDivision = a.getInt(R.styleable.TimeSelectionView_numberOfDivision, 0);
        mDivisionLenght = a.getInt(R.styleable.TimeSelectionView_divisionLength, 0);
        mMinMarking = a.getInt(R.styleable.TimeSelectionView_minMarking, 0);
        mMinuteGap = a.getInt(R.styleable.TimeSelectionView_markingGapDifference, 0);
        mListListenors = new ArrayList<IOnTouchListner>();
        mMarkingTextSize = a.getInt(R.styleable.TimeSelectionView_markingTextSize,0);
        init();
    }


    private void init() {
        mPaintFill = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintFill.setStyle(Paint.Style.FILL);
        mPaintFill.setColor(Color.BLACK);

        mPaintStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintStroke.setStyle(Paint.Style.STROKE);
        mPaintStroke.setStrokeWidth(mStrokeWidth);
        mPaintStroke.setColor(mStrokeColor);

        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintText.setTextAlign(Paint.Align.CENTER);
        mPaintText.setTextSize(mMarkingTextSize);

    }

    private void drawArc(Canvas canvas) {
        //canvas.drawCircle(mCentre.x,mCentre.y,mRadius,mPaintStroke);
        Path path = new Path();
        path.arcTo(mRectF, 180, 180);
        canvas.drawPath(path, mPaintStroke);
        //canvas.drawArc(rect, 180, 360, true, mPaintStroke);
    }

    private void drawBitmap(Canvas canvas) {
        //75 is the width of the needle drawable, but on doing drawable.getWidth() it doesn't return the same.
        RectF rect = new RectF(mCentre.x - mBitmap.getWidth() / 2, mCentre.y - mRadius, mCentre.x + mBitmap.getWidth() / 2, mCentre.y);

        canvas.rotate(90-mRotateAngler,mCentre.x,mCentre.y);
        canvas.drawBitmap(mBitmap, null, rect, null);

    }

    private void drawDivisisions(Canvas canvas) {
        mAngles = new ArrayList<Integer>();
        int equalDevisionOfAngle = 180 / mNumberOfDivision;
        int minMarking = 0;
        for (int i = 0; i <= mNumberOfDivision; i++) {
            if (i == 0) {
                mAngles.add(0, 0);
            } else {
                mAngles.add(i, mAngles.get(i - 1) + equalDevisionOfAngle);
            }
            int startX = (int) (mCentre.x + (mRadius + mDivisionLenght) * Math.cos(Math.toRadians(-mAngles.get(i))));
            int startY = (int) (mCentre.y + (mRadius + mDivisionLenght) * Math.sin(Math.toRadians(-mAngles.get(i))));

            int stopX = (int) (mCentre.x + (mRadius - mDivisionLenght) * Math.cos(Math.toRadians(-mAngles.get(i))));
            int stopY = (int) (mCentre.y + (mRadius - mDivisionLenght) * Math.sin(Math.toRadians(-mAngles.get(i))));

            //draw partition
            canvas.drawLine(startX, startY, stopX, stopY, mPaintStroke);
            int textX = (int) (mCentre.x + (mRadius - 3 * mDivisionLenght) * Math.cos(Math.toRadians(-mAngles.get(i))));
            int textY = (int) (mCentre.y + (mRadius - 3 * mDivisionLenght) * Math.sin(Math.toRadians(-mAngles.get(i))));

            //draw text on the custom view
            canvas.drawText(""+minMarking, textX, textY , mPaintText);
            minMarking += mMinuteGap;
        }
        for (int i = 0; i < 180; i = i + mMinMarking) {
            int startX = (int) (mCentre.x + (mRadius + mDivisionLenght / 2) * Math.cos(Math.toRadians(-i)));
            int startY = (int) (mCentre.y + (mRadius + mDivisionLenght / 2) * Math.sin(Math.toRadians(-i)));

            int stopX = (int) (mCentre.x + (mRadius - mDivisionLenght / 2) * Math.cos(Math.toRadians(-i)));
            int stopY = (int) (mCentre.y + (mRadius - mDivisionLenght / 2) * Math.sin(Math.toRadians(-i)));

            //draw partition
            canvas.drawLine(startX, startY, stopX, stopY, mPaintStroke);

        }

    }

    private double angleOfTouchFromCenter(float x, float y) {
        double angleOfIntersection = 0;
        double angle1 = Math.atan2(y - mCentre.y, x - mCentre.x);
        double angle2 = Math.atan2(0, mRadius);
        angleOfIntersection = Math.abs(angle1 - angle2);
        angleOfIntersection = Math.toDegrees(angleOfIntersection);
//        Log.d(TAG, "angle:" + angleOfIntersection);
        return angleOfIntersection;
    }


    public int selectedTime(){
        return (int) Math.round(((mRotateAngler)*mMinuteGap*mNumberOfDivision)/180);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec),measureHeight(heightMeasureSpec));
        mCentre = new Point(getMeasuredWidth() / 2, getMeasuredHeight()-mBitmap.getWidth()/2);
        mRectF = new RectF(getMeasuredWidth()/2 - mRadius, getMeasuredHeight()- mRadius-mBitmap.getWidth()/2, getMeasuredWidth()/2 + mRadius, getMeasuredHeight()+ mRadius -mBitmap.getWidth()/2);

        //mRadius = 200;
    }

    private int measureWidth(int widthMeasureSpec) {
        int size = mRadius;
        size = resolveSizeAndState(size, widthMeasureSpec, 0);
        return size;
    }


    private int measureHeight(int heightMeasureSpec) {
        int size = mRadius + getPaddingTop()+getPaddingBottom()+mBitmap.getWidth();
        size = resolveSizeAndState(size, heightMeasureSpec, 0);
        return size;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawArc(canvas);
        drawDivisisions(canvas);
        drawBitmap(canvas);
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isTouchWithinTheArc(event.getX(), event.getY())) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    mRotateAngler = (float) angleOfTouchFromCenter(event.getX(), event.getY());
                    break;
            }
            invalidate();
            onTimeSelected(selectedTime());
            return true;
        }
        return false;
    }

    private boolean isTouchWithinTheArc(float x, float y) {
//        getMeasuredWidth()/2 - mRadius, getMeasuredHeight()/2- mRadius, getMeasuredWidth()/2 + mRadius, getMeasuredHeight()/2+ mRadius);
        if ((y < mCentre.y - mRadius) || (y >mCentre.y )) {
            return false;
        }
        return true;
    }

    public interface IOnTouchListner {
        public void onTimeSelected(int min);
    }

    public void setOnTouchListner(IOnTouchListner listner) {
        if (!mListListenors.contains(listner)) {
            mListListenors.add(listner);
        }
    }

    public void removeListner(IOnTouchListner listner) {
        if (mListListenors.contains(listner)) {
            mListListenors.remove(listner);
        }
    }

    public void onTimeSelected(int time) {
        for (IOnTouchListner listner : mListListenors) {
            listner.onTimeSelected(time);
        }
    }
}
