package com.dmto.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.dmto.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ankitkha on 24-Jan-16.
 */
public class SelectionView extends View {
    private static final String TAG = SelectionView.class.getSimpleName();
    String mLabelTextOne, mLabelTextTwo, mLabelTextThree;
    int mWidthLabelOne,mWidthLabelTwo, mWidthLabelThree,mMaxLabelHeight;
    int mXPaddingRect,mYPaddingRect,mXPaddingText, mYPaddingText;
    int mNoOfOptions, mSelectedIndex =0;
    int mTextSize;
    float mBorderStrokeWidth;
    float mRectStartX, mRectStartY, mRectRightX, mRectBottomY;
    float mTextStartX, mTextStartY;
    private List<ISelectionViewClickListenor> mClickListenors;

    int mBackgroundColor,mSelectedBackgroundColor, mTextColor, mStrokeColor;
    Paint mPaintBackgroundColor, mPaintTextColor, mPaintStroke;
    public SelectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d(TAG, "Constructor selectionView");
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SelectionView, 0, 0);
        try{
            mLabelTextOne = a.getString(R.styleable.SelectionView_text1);
            mLabelTextTwo = a.getString(R.styleable.SelectionView_text2);
            mLabelTextThree = a.getString(R.styleable.SelectionView_text3);
            mNoOfOptions = a.getInt(R.styleable.SelectionView_numberOfOptions, 0);
            mTextSize = a.getInt(R.styleable.SelectionView_textSize,0);
            mXPaddingRect = a.getInt(R.styleable.SelectionView_xPaddingRectangle,0);
            mYPaddingRect = a.getInt(R.styleable.SelectionView_yPaddingRectangle,0);
            mXPaddingText = a.getInt(R.styleable.SelectionView_xPaddingText,0);
            mYPaddingText = a.getInt(R.styleable.SelectionView_yPaddingText,0);
            mBackgroundColor = a.getColor(R.styleable.SelectionView_backgroundColor, 0);
            mSelectedBackgroundColor = a.getColor(R.styleable.SelectionView_selectedBackgroundColor, 0);
            mTextColor = a.getColor(R.styleable.SelectionView_textColor, 0);
            mStrokeColor = a.getColor(R.styleable.SelectionView_strokeColor, 0);
            mClickListenors = new ArrayList<ISelectionViewClickListenor>();
            mBorderStrokeWidth = a.getFloat(R.styleable.SelectionView_borderStroke, (float) 0.0);

            init();
        }finally {
            a.recycle();
        }
    }

    private void init() {
        mPaintBackgroundColor = new Paint(Paint.ANTI_ALIAS_FLAG);
        //fill background colour of rectangle
        mPaintBackgroundColor.setStyle(Paint.Style.FILL);
        mPaintBackgroundColor.setColor(mBackgroundColor);

        //text color
        mPaintTextColor = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintTextColor.setColor(mTextColor);
        mPaintTextColor.setTextSize(mTextSize);
        mPaintTextColor.setTextAlign(Paint.Align.LEFT);

        //rectangle border stroke colour
        mPaintStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintStroke.setColor(mStrokeColor);
        mPaintStroke.setStyle(Paint.Style.STROKE);
        mPaintStroke.setStrokeWidth(mBorderStrokeWidth);
        mPaintStroke.setColor(Color.BLACK);

        //width required by label 1
        Rect bounds = new Rect();
        mPaintTextColor.getTextBounds(mLabelTextOne, 0, mLabelTextOne.length(), bounds);
        mWidthLabelOne = bounds.width();
        int heightLabelOne = bounds.height();

        //width required by label 2
        bounds = new Rect();
        mPaintTextColor.getTextBounds(mLabelTextTwo, 0, mLabelTextTwo.length(), bounds);
        mWidthLabelTwo = bounds.width();
        int heightLabelTwo = bounds.height();

        //width required by label 3
        bounds = new Rect();
        mPaintTextColor.getTextBounds(mLabelTextThree, 0, mLabelTextThree.length(), bounds);
        mWidthLabelThree = bounds.width();
        int heightLabelThree = bounds.height();

        //max height required out of all the three labels
        mMaxLabelHeight = Math.max(heightLabelOne, Math.max(heightLabelTwo, heightLabelThree));

        mRectStartX = getPaddingLeft() + mXPaddingRect;
        mRectStartY = getPaddingTop() + mYPaddingRect;

        mTextStartX = mRectStartX + mXPaddingText;
        mTextStartY = mRectStartY + mYPaddingText;
        Log.d(TAG, "init done ");
    }
    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw");
        drawRectangle(canvas);
        drawLabel(canvas);
        drawVerticalLine(canvas);
    }

    private int measureHeight(int measureSpec) {
        int size = (int) mTextStartY;
        Log.d(TAG, "measureHeight mTextStartY:"+mTextStartY);
        //size += mYPaddingRect;// padding between view boundry and rect boundry
       // mRectStartY = size; // this is start Y position for rect

       // size += mYPaddingText; // padding between rect boundry and text
        size += mMaxLabelHeight;//max vertical space required by text labels
        Log.d(TAG, "measureHeight mMaxLabelHeight:"+mMaxLabelHeight+" size:"+size);
        size += mYPaddingText;//padding between bottom part of text label and rect boundry

        mRectBottomY = size;
        Log.d(TAG, "measureHeight mRectBottomY:"+mRectBottomY+" size:"+size);
        size += mYPaddingRect;//padding between rect boundry and view boundry
        Log.d(TAG, "measureHeight mRectBottomY+padding text:"+size);
        size += getPaddingBottom();
        Log.d(TAG, "measureHeight final size asked for::"+size);

        size = resolveSizeAndState(size, measureSpec, 0);
        Log.d(TAG, "measureHeight:"+size);
        return size;
    }

    private int measureWidth(int measureSpec) {
        int size = (int) mTextStartX;
        int textLabelWidthIncludingSpaces = (int) mTextStartX + mWidthLabelOne + mXPaddingText
                + mXPaddingText + mWidthLabelTwo + mXPaddingText;

        if (mNoOfOptions > 2) {
            textLabelWidthIncludingSpaces += mXPaddingText + mWidthLabelThree + mXPaddingText;
        }

        size += textLabelWidthIncludingSpaces;
        mRectRightX =  textLabelWidthIncludingSpaces; // this is right corner of rectangle
        size += mXPaddingRect;//padding between rectangle right boundry and view's right boundry
        size += getPaddingRight();
        size = resolveSizeAndState(size, measureSpec, 0);
        Log.d(TAG, "measureWidth:"+size);
        return size;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        Log.d(TAG, "onMeasure");
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private void drawLabel(Canvas canvas) {
        //draw 1st label
        float xStartOfLabel = mTextStartX ;
        float yStartOfLabel = mTextStartY + mMaxLabelHeight;
        canvas.drawText(mLabelTextOne,//label 1 string
                xStartOfLabel  , //start position of label 1
                yStartOfLabel, mPaintTextColor);

        //draw 2nd label
        xStartOfLabel =  xStartOfLabel + mWidthLabelOne + 2*mXPaddingText;
        canvas.drawText(mLabelTextTwo,//label 2 string
                xStartOfLabel,//start after label one's width and leave some space between the two label
                yStartOfLabel, mPaintTextColor);

        //draw 3rd label
        xStartOfLabel = xStartOfLabel + mWidthLabelTwo + 2 * mXPaddingText;
        canvas.drawText(mLabelTextThree,
                xStartOfLabel, //width required by label 2 and some gap after that
                yStartOfLabel, mPaintTextColor);
    }

    private void drawRectangle(Canvas canvas) {
        float roundRadii = mMaxLabelHeight / 3;

        RectF rect = new RectF(mRectStartX, mRectStartY, mRectRightX, mRectBottomY);
        //draw filled rectangle
        mPaintBackgroundColor.setColor(mBackgroundColor);
        canvas.drawRoundRect(rect, roundRadii, roundRadii, mPaintBackgroundColor);

        switch (mSelectedIndex) {
            case 1: {
                mPaintBackgroundColor.setColor(mSelectedBackgroundColor);
                float selectedRectStartX = mRectStartX;
                float selectedRectEndX = mRectStartX + mXPaddingText + mWidthLabelOne + mXPaddingText;
                rect = new RectF(selectedRectStartX, mRectStartY, selectedRectEndX, mRectBottomY);
                canvas.drawRoundRect(rect, roundRadii, roundRadii, mPaintBackgroundColor);
                break;
            }
            case 2: {
                mPaintBackgroundColor.setColor(mSelectedBackgroundColor);
                float selectedRectStartX = mRectStartX + mXPaddingText + mWidthLabelOne + mXPaddingText;
                float selectedRectEndX = selectedRectStartX + mXPaddingText +mWidthLabelTwo + mXPaddingText;
                rect = new RectF(selectedRectStartX, mRectStartY, selectedRectEndX, mRectBottomY);
                canvas.drawRoundRect(rect, roundRadii, roundRadii, mPaintBackgroundColor);
                break;
            }
            case 3:{
                mPaintBackgroundColor.setColor(mSelectedBackgroundColor);
                float selectedRectStartX = mRectStartX + mXPaddingText + mWidthLabelOne + mXPaddingText+mXPaddingText+ mWidthLabelTwo + mXPaddingText;
                float selectedRectEndX = selectedRectStartX + mXPaddingText+mWidthLabelThree + mXPaddingText;
                rect = new RectF(selectedRectStartX, mRectStartY, selectedRectEndX, mRectBottomY);
                canvas.drawRoundRect(rect, roundRadii, roundRadii, mPaintBackgroundColor);
                break;
            }
            default:
                break;
        }
        //draw border of the rectangle
        rect = new RectF(mRectStartX, mRectStartY, mRectRightX, mRectBottomY);
        canvas.drawRoundRect(rect,roundRadii, roundRadii , mPaintStroke);
        //canvas.drawRect(x, y, right, bottom, mPaintBackgroundColor);
    }

    private void drawVerticalLine(Canvas canvas) {
        float verticalSeparatorX = mTextStartX + mWidthLabelOne + mXPaddingText;
        float verticalSeparatorY = mRectBottomY;
        canvas.drawLine(verticalSeparatorX,mRectStartY,verticalSeparatorX,mRectBottomY,mPaintStroke);
        if (mNoOfOptions > 2) {
            verticalSeparatorX +=  mXPaddingText+ mWidthLabelTwo+ mXPaddingText;
            canvas.drawLine(verticalSeparatorX,mRectStartY,verticalSeparatorX,mRectBottomY,mPaintStroke);
        }
    }

    public void setOnClickListener(ISelectionViewClickListenor listenor) {
        Log.d(TAG, "listner registered:" + listenor);
        mClickListenors.add(listenor);
    }

    public void removeOnClickListenor(ISelectionViewClickListenor listenor) {
        mClickListenors.remove(listenor);
    }

    private void viewClicked(float x, float y,String textClicked) {
        for (ISelectionViewClickListenor listenor : mClickListenors) {
            Log.d(TAG, "informing listner:" + listenor);
            listenor.onClick(x,y,textClicked);
        }
    }

    private String clickedText(float x) {
        float rangeLabelOne = mTextStartX + mWidthLabelOne + mXPaddingText;
        float rangeLabelTwo = rangeLabelOne + mXPaddingText +mWidthLabelTwo + mXPaddingText;
        float rangeLabelThree = rangeLabelTwo + mXPaddingText +mWidthLabelThree + mXPaddingText;

        String label = null;
        if (x < rangeLabelOne) {
            mSelectedIndex = 1;
            label = mLabelTextOne;
        } else if (x < rangeLabelTwo) {
            mSelectedIndex = 2;
            label = mLabelTextTwo;
        } else if (x < rangeLabelThree) {
            mSelectedIndex = 3;
            label= mLabelTextThree;
        }
        invalidate();
        return label;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean touched = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                touched = true;
                break;
        }
        if (touched) {
            viewClicked(event.getX(), event.getY(),clickedText(event.getX()));
        }
        return true;
    }

    public interface ISelectionViewClickListenor{
        public void onClick(float x,float y,String option);
    }
}
