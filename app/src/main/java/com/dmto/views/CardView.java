package com.dmto.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dmto.R;
import com.dmto.utilities.TextUtils;


import java.util.ArrayList;

/**
 * Created by ankitkha on 17-Feb-16.
 */
public class CardView extends LinearLayout {
    private String mTitle;
    private AutoCompleteTextView mActvName;
    private SelectionView mSelectionView;
    private TimeSelectionView mTimerView;
    private boolean mIsShowMapViewEnabled,mIsShowAutoCompleteTextViewEnabled,mIsShowSelectionViewEnabled, mIsShowTimerViewEnabled;
    private boolean mIsVerticalBorderRightEnabled,mIsVerticalBorderLeftEnabled,mIsHorizontalBorderTopEnabled, mIsHorizontalBorderBottomEnabled;
    private View mThisView;

    private TextView mTvTitle;
    public CardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SelectionView, 0, 0);
        try{
            mIsShowMapViewEnabled = a.getBoolean(R.styleable.CardView_showMapView, false);
            mIsShowAutoCompleteTextViewEnabled = a.getBoolean(R.styleable.CardView_showAutoCompleteTextView, false);
            mIsShowSelectionViewEnabled = a.getBoolean(R.styleable.CardView_showSelectionView, false);
            mIsShowTimerViewEnabled = a.getBoolean(R.styleable.CardView_showTimerView, false);

            mIsVerticalBorderRightEnabled = a.getBoolean(R.styleable.CardView_verticalBorderRight, false);
            mIsVerticalBorderLeftEnabled = a.getBoolean(R.styleable.CardView_verticalBorderLeft, false);
            mIsHorizontalBorderTopEnabled = a.getBoolean(R.styleable.CardView_horizontalBorderTop, false);
            mIsHorizontalBorderBottomEnabled = a.getBoolean(R.styleable.CardView_horizontalBorderBottom, false);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mThisView = inflater.inflate(R.layout.view_card, this, true);
            init();
        }finally {
            a.recycle();
        }
    }

    public void setTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            mTitle = title;
            mTvTitle.setText(mTitle);
        }
    }

    private void init() {
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        if (mIsShowAutoCompleteTextViewEnabled) {

        }
    }
}
