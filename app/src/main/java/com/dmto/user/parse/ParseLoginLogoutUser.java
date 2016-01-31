package com.dmto.user.parse;

import android.content.Context;
import android.util.Log;

import com.dmto.DmtoApplication;
import com.dmto.server.IResponseCallback;
import com.dmto.user.ILoginLogoutUser;
import com.dmto.user.User;
import com.dmto.utilities.DmtoException;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Created by ankitkha on 24-Dec-15.
 */
public class ParseLoginLogoutUser implements ILoginLogoutUser {
    private static final String TAG = ParseAuthenticateUser.class.getSimpleName();
    private ParseUser mParseUser;
    //Todo:Can lead to memory leak?
    private Context mContext;

    public ParseLoginLogoutUser(Context context) {
        mParseUser = ParseUser.getCurrentUser();
        mContext = context;
    }

    @Override
    public void loginUser(final String uname, final String pwd, final IResponseCallback<SIGNIN_STATES, SIGNIN_STATES> callback) {
        if (mParseUser == null) {
            ParseUser.logInInBackground(uname, pwd, new LogInCallback() {
                @Override
                public void done(ParseUser parseUser, ParseException e) {
                    if (e == null && parseUser != null) {
                        Log.d(TAG, "Login successful for user:" + uname);
                        if (parseUser.isAuthenticated()) {
                            //This is first time login for user, create new User
                            User user = new User(parseUser.getEmail(), pwd, parseUser.getUsername());
                            try {
                                DmtoApplication.setmUser(user);
                                callback.onSuccess(SIGNIN_STATES.LOGIN_SUCCESSFUL);
                            } catch (DmtoException e1) {
                                e1.printStackTrace();
                                callback.onFailure(SIGNIN_STATES.SOMETHING_ELSE_WRONG);
                            }
                        }else{
                            callback.onFailure(SIGNIN_STATES.CODE_VALIDATION_PENDING);
                        }
                    }else if(parseUser == null){
                        callback.onFailure(SIGNIN_STATES.USERNAME_OR_PASSWORD_INVALID);
                    }else{
                        callback.onFailure(SIGNIN_STATES.NETWORK_ERROR);
                    }
                }
            });
        }else{
            if (mParseUser.isAuthenticated()) {
                callback.onSuccess(SIGNIN_STATES.LOGIN_SUCCESSFUL);
            }else{
                callback.onFailure(SIGNIN_STATES.CODE_VALIDATION_PENDING);
            }
        }

    }

    @Override
    public void logoutUser() {

    }

}
