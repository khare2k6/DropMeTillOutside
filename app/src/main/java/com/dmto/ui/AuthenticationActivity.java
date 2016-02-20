package com.dmto.ui;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dmto.R;
import com.dmto.implementations.Factory;
import com.dmto.user.IAuthenticateUser;
import com.dmto.webservice.Constants;
import com.dmto.webservice.HttpRequest;

public class AuthenticationActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = AuthenticationActivity.class.getSimpleName();
    private Button mBtnSubmitCode;
    private TextView mTvMessage;
    private EditText mEtSecurityCode;
    private String mEmail;
    private ProgressDialog mProgressDialog;
    private IAuthenticateUser mAuthenticateUser;
    private boolean mIsAuthenticationSuccessful;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        mBtnSubmitCode = (Button) findViewById(R.id.btn_submit_authentication_code);
        mTvMessage = (TextView) findViewById(R.id.tv_authentication_message);
        mEtSecurityCode = (EditText) findViewById(R.id.et_authentication_code);
        mProgressDialog = new ProgressDialog(this);
        mAuthenticateUser = Factory.getUserAuthenticateImplementation(this, Factory.TYPE.SMACK_XMPP);
        mProgressDialog.setTitle(R.string.title_please_wait);
    }

    private void showToast(String msg) {
        Toast.makeText(AuthenticationActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mEmail = getIntent().getStringExtra(Constants.PARAM_EMAIL);
        mTvMessage.setText(mTvMessage.getText().toString() + Constants.COLON + mEmail);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit_authentication_code:
                if (mEtSecurityCode.getText() != null) {
                    String securityCodeEntered = mEtSecurityCode.getText().toString();
                    AuthenticationTask task = new AuthenticationTask();
                    task.execute(mEmail, securityCodeEntered);
                    mProgressDialog.show();
                }
                break;
        }
    }

    private class AuthenticationTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            mAuthenticateUser.authenticateThisUser(params[0], params[1], new IAuthenticateUser.IAuthenticationResult() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "onSuccess");
                    mIsAuthenticationSuccessful = true;
                }

                @Override
                public void onFailure(String message) {
                    mIsAuthenticationSuccessful = false;
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mProgressDialog.cancel();
            if (mIsAuthenticationSuccessful) {
                showToast(getResources().getString(R.string.title_authentication_sucessful));
                //move to main screen
            }else{
                showToast(getResources().getString(R.string.title_authentication_failed));
                mTvMessage.setText(R.string.title_try_again);

            }
        }
    }

}
