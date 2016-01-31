package com.dmto.request;

import android.os.SystemClock;
import android.util.Log;

import com.dmto.acknowlwdgement.Acknowledgement;
import com.dmto.implementations.Factory;
import com.dmto.message.ISendAndReceiveMessage;
import com.dmto.message.Message;
import com.dmto.response.Response;
import com.dmto.utilities.DmtoException;

import java.util.ArrayList;

/**
 * Created by ankitkha on 28-Dec-15.
 */
public class RequestResponseManager {
    private static final String TAG = RequestResponseManager.class.getSimpleName();
    //A pedestrian can send only 1 request at a tim
    private Request mSentRequest;
    //A car owner can receive mutiple requests
    private ArrayList<Request> mReceivedRequestsList;
    private ArrayList<Response> mResponsesSentList;
    private ArrayList<Response> mReceivedResponsesList;
    private ArrayList<Request.INewRequestListener> mNewRequestListenersList;
    private ArrayList<Response.INewResponseReceivedListener> mResponseReceivedListenersList;
    private ArrayList<Acknowledgement.IAcknowledgementReceivedListner> mAckListnerList;

    private ISendAndReceiveMessage mSendReceiveMsgs;

    private static RequestResponseManager mInstance;

    private RequestResponseManager() {
        //there can be only 1 request a person can send at a time
        mSentRequest = null;
        //there can be any number of requests received by a user(vehicle owners)
        mReceivedRequestsList = new ArrayList<Request>();
        // at max, in a car, 3 pedestrians can be accomodated
        mResponsesSentList = new ArrayList<Response>(3);
        mSendReceiveMsgs = Factory.getSendReceiveMessagesImplementation(Factory.TYPE.PARSE);
        mNewRequestListenersList = new ArrayList<Request.INewRequestListener>();
        mResponseReceivedListenersList = new ArrayList<Response.INewResponseReceivedListener>();
        mReceivedResponsesList = new ArrayList<Response>();
        mAckListnerList = new ArrayList<Acknowledgement.IAcknowledgementReceivedListner>();
    }

    public static RequestResponseManager getInstance() {
        if (mInstance == null) {
            mInstance = new RequestResponseManager();
        }
        return mInstance;
    }

    /************************************************/
    /*    Listeners registration and removal       */
    /************************************************/
    public void registerForNewReceivedRequests(Request.INewRequestListener listener) {
        mNewRequestListenersList.add(listener);
    }

    public void removeFromNewReceivedRequestListener(Request.INewRequestListener listener) {
        mNewRequestListenersList.remove(listener);
    }

    public void registerForNewReceivedResponses(Response.INewResponseReceivedListener listener) {
        mResponseReceivedListenersList.add(listener);
    }


    public void removeFromNewReceivedResponseListener(Response.INewResponseReceivedListener listener) {
        mResponseReceivedListenersList.remove(listener);
    }

    public void registerForAckReceivedListener(Acknowledgement.IAcknowledgementReceivedListner listener) {
        mAckListnerList.add(listener);
    }

    public void removeFromNewReceivedResponseListener(Acknowledgement.IAcknowledgementReceivedListner listener) {
        mAckListnerList.remove(listener);
    }

    /****************************************/
    /*   Acknowledgement related APIs      */
    /***************************************/
    public void sendAcknowledgement(Acknowledgement acknowledgement) {
        mSendReceiveMsgs.sendMessage(new Message(acknowledgement));
    }

    public void receivedAcknowledgement(Acknowledgement acknowledgement) {
        for(Acknowledgement.IAcknowledgementReceivedListner listner:mAckListnerList){
            listner.onAckReceived(acknowledgement);
        }
    }

    /****************************************/
    /*         Response related APIs        */
    /***************************************/
    public void sendResponse(Response response) {
        if (!mResponsesSentList.contains(response)) {
            mResponsesSentList.add(response);
            mSendReceiveMsgs.sendMessage(new Message(response));
        } else {
            Log.d(TAG, "Response is already sent");
        }
    }

    public void receivedResponse(Response response) {
        if (!mReceivedResponsesList.contains(response)) {
            mReceivedResponsesList.add(response);

            for (Response.INewResponseReceivedListener listner : mResponseReceivedListenersList) {
                listner.onNewResponseReceived(response);
            }
        }
    }

    public void clearResponse(Response res) {
        mResponsesSentList.remove(res);
    }

    public void removeAllResponses() {
        mResponsesSentList.clear();
    }

    /****************************************/
    /*           Request related APIs      /
    /****************************************/
    public void receivedNewRequest(Request request) {
        if(!mReceivedRequestsList.contains(request))
            mReceivedRequestsList.add(request);
        for (Request.INewRequestListener listner : mNewRequestListenersList) {
            listner.newRequestReceived(request);
        }
    }

    public void deleteRequestFromReceivedRequestsList(Request request) {
        if (mReceivedRequestsList.contains(request)) {
            mReceivedRequestsList.remove(request);
        }
    }

    public void removeAllReceivedRequests() {
        mReceivedRequestsList.clear();
    }

    public ArrayList<Request>getAllReceivedRequests() {
        return mReceivedRequestsList;
    }

    /**
     * Broadcast this request
     *
     * @param request
     * @throws DmtoException, in case there is already any active request
     */
    public void sendNewRequest(Request request) throws DmtoException {
        if (mSentRequest != null) {
            throw new DmtoException("Last sent request is still active");
        }
        mSentRequest = request;
        mSendReceiveMsgs.sendMessage(new Message(mSentRequest));
    }

    public Request getActiveRequest() {
        return mSentRequest;
    }

    public boolean isLastSentRequestActive() {
        return (mSentRequest != null) ? true : false;
    }

    public void deleteLastSentRequest() {
        mSentRequest = null;
    }

    /**
     * Checks and deletes the request if request's timeout has happened
     *
     * @return , true if request has expired.
     */
    public boolean lastRequestExpired() {
        boolean isRequestExpired = false;
        if (mSentRequest != null && SystemClock.currentThreadTimeMillis() > mSentRequest.getExpiry()) {
            Log.d(TAG, "lastRequestExpired(): request has expired,deleting it.");
            deleteLastSentRequest();
            isRequestExpired = true;
        }
        return isRequestExpired;
    }

}
