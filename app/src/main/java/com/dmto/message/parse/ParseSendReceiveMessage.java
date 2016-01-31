package com.dmto.message.parse;

import android.util.Log;

import com.dmto.message.ISendAndReceiveMessage;
import com.dmto.message.Message;
import com.dmto.user.parse.ParseConstants;
import com.dmto.utilities.parse.ParseChannels;
import com.google.gson.Gson;
import com.parse.ParseCloud;

import java.util.HashMap;

/**
 * Created by ankitkha on 12-Jan-16.
 */
public class ParseSendReceiveMessage implements ISendAndReceiveMessage {
    private static final String TAG = ParseSendReceiveMessage.class.getSimpleName();

    @Override
    public void sendMessage(Message msg) {
        String channel = null;
        String apiName = null;

        switch (msg.getmMessageType()) {
            case REQUEST:
                channel = ParseChannels.getRequestChannel(msg.getmRequest().getRequester());
                apiName = ParseConstants.API_NEW_REQUEST;
                break;

            case RESPONSE:
                channel =  ParseChannels.getPersonalCommunicationChannel(msg.getmResponse().getmRequest().getRequester());
                apiName = ParseConstants.API_NEW_RESPONSE;
                break;

            case RESPONSE_ACK:
                channel = ParseChannels.getPersonalCommunicationChannel(msg.getmAck().getResponse().getResponder());
                apiName = ParseConstants.API_ACK_RESPONSE;
                break;
        }
        
        Gson gson = new Gson();
        String jsonOfMessageToBeSent = gson.toJson(msg);
        Log.d(TAG, "request to be sent:" + jsonOfMessageToBeSent);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(ParseConstants.KEY_CHANNEL, channel);
        params.put(ParseConstants.KEY_MESSAGE, jsonOfMessageToBeSent);
        ParseCloud.callFunctionInBackground(apiName, params);
    }

    @Override
    public void receiveMessage(Message msg) {

    }

    @Override
    public void discardMessageFromSelf() {

    }

}
