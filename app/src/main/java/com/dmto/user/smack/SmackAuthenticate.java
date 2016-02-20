package com.dmto.user.smack;

import android.content.Context;
import android.util.Log;

import com.dmto.server.IResponseCallback;
import com.dmto.user.IAuthenticateUser;
import com.dmto.user.IUserSignup;
import com.dmto.webservice.Constants;
import com.dmto.webservice.HttpRequest;
import com.dmto.webservice.NetworkConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by ankitkha on 15-Feb-16.
 */
public class SmackAuthenticate implements IAuthenticateUser {

    private static final String TAG = SmackAuthenticate.class.getSimpleName();
    private final Context mContext;

    public SmackAuthenticate(Context context) {
        mContext = context;
    }

    @Override
    public AUTHENTICATION_ERRORS authenticateThisUser(final String myEmail, String code, final IAuthenticationResult callback) {
        JSONObject jObj = new JSONObject();
        try {
            jObj.put(Constants.PARAM_EMAIL, myEmail);
            jObj.put(Constants.PARAM_CODE, code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpRequest.HttpRequestBuilder httpRequestBuilder = new HttpRequest.HttpRequestBuilder();
        httpRequestBuilder.setUrl(Constants.API_AUTHENTICATE_NEW_USER)
                .setMethodType(HttpRequest.HttpMethod.POST)
                .setJsonRequestParams(jObj)
                .setCallback(new HttpRequest.IResponseCallBack() {
                    @Override
                    public void onFailure(String msg) {
                        Log.d(TAG, "error in authenticating this user:"+msg);
                        callback.onFailure(msg);
                    }

                    @Override
                    public void onSuccess(HashMap responseObjects) {
                        Log.d(TAG, "successful authentication for user :"+myEmail);
                        callback.onSuccess();
                    }
                });
        NetworkConnection.getInstance(mContext).addHttpJsonRequest(httpRequestBuilder.getNewHttpRequest());
        return null;
    }

    @Override
    public void resendEmailAuthentication(String email, IResponseCallback<String, String> callback) {

    }

    @Override
    public void showErrorToUser(AUTHENTICATION_ERRORS errorState) {

    }
}
