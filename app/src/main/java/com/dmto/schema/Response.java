package com.dmto.schema;

import android.text.TextUtils;

import com.dmto.utilities.Utils;

import org.w3c.dom.Text;

/**
 * Created by ankitkha on 15-Dec-15.
 */
public class Response {
    //Request ID for which this is response for
    private String mRequestId;
    //Uniquely distinguishes this response
    private String mResponseId;
    //User object of this user(vehicle owner)who is about to send response to above requestId
    private User mVehicleOwnerUser;

     /*// Company name of this Vehicle Owner user
     // Todo:As of now this has to be same as requesting user's company, later should be changed.
    private String mCompanyname;

    // At least last 4 digit of the number so that requester can identify.
    private String mVehicleNumber;
    //Whether requester should look for bike or car
    private String mVehicleType;

    public User.DMTO_CATEGORY getVehicleType(){
        return mVehicleOwnerUser.getmVehicleCategory();
    }*/

    /**
     * Gets unique response ID generated from Responder's user object
     * @return
     */
    public String getResponseId() {
        if (mResponseId == null && mVehicleOwnerUser != null) {
            mResponseId = Utils.generateAndGetUniqueId(mVehicleOwnerUser);
        }
        return mResponseId;
    }
    /**
     * Check some important parameters in response object
     * @return true if its valid request
     */
    public boolean validateResponse() {
        boolean isValidResponse = false;
        Request req = Request.getActiveRequest();

        if (
        //Company name of responder and requestor should be same.
        //Todo:This condition of same company for requester and responder has to be removed later
             req.getRequesterCompanyName().equalsIgnoreCase(mVehicleOwnerUser.getmCompanyName())
        //Responder should not belong to NO_VEHICLE category
             && mVehicleOwnerUser.getmVehicleCategory() != User.DMTO_CATEGORY.NO_VEHICLE
        //Vehicle number should not be empty,atleast 4 digit number should be there
             && !TextUtils.isEmpty(mVehicleOwnerUser.getmVehicleNumber())) {
            isValidResponse = true;
        }
        return isValidResponse;
    }
    /**
     * Returns the User information of the person who responded for the request.
     * @return User object of responder
     */
    public User getResponder() {
        return mVehicleOwnerUser;
    }
}
