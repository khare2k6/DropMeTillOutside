package com.dmto.server;

import android.content.Context;

import com.dmto.DmtoApplication;
import com.dmto.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ankitkha on 18-Dec-15.
 */
public class SubscriptionManager implements ISubscriptionRegistration {
    private List<String> mChannelList;
    private static SubscriptionManager mInstance;
    private String mCommonChannel,mPersonalChannel;
    private final String TAG = SubscriptionManager.class.getSimpleName();

    public SubscriptionManager getInstance(Context context) {
        if (mInstance == null) {
            User user = DmtoApplication.getUser();
            if(user == null) throw new NullPointerException(TAG + "in getInstance of subscription" +
                    "manager, user is null");
            mInstance = new SubscriptionManager();
        }
        return mInstance;
    }

    private SubscriptionManager() {
        mChannelList = new ArrayList<String>();
    }

    @Override
    public void registerForPersonalChannel(Context context) {
         //subscribe to this channenl here
    }

    @Override
    public void unregisterFromPersonalChannel(Context context) {

    }

    @Override
    public void registerForCommonChannel(Context context) {

    }

    @Override
    public void unregisterFromCommonChannel(Context context) {

    }
}
