package com.dmto.server;

import com.dmto.schema.Message;

/**
 * Created by ankitkha on 16-Dec-15.
 */
public interface ISendAndReceiveMessage {
    public void sendMessage(Message msg);
}
