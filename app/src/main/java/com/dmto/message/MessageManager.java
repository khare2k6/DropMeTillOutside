package com.dmto.message;

import com.dmto.acknowlwdgement.Acknowledgement;
import com.dmto.acknowlwdgement.ISendAndReceiveAcknowledgement;
import com.dmto.implementations.Factory;
import com.dmto.request.ISendAndReceiveRequest;
import com.dmto.request.Request;
import com.dmto.response.ISendReceiveResponse;
import com.dmto.response.Response;

/**
 * Created by ankitkha on 12-Jan-16.
 */
public class MessageManager implements ISendAndReceiveRequest,ISendAndReceiveAcknowledgement,ISendReceiveResponse {
    ISendAndReceiveMessage mParseMessageMgr = Factory.getSendReceiveMessagesImplementation(Factory.TYPE.PARSE);

    @Override
    public void sendAcknowledgement(Acknowledgement ack) {
        mParseMessageMgr.sendMessage(new Message(ack));
    }

    @Override
    public void receivedAcknowledgement(Acknowledgement ack) {

    }

    @Override
    public String sendRequest(Request request) {
        mParseMessageMgr.sendMessage(new Message(request));
        return null;
    }

    @Override
    public void receivedNewRequest(Request request) {

    }



    @Override
    public void sendResponse(Response response) {
        mParseMessageMgr.sendMessage(new Message(response));

    }

    @Override
    public void receivedResponse(Response response) {

    }


}
