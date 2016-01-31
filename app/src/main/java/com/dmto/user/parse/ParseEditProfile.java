package com.dmto.user.parse;

import android.content.Context;

import com.dmto.DmtoApplication;
import com.dmto.user.IEditProfile;
import com.dmto.user.User;

/**
 * Created by ankitkha on 27-Dec-15.
 */
public class ParseEditProfile implements IEditProfile {

    @Override
    public void updateTechParkName(String techParkName, Context context) {
        User user = DmtoApplication.getUser();
        if (user != null) {
            user.setmTechParkName(techParkName);
        }

    }

}
