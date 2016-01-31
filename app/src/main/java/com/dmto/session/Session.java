package com.dmto.session;

import com.dmto.acknowlwdgement.Acknowledgement;

/**
 * Created by ankitkha on 11-Jan-16.
 */
public class Session {
    private Acknowledgement mAck;

    public Session(Acknowledgement acknowledgement) {
        mAck = acknowledgement;
    }

    public void stopSession() {
        //expire the request,clear response and ack here
    }
}
