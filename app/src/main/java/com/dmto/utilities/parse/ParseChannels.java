package com.dmto.utilities.parse;

import android.util.Log;

import com.dmto.request.Request;
import com.dmto.user.User;
import com.dmto.utilities.TextUtils;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.SaveCallback;

/**
 * Created by ankitkha on 08-Jan-16.
 */
public class ParseChannels {
    private String TAG = ParseChannels.class.getSimpleName();

    /**
     * The channel at which request will be sent by this user, i.e. to the channel at which
     * other type of user is listening for new request
     * @return
     */
    public static String getRequestChannel(User user){
        String channel = null;
        String vehicleType = null;

        switch (user.getmVehicleCategory()) {
            case BIKE:
            case CAR:
                vehicleType = "pedestrians";
                break;
            default:
                vehicleType = "vehicleOwners";
        }

        if (!TextUtils.isEmpty(user.getmCompanyName()) && !TextUtils.isEmpty(user.getmTechParkName())) {
            channel = vehicleType + user.getmCompanyName() + user.getmTechParkName();
        }
        return channel;
    }

    /**
     * Get common communication channel name for pedestrians OR vehicle owners
     * vehicleOwneersCompanyDomainTechParkName
     * eg vehicleOwneersaricentjpmorgantechpark OR pedestriansaricentjpmorgantechpark
     * @return groupCommonSubscription channel
     */
    public static String getCommonCommunicationChannel(User user){
        String channel = null;
        String vehicleType = null;

        switch (user.getmVehicleCategory()) {
            case BIKE:
            case CAR:
                vehicleType = "vehicleOwners";
                break;
            default:
                vehicleType = "pedestrians";
        }

        if (!TextUtils.isEmpty(user.getmCompanyName()) && !TextUtils.isEmpty(user.getmTechParkName())) {
            channel = vehicleType + user.getmCompanyName() + user.getmTechParkName();
        }
        return channel;
    }

    /**
     * Formulate personal channel name for this user
     * eg userNameCompanyDomainTechParkName
     * @return channel name
     */
    public static String getPersonalCommunicationChannel(User user){
        String channel = null;
        if (!TextUtils.isEmpty(user.getmCompanyName()) && !TextUtils.isEmpty(user.getmTechParkName())) {
            channel = user.getmUserName() + user.getmCompanyName() + user.getmTechParkName();
        }
        return channel;
    }



    public void subscribeForPersonalChannel(final User user) {
        //Todo:Do not enable the send request button unless subscribed to personal channel already.
        ParsePush.subscribeInBackground(ParseChannels.getPersonalCommunicationChannel(user), new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d(TAG, "subscribed to personal channel:" + ParseChannels.getCommonCommunicationChannel(user));

                }
            }
        });
    }


    public void subscribeForRequestListeners(final User user, long timeExpiry){
        ParsePush.subscribeInBackground(ParseChannels.getCommonCommunicationChannel(user), new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d(TAG, "subscribed to common channel " + ParseChannels.getCommonCommunicationChannel(user));
                }
            }
        });
    }
}
