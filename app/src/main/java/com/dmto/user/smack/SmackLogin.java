package com.dmto.user.smack;

import android.util.Log;

import com.dmto.server.IResponseCallback;
import com.dmto.user.ILoginLogoutUser;
import com.dmto.webservice.Constants;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.io.IOException;

/**
 * Created by ankitkha on 12-Feb-16.
 */
public class SmackLogin implements ILoginLogoutUser {
    private static final String TAG = SmackLogin.class.getSimpleName();

    @Override
    public void loginUser(String uname, String pwd, IResponseCallback<SIGNIN_STATES, SIGNIN_STATES> callback) {
//        XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
//                .setUsernameAndPassword(uname, pwd)
//                .setServiceName("dmto.com")
//                .setHost("192.168.1.102")
//                .build();
        AbstractXMPPConnection connection = Constants.getXmppConnection(uname, pwd);
        try {
            connection.connect();
        } catch (SmackException e) {
            Log.d(TAG, "smackException during connect():" + e);
        } catch (IOException e) {
            Log.d(TAG, "IOException during connect():" + e);
        } catch (XMPPException e) {
            Log.d(TAG, "XMPPException during connect():" + e);
        }

        try {
            connection.login();
        } catch (XMPPException e) {
            Log.d(TAG, "XMPPException during login():" + e);
        } catch (SmackException e) {
            Log.d(TAG, "SmackException during login():" + e);
        } catch (IOException e) {
            Log.d(TAG, "IOException during login():" + e);
        }

    }

    @Override
    public void logoutUser() {

    }
}
