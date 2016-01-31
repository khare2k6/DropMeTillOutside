package com.dmto.implementations;

import android.content.Context;

import com.dmto.message.ISendAndReceiveMessage;
import com.dmto.message.parse.ParseSendReceiveMessage;
import com.dmto.request.ISendAndReceiveRequest;

import com.dmto.user.ILoginLogoutUser;
import com.dmto.user.IUserSignup;
import com.dmto.user.parse.ParseLoginLogoutUser;
import com.dmto.user.parse.ParseUserSignup;

/**
 * Created by ankitkha on 23-Dec-15.
 */
public class Factory {
    public enum TYPE {
        PARSE("PARSE"),
        OTHER("OTHER");

        private String mName;

        private TYPE(String name) {
            mName = name;
        }

        public String getName() {
            return mName;
        }
    }

    public static IUserSignup getUserSignupImplementation(TYPE type) {
        switch (type) {
            case PARSE:
                return new ParseUserSignup();
            case OTHER:
                break;
        }
        return null;
    }



    public static ISendAndReceiveMessage getSendReceiveMessagesImplementation(TYPE type) {
        switch (type) {
            case PARSE:
                return new ParseSendReceiveMessage();
            case OTHER:
                break;
        }
        return null;
    }

    public static ILoginLogoutUser getLoginLogoutImplementation(TYPE type, Context context) {
        switch (type) {
            case PARSE:
                return new ParseLoginLogoutUser(context);
            case OTHER:
                break;
        }
        return null;
    }

    public static IUserSignup getSignupImplementation(TYPE type) {
        switch (type) {
            case PARSE:
                return new ParseUserSignup();
            case OTHER:
                break;
        }
        return null;
    }

}
