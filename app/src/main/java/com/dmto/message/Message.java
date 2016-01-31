package com.dmto.message;

import com.dmto.acknowlwdgement.Acknowledgement;
import com.dmto.request.Request;
import com.dmto.response.Response;

/**
 * Created by ankitkha on 16-Dec-15.
 */
public class Message {
    //Sender user name
    private String mSenderUserName;
    private String mReceiverUserName;
    private String mMessage;
    //Some of the implementation might require channelName or URL for messages to be delivered.
    private String mChannelName;
    private MessageType mMessageType;
    //In case of location update message, sender's updated latitude
    private String mLatitude;
    //In case of location update message, sender's updated longitude
    private String mLongitude;
    // New request to be sent
    private Request mRequest;
    // Response for some request to be sent
    private Response mResponse;

    private Acknowledgement mAck;

    /**
     * Constructor when ANNOUCEMENT or CHAT message has to be sent
     * @param userName
     * @param msg
     * @param type
     * @param lat
     * @param longitude
     */
    public Message(String userName, String msg, String type, String lat, String longitude) {
        mSenderUserName = userName;
        mMessage = msg;
        mMessageType = MessageType.valueOf(type);
        mLatitude = lat;
        mLongitude = longitude;
    }

    public Message(Request request) {
        mSenderUserName = request.getRequesterUserName();
        mMessageType = MessageType.REQUEST;
        mRequest = request;
    }

    public Message(Response response) {
        mSenderUserName = response.getResponder().getmUserName();
        mMessageType = MessageType.RESPONSE;
        mResponse = response;
    }



    public Message(Acknowledgement ack) {
        mAck = ack;
        mMessageType = MessageType.RESPONSE_ACK;
        mSenderUserName = ack.getResponse().getResponder().getmUserName();

    }

    public MessageType getmMessageType() {
        return mMessageType;
    }

    public Request getmRequest() {
        return mRequest;
    }

    public Response getmResponse() {
        return mResponse;
    }

    public Acknowledgement getmAck() {
        return mAck;
    }
    /**
     * Types of messages can be:
     * <br>
     * <li><b>Chat Message</b></li>
     * <li><b>Location Update Message</b></li>
     * <li><b>Announcement Message</b></li>
     */
    public enum MessageType {
        CHAT("CHAT"),
        LOCATION_UPDATE("LOCATION_UPDATE_MESSAGE"),
        ANNOUNCEMENT("ANNOUNCEMENT"),
        REQUEST("REQUEST"),
        RESPONSE("RESPONSE"),
        RESPONSE_ACK("RESPONSE_ACK");

        private String mName;

        private MessageType(String name) {
            mName = name;
        }

        public String getName() {
            return mName;
        }

    }
}
