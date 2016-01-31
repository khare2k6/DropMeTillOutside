package com.dmto.acknowlwdgement;

import com.dmto.request.Request;
import com.dmto.response.Response;
import com.dmto.utilities.Utils;

/**
 * Created by ankitkha on 09-Jan-16.
 * Acknowledgement class of 3 way handshake for confirming a session.
 */
public class Acknowledgement {
    private Request mRequest;
    private Response mResponse;
    private String mAckId;

    public Acknowledgement(Request request, Response response) {
        mRequest = request;
        mResponse = response;
    }

    public  Request getRequest() {
        return mRequest;
    }

    public Response getResponse(){
        return mResponse;
    }

    public interface IAcknowledgementReceivedListner {
        public void onAckReceived(Acknowledgement acknowledgement);
    }

}
