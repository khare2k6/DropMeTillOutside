package com.dmto.message;

import com.dmto.message.Message;

/**
 * Created by ankitkha on 16-Dec-15.
 */
public interface ISendAndReceiveMessage {
    public void sendMessage(Message msg);
    public void receiveMessage(Message msg);

    /**
     * When 3-4 people involved in the session will be listening to a common channel,
     * parse server will send messages sent by them to themeselves as well. User need
     * to discard such message
     */
    public void discardMessageFromSelf();
}
