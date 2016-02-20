package com.dmto.user.smack;

import android.content.Context;
import android.util.Log;

import com.dmto.server.IResponseCallback;
import com.dmto.user.IUserSignup;
import com.dmto.webservice.Constants;
import com.dmto.webservice.HttpRequest;
import com.dmto.webservice.NetworkConnection;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 *
 * Created by ankitkha on 15-Feb-16.
 */
public class SmackSignup implements IUserSignup {
    private static final String TAG = SmackSignup.class.getSimpleName();
    private Context mContext;
    private AccountManager mSignupXmpp;

    public SmackSignup(Context context) {
        mContext = context;

    }
    @Override
    public void isUsernameAvailable(String uName, IResponseCallback<Boolean, String> callback) {

    }

    @Override
    public void newUserSignup(String userName, final String email, final String pwd, final IResponseCallback<SIGNUP_ERROR_STATES, SIGNUP_ERROR_STATES> callback) {
        JSONObject jObj = new JSONObject();
        try {
            jObj.put(Constants.PARAM_EMAIL, email);
            jObj.put(Constants.PARAM_PASSWORD, pwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpRequest.HttpRequestBuilder httpRequestBuilder = new HttpRequest.HttpRequestBuilder();
        httpRequestBuilder.setUrl(Constants.API_SIGNUP_NEW_USER)
                .setMethodType(HttpRequest.HttpMethod.POST)
                .setJsonRequestParams(jObj)
                .setCallback(new HttpRequest.IResponseCallBack() {
                    @Override
                    public void onFailure(String msg) {
                        Log.d(TAG, "error in signingup:"+msg);
                        callback.onFailure(SIGNUP_ERROR_STATES.SERVER_ERROR);
                    }

                    @Override
                    public void onSuccess(HashMap responseObjects) {
                        callback.onSuccess(SIGNUP_ERROR_STATES.NO_ERROR);
                        AbstractXMPPConnection connection = Constants.getXmppConnection(email, pwd);
                        mSignupXmpp = AccountManager.getInstance(connection);
                        try {
                            mSignupXmpp.createAccount(email, pwd);
                        } catch (SmackException.NoResponseException e) {
                            Log.d(TAG, "Signup with xmpp:No response exception");
                        } catch (XMPPException.XMPPErrorException e) {
                            Log.d(TAG, "Signup with xmpp:XMPPErrorException exception");
                        } catch (SmackException.NotConnectedException e) {
                            Log.d(TAG, "Signup with xmpp:NotConnectedException");
                        }
                    }
                });
        NetworkConnection.getInstance(mContext).addHttpJsonRequest(httpRequestBuilder.getNewHttpRequest());
    }

    @Override
    public void forgotPassword(String email, IResponseCallback<String, String> callback) {

    }

    @Override
    public void showErrorToUser(SIGNUP_ERROR_STATES errorState) {

    }
}
