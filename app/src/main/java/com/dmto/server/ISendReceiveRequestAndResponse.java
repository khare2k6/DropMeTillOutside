package com.dmto.server;

import com.dmto.schema.Request;
import com.dmto.schema.Response;
import com.dmto.schema.User;

/**
 * Created by ankitkha on 14-Dec-15.
 */
public interface ISendReceiveRequestAndResponse {
    /**
     * Send new {@link Request} to server so that it can be delivered to people who are
     * listening for / registered for  other kind of category({@link com.dmto.schema.User.DMTO_CATEGORY})
     * @param request
     * @return if successfully created and delivered request then unique request ID is returned
     * or in case of error one of {@link REQUEST_STATUS}
     */
    public String sendRequest(Request request);

    /**
     * Need to show to user some details about the new request being received
     * @param request
     */
    public void receivedNewRequest(Request request);

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

    /**
     * User might receive multiple responses for his request, he needs to select one of them,
     * acknowledge it and be in session with one responder
     * @param response
     */
    public void acknowledgeResponse(Request request,Response response);

    /**
     * User will call this api to subscribe for getting requests from pedestrians for
     * specific period of time.
     * @param user
     * @param timeExpiry
     */
    public void subscribeForPedestrianRequests(User user, long timeExpiry);

    /**
     * When requester accept response of this user, this user and requester should
     * now be in a session in which they can chat and have a map view.
     */
    public void acknowledgeReceived(Request request,Response response);

    /**
     * Enum defining possible states of a request / response delivery
     */
    public enum REQUEST_STATUS {
        NO_ERROR("NO_ERROR"),
        NETWORK_ERROR("NETWORK_ERROR"),
        SERVER_ERROR("SERVER_ERROR"),
        INVALID_REQUEST("INVALID_REQUEST");

        private String mName;

        private REQUEST_STATUS(String name) {
            mName = name;
        }

        public String getName() {
            return mName;
        }
    }
}
