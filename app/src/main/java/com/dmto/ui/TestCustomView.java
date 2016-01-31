package com.dmto.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.dmto.R;
import com.dmto.views.SelectionView;

public class TestCustomView extends AppCompatActivity {

    private static String  TAG = TestCustomView.class.getSimpleName() ;
    private SelectionView mSelectionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_test_custom_view);
        mSelectionView = (SelectionView) findViewById(R.id.sv_selection);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSelectionView.setOnClickListener(new SelectionView.ISelectionViewClickListenor() {
            @Override
            public void onClick(float x, float y,String textClicked) {
                Log.d(TAG, "view clicked:x=" + x+ " y = " + y);
                Toast.makeText(TestCustomView.this,"clicked:"+textClicked,Toast.LENGTH_SHORT).show();
            }
        });
    }


}
