package com.dmto.response;

import com.dmto.request.Request;

/**
 * Created by ankitkha on 14-Dec-15.
 */
public interface ISendReceiveResponse {

    /**
     * When a person from WITH_VEHICLE category has accepted request of a PEDESTRIAN.
     * Server will send a response
     * @param response
     */
    public void sendResponse(Response response);

    /**
     * Response received for the request we sent
     */
    public void receivedResponse(Response response);

}
