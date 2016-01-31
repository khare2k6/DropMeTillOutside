package com.dmto.utilities;

import android.os.SystemClock;



import com.dmto.user.User;

/**
 * Created by ankitkha on 17-Dec-15.
 */
public class Utils {
    public static final int BEFORE_DELIMITER= 0;
    public static final int AFTER_DELIMITER = 1;
    public static final String AT_THE_RATE_SYMBOL = "@";
    public static final String DOT = "\\." ;

    /**
     * Generates unique Id using details of user object
     *
     * @param user
     * @return unique ID string
     */
    public static String generateAndGetUniqueId(User user) {
        String reqId = null;
        if (user != null && !TextUtils.isEmpty(user.getmEmail())) {
            reqId = user.getmEmail() + String.valueOf(SystemClock.currentThreadTimeMillis());
        }
        return reqId;
    }

    public static String getCompanyDomain(String email) {
        String companyDomainName = null;
        try {
            if (!TextUtils.isEmpty(email)) {
                String[] arr = email.split(AT_THE_RATE_SYMBOL);
                if ( TextUtils.isEmpty(arr[AFTER_DELIMITER])) {
                    return companyDomainName;
                }
                companyDomainName = arr[AFTER_DELIMITER];
            }
        }catch (ArrayIndexOutOfBoundsException e){

        }
        return companyDomainName;
    }

    public static String getCompanyName(String email) {
        String companyDomain = getCompanyDomain(email);
        String companyName = null;
        if (companyDomain != null) {
            String[] arr = companyDomain.split(DOT);
            companyName = arr[BEFORE_DELIMITER];
        }
        return companyName;
    }

    public static String getUsernameFromEmail(String email) {
        String uname = null;
        try {
            if (!TextUtils.isEmpty(email)) {
                String[] arr = email.split(AT_THE_RATE_SYMBOL);
                if ( !TextUtils.isEmpty(arr[BEFORE_DELIMITER])) {
                    uname = arr[BEFORE_DELIMITER];
                }
            }
        }catch (ArrayIndexOutOfBoundsException e){

        }
        return uname;
    }
}
