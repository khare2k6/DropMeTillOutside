package com.dmto.user.parse;

import android.util.Log;

import com.dmto.server.IResponseCallback;
import com.dmto.user.IUserSignup;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.parse.SignUpCallback;

import java.util.HashMap;

/**
 * Created by ankitkha on 23-Dec-15.
 */
public class ParseUserSignup implements IUserSignup {


    private static final String TAG = ParseUserSignup.class.getSimpleName();

    @Override
    public void isUsernameAvailable(String uName, final IResponseCallback<Boolean,String> callback) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(KEY_USERNAME, uName);

        ParseCloud.callFunctionInBackground(ParseConstants.API_IS_USERNAME_AVAIALBLE, params, new FunctionCallback<Boolean>() {
            @Override
            public void done(Boolean isAvailable, ParseException e) {
                if(e == null){
                    callback.onSuccess(isAvailable);
                }else{
                    callback.onFailure("Some server error!"+e);
                }
            }
        });
    }

    @Override
    public void newUserSignup(String userName, String email, String pwd, final IResponseCallback<SIGNUP_ERROR_STATES,SIGNUP_ERROR_STATES> callback) {
        ParseUser user = new ParseUser();
        user.setUsername(userName);
        user.setPassword(pwd);
        user.setEmail(email);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d(TAG, "signup successful");
                    // no need to keep the user logged in parseCache.
                    //So that 1st time when user logs in, ParseUser.getCurrentUser should return null
                    ParseUser.logOut();
                    callback.onSuccess(SIGNUP_ERROR_STATES.NO_ERROR);
                }else{
                    Log.d(TAG, "signup failed:e=" + e + " e.getMsg()=" + e.getMessage());
                    if(e.getMessage().equalsIgnoreCase("Username is taken")){
                        callback.onFailure(SIGNUP_ERROR_STATES.USERNAME_TAKEN);
                    }//Todo:check if email registered error
                }
            }
        });
    }

    @Override
    public void forgotPassword(final String email, final IResponseCallback<String,String>callback) {
        ParseUser user = new ParseUser();
        user.requestPasswordResetInBackground(email, new RequestPasswordResetCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    callback.onSuccess("Password Reset instructions sent to :"+email);
                }else{
                    callback.onFailure("Please try after sometime:"+e);
                }
            }
        });
    }

    @Override
    public void showErrorToUser(SIGNUP_ERROR_STATES errorState) {

    }
}
