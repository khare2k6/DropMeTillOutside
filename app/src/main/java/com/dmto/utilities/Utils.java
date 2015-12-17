package com.dmto.utilities;

import android.os.SystemClock;

import com.dmto.schema.User;

/**
 * Created by ankitkha on 17-Dec-15.
 */
public class Utils {
    /**
     * Generates unique Id using details of user object
     * @param user
     * @return unique ID string
     */
    public static String generateAndGetUniqueId(User user) {
        String reqId = null;
        if (user != null) {
            reqId = user.getmEmail() + String.valueOf(SystemClock.currentThreadTimeMillis());
        }
        return reqId;
    }
}
