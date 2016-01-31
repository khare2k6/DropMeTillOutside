package com.dmto.user.parse;

import android.util.Log;

import com.dmto.server.IResponseCallback;
import com.dmto.user.IAuthenticateUser;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by ankitkha on 24-Dec-15.
 */
public class ParseAuthenticateUser implements IAuthenticateUser {

    private static final String TAG = ParseAuthenticateUser.class.getSimpleName() ;

    @Override
    public boolean getAuthenticationCodeToEmail(String userEmail) {
        // not required as parse, on registering with email,will send authentication email.
        return false;
    }

    @Override
    public AUTHENTICATION_ERRORS authenticateThisUser(String myEmail, String code) {
        // not required for parse implementaiton
        return null;
    }

    @Override
    public void resendEmailAuthentication(final String email, final IResponseCallback<String,String>callback) {
        ParseUser user = ParseUser.getCurrentUser();
        user.setEmail(email);
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d(TAG, "successfully udpated email, verification email must have been sent");
                    callback.onSuccess("Authenticated mail sent again to :" + email);
                }else{
                    Log.d(TAG, "some error while resending authentication email" + e);
                    callback.onFailure("Some error, please try later "+e);
                }
            }
        });
    }

    @Override
    public void showErrorToUser(AUTHENTICATION_ERRORS errorState) {

    }
}
