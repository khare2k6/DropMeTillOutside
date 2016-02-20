package com.dmto.ui;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dmto.DmtoApplication;
import com.dmto.R;
import com.dmto.implementations.Factory;
import com.dmto.server.IResponseCallback;
import com.dmto.user.ILoginLogoutUser;
import com.dmto.user.IUserSignup;
import com.dmto.utilities.Utils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private ILoginLogoutUser mLoginManager;
    private IUserSignup mSignupManager;
    private ProgressDialog mProgressDialog;
    private ProgressDialog.Builder mProgressDialogBuilder;

    private TextView mTvStatus;
    private EditText mEtUserName;
    private EditText mEtPassword;
    private Button mBtnLogIn;
    private Button mBtnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mProgressDialogBuilder = new ProgressDialog.Builder(this);

        mEtPassword = (EditText) findViewById(R.id.et_password);
        mEtUserName = (EditText) findViewById(R.id.et_username);
        mBtnLogIn = (Button) findViewById(R.id.btn_login);
        mBtnSignUp = (Button) findViewById(R.id.btn_signup);
        mTvStatus = (TextView) findViewById(R.id.tv_status);
        mLoginManager = Factory.getLoginLogoutImplementation(Factory.TYPE.SMACK_XMPP, this);
        mBtnSignUp.setOnClickListener(this);
        mBtnLogIn.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (DmtoApplication.getUser() == null) {
            //first time user opened this application, sign up required
            mTvStatus.setText("Signup required..");
            mSignupManager = Factory.getSignupImplementation(Factory.TYPE.SMACK_XMPP);
        } else {
            mTvStatus.setText("User already logged in");
        }

    }

    private void showProgressDialog(int message) {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Logging In");
        mProgressDialog.show();
    }

    private void clearProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
            mProgressDialog = null;
        }
    }

    private void setStatus(String message) {
        mTvStatus.setText(message);
        clearProgressDialog();
    }

    private void silentRegisterWithOpenFire(){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_signup:
                if (mEtUserName.getText() != null && mEtPassword.getText() != null) {
                    String email = mEtUserName.getText().toString();
                    String pwd = mEtPassword.getText().toString();
                    showProgressDialog(R.string.title_signingup);
                    final String uName = Utils.getUsernameFromEmail(email);
                    Log.d(TAG, "Signup cliicked:" + uName);
                    mSignupManager.newUserSignup(uName, email, pwd, new IResponseCallback<IUserSignup.SIGNUP_ERROR_STATES, IUserSignup.SIGNUP_ERROR_STATES>() {
                        @Override
                        public void onSuccess(IUserSignup.SIGNUP_ERROR_STATES signup_error_states) {
                            Log.d(TAG, "signup:onSuccess:" + signup_error_states);
                            setStatus(signup_error_states.name());
                            if (signup_error_states.equals(IUserSignup.SIGNUP_ERROR_STATES.NO_ERROR)) {
                                Log.d(TAG, "move to authntication activity");
                            }
                        }

                        @Override
                        public void onFailure(IUserSignup.SIGNUP_ERROR_STATES signup_error_states) {
                            Log.d(TAG, "signup:onFailure:" + signup_error_states);
                            setStatus(signup_error_states.name());
                        }
                    });

                }
                break;

            case R.id.btn_login:
                if (mEtUserName.getText() != null && mEtPassword.getText() != null) {
                    String email = mEtUserName.getText().toString();
                    String pwd = mEtPassword.getText().toString();
                    showProgressDialog(R.string.title_signingup);
                    final String uName = Utils.getUsernameFromEmail(email);
                    mLoginManager.loginUser(uName, pwd, new IResponseCallback<ILoginLogoutUser.SIGNIN_STATES, ILoginLogoutUser.SIGNIN_STATES>() {
                        @Override
                        public void onSuccess(ILoginLogoutUser.SIGNIN_STATES signin_states) {
                            Log.d(TAG, "login:onSuccess:" + signin_states);
                            setStatus(signin_states.getName());
                        }

                        @Override
                        public void onFailure(ILoginLogoutUser.SIGNIN_STATES signin_states) {
                            Log.d(TAG, "login:onSuccess:" + signin_states);
                            setStatus(signin_states.getName());
                        }
                    });
                }
                break;
        }
    }
}
