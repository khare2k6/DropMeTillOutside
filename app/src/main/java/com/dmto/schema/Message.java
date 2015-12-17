package com.dmto.schema;

/**
 * Created by ankitkha on 16-Dec-15.
 */
public class Message {
    //Sender user name
    private String mUserName;
    private String mMessage;
    private MessageType mMessageType;
    //In case of location update message, sender's updated latitude
    private String mLatitude;
    //In case of location update message, sender's updated longitude
    private String mLongitude;
    // New request to be sent
    private Request mRequest;
    // Response for some request to be sent
    private Response mResponse;

    /**
     * Constructor when ANNOUCEMENT or CHAT message has to be sent
     * @param userName
     * @param msg
     * @param type
     * @param lat
     * @param longitude
     */
    public Message(String userName, String msg, String type, String lat, String longitude) {
        mUserName = userName;
        mMessage = msg;
        mMessageType = MessageType.valueOf(type);
        mLatitude = lat;
        mLongitude = longitude;
    }

    /**
     * Constructor when REQUEST has to be sent.
     * @param request
     */
    public Message(Request request,Response response,MessageType type) {
        mMessageType = type;
        switch (type) {
            case REQUEST:
                mRequest = request;
                break;

            case RESPONSE:
                mResponse = response;
                break;
        }
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
