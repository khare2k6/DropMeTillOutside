package com.dmto.server;

import android.content.Context;

/**
 * This interface defines APIs required for subscribing for receiving
 * communication / messages from server and other devices
 * Todo:This might be only required for Parse?
 * Created by ankitkha on 18-Dec-15.
 */
public interface ISubscriptionRegistration {
    public void registerForPersonalChannel(Context context);
    public void unregisterFromPersonalChannel(Context context);
    public void registerForCommonChannel(Context context);
    public void unregisterFromCommonChannel(Context context);



}
