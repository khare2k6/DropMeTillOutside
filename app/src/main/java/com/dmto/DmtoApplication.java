package com.dmto;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Parcelable;

import com.dmto.user.User;
import com.dmto.utilities.DmtoException;
import com.dmto.utilities.PreferenceManager;
import com.dmto.utilities.TextUtils;
import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by ankitkha on 23-Dec-15.
 */
public class DmtoApplication extends Application {
    private static final String APP_ID = "CHYALF7qNB2DQ4tqVu86eeMhPLrMYmvmmxL3VyBO";
    private static final String CLIENT_KEY= "Lw5e3fZgH5E0jRQxVx5KOIDSQNfoxCkxKt7N5OiN";
    private static User mUser = null;
    private PreferenceManager mPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, APP_ID, CLIENT_KEY);

        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.saveInBackground();
        mPreferences = PreferenceManager.getmInstance(this);
        if (!TextUtils.isEmpty(mPreferences.getUserName())) {
            mUser = new User(mPreferences.getUserEmail(),mPreferences.getUserPassword(),mPreferences.getUserName());
            if(!TextUtils.isEmpty(mPreferences.getCompanyName())) mUser.setmCompanyName(mPreferences.getCompanyName());
            if(!TextUtils.isEmpty(mPreferences.getTechParkName())) mUser.setmTechParkName(mPreferences.getTechParkName());
        }

    }

    public static User getUser() {
        return mUser;
    }

    public static void setmUser(User user) throws DmtoException {
        if (mUser == null) {
            mUser = user;
        }else{
            throw new DmtoException("User is not null:" + mUser.getmUserName() + " still setUser with user:" + user.getmEmail() + " called");
        }
    }
}
