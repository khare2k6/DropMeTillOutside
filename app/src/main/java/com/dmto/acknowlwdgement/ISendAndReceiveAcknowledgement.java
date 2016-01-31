package com.dmto.acknowlwdgement;

/**
 * Created by ankitkha on 12-Jan-16.
 */
public interface ISendAndReceiveAcknowledgement {
    /**
     * User might receive multiple responses for his request, he needs to select one of them,
     * acknowledge it and be in session with one responder
     * @param ack
     */
    public void sendAcknowledgement(Acknowledgement ack);

    /**
     * When requester accept response of this user, this user and requester should
     * now be in a session in which they can chat and have a map view.
     */
    public void receivedAcknowledgement(Acknowledgement ack);
}
