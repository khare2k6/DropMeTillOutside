package com.dmto.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.dmto.R;
import com.dmto.views.SelectionView;
import com.dmto.views.TimeSelectionView;

public class TestCustomView extends AppCompatActivity {

    private static String  TAG = TestCustomView.class.getSimpleName() ;
    private SelectionView mSelectionView;
    private TimeSelectionView mTsv;
    private TextView mSelectedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_test_custom_view);
        mSelectedTime = (TextView) findViewById(R.id.tv_selected_time);
        mSelectionView = (SelectionView) findViewById(R.id.sv_selection);
        mTsv = (TimeSelectionView) findViewById(R.id.time_selection_view);
        mSelectionView.setOnClickListener(new SelectionView.ISelectionViewClickListenor() {
            @Override
            public void onClick(float x, float y,String textClicked) {
                Log.d(TAG, "view clicked:x=" + x+ " y = " + y);
                Toast.makeText(TestCustomView.this,"clicked:"+textClicked,Toast.LENGTH_SHORT).show();
            }
        });
        mTsv.setOnTouchListner(new TimeSelectionView.IOnTouchListner() {
            @Override
            public void onTimeSelected(int min) {
                mSelectedTime.setText(min + " min");
            }
        });
    }


}
