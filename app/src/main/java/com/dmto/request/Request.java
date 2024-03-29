package com.dmto.request;

import android.content.Context;
import android.os.SystemClock;

import com.dmto.user.User;
import com.dmto.utilities.Utils;

/**
 * Created by ankitkha on 14-Dec-15.
 */
public class Request {

    /**
     * Till where user is going ,for now ,mostly hardcoded to OUTSIDE meaning outside of techpark
     * Todo:Change this to Location Object of drop of location and should be configurable.
     */
    private String mDestinationArea;
    /**
     * For how long this request is valid. i.e.How much time user takes to usually get out
     * tech park by whichever way he is commuting.
     * Todo:Request should cancelled after this much time and app should close automatically after this time expires.
     */
    private long mRequestExpiryTime;
    private long mRequestStartTime;
    private User mRequester;

   /* //Identifies if request is from pedestrian or vehicle owner
    private User.DMTO_CATEGORY mRequestCategory;
    //User's current location
    private Location mUserLocation;
    private String mUserName;*/

    private String mRequestId;


    public Request(User user, String destination, long time) {
        mRequester = user;
        mDestinationArea = destination;
        mRequestExpiryTime = time;
        mRequestId = Utils.generateAndGetUniqueId(mRequester);
        mRequestStartTime = SystemClock.currentThreadTimeMillis();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        if (mRequestExpiryTime != request.mRequestExpiryTime) return false;
        if (mRequestStartTime != request.mRequestStartTime) return false;
        if (!mDestinationArea.equals(request.mDestinationArea)) return false;
        if (!mRequester.equals(request.mRequester)) return false;
        return mRequestId.equals(request.mRequestId);

    }

    @Override
    public int hashCode() {
        int result = mDestinationArea.hashCode();
        result = 31 * result + (int) (mRequestExpiryTime ^ (mRequestExpiryTime >>> 32));
        result = 31 * result + (int) (mRequestStartTime ^ (mRequestStartTime >>> 32));
        result = 31 * result + mRequester.hashCode();
        result = 31 * result + mRequestId.hashCode();
        return result;
    }
    //    /*
//     * Creates and returns active request object. By this time , user object should be created and
//     * stored in preferences. Throws NPE if not so.
//     * @param context
//     * @param destination Where user wants to go OR heading to
//     * @param time
//     * @return Request object
//     * @throws NullPointerException
//     */
//    public static Request getNewRequest(Context context, String destination, long time) throws NullPointerException{
//        if (mInstance == null) {
//            User user = User.getmInstance(context);
//            if (user == null) {
//                throw new NullPointerException("User object is null.From request creation api");
//            }
//            mInstance = new Request(user,destination,time);
//        }
//        return mInstance;
//    }

    public String getDropOffLocation() {
        return mDestinationArea;
    }

    /**
     * Get category of this request.
     * @return {@link User.DMTO_CATEGORY}
     */
    public User.DMTO_CATEGORY getDmtoCategoryType() {
        return mRequester.getmVehicleCategory();
    }

    public String getmRequestId() {
        return mRequestId;
    }

    public String getRequesterCompanyName() {
        return mRequester.getmCompanyName();
    }
//    /**
//     * Generates a unique requestId for this request
//     * @return
//     */
//    private String generateAndGetRequestId() {
//        String reqId = null;
//        if (mRequester != null) {
//            reqId = mRequester.getmEmail() + String.valueOf(SystemClock.currentThreadTimeMillis());
//        }
//        return reqId;
//    }

    /**
     * Communcation channel name for this user where responder can send message to requester
     * @return CommunicationChannel name which is same as request Id as of now.
     */
    public String getCommunicationChannel() {
        return mRequestId;
    }

    /**
     * After receiving a response, compare if response is for the same request which was sent.
     * @param resId
     * @return true is response if for the pending request

    public boolean isValidResponse(String resId) {
        boolean isValid = false;
        if (!TextUtils.isEmpty(resId) && !TextUtils.isEmpty(mRequestId)) {
            isValid = resId.equalsIgnoreCase(mRequestId);
        }
        return isValid;
    }*/
    /**
     * Get initiator's user name
     * @return
     */
    public String getRequesterUserName() {
        return mRequester.getmUserName();
    }

    public User getRequester() {
        return mRequester;
    }
    /**
     * Returns time(in min) remaining till expiry of this request.
     * @return
     */
    public long getExpiry() {
        long timePassedSinceRequestStart = SystemClock.currentThreadTimeMillis() - mRequestStartTime;
        return mRequestExpiryTime - timePassedSinceRequestStart;
    }

    /**
     * UI component to show new request received
     */
    public interface INewRequestListener {
        public void newRequestReceived(Request request);
    }

}
