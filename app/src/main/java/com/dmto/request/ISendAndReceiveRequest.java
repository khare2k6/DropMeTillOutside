package com.dmto.request;

import com.dmto.user.User;

/**
 * Created by ankitkha on 22-Dec-15.
 */
public interface ISendAndReceiveRequest {
    /**
     * Send new {@link Request} to server so that it can be delivered to people who are
     * listening for / registered for  other kind of category({@link User.DMTO_CATEGORY})
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
     * User will call this api to subscribe for getting requests from pedestrians/car owners for
     * specific period of time.
     * @param user
     * @param timeExpiry
     */
    //public void subscribeForRequestListeners(User user, long timeExpiry);

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
