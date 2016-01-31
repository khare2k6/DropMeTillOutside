package com.dmto.utilities;

/**
 * Created by ankitkha on 26-Dec-15.
 */
public class TextUtils {
    public static boolean isEmpty(String str) {
        boolean isEmpty = true;

        if (str != null && !str.equalsIgnoreCase("")) {
            isEmpty = false;
        }
        return isEmpty;
    }
}
