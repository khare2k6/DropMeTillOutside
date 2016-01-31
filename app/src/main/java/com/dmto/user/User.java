package com.dmto.user;

import android.content.Context;
import android.location.Location;

import com.dmto.utilities.TextUtils;
import com.dmto.utilities.Utils;

/**
 * Created by ankitkha on 11-Dec-15.
 */
public class User /*implements Parcelable*/{
    private String mName;
    private String mEmail;
    private String mPassword;
    private String mUserName;
    private String mPhoneNumber;
    //Name of the user's employer
    private String mCompanyName;
    //One company can be present in multiple techparks
    private String mTechParkName;



    private String mVehicleNumber;
    private String mCompanyDomainName;
    //In case of parse like server where delivering of messages to clients is based on subscrption
    // to channels . Below field refer the personal communication channel of this user.
    // eg userName@companyName@TechParkName
  //  private String mPersonalChannelForCommunication;
    //In case of parse like server where delivering of messages to clients is based on subscrption
    // to channels . Below field refer the common communication channel i.e. group of users are listening to .
    // eg pedestrians@aricentJPMorganTechPark
//    private String mCommonChannelForCommunication;
    private OfficeLocation mOfficeLocation;
    private DMTO_CATEGORY mVehicleCategory;
    private ILoginLogoutUser.SIGNIN_STATES mUserCurrentState;
    //Todo:User image has to be accomodated.
    //private static PreferenceManager mPreferences;
    //private static User mInstance ;

//    /**
//     * Instance of logged in user
//     * @param context
//     * @return
//     */
//    public static User getmInstance(Context context) {
//        if( mInstance == null){
//            mPreferences = PreferenceManager.getmInstance(context);
//            if (!TextUtils.isEmpty(mPreferences.getUserEmail())) {
//                mInstance = new User(mPreferences.getUserEmail(), mPreferences.getUserPassword(), mPreferences.getUserName());
//                mInstance.setmCompanyName(mPreferences.getCompanyName());
//                mInstance.setmCompanyDomainName(mPreferences.getCompanyDomain());
//            }
//        }
//        return mInstance;
//    }
//
//    /**
//     * When user logs in for the first time , use this API, these will be stored in preferences for
//     * future use.
//     *
//     * @param context
//     * @param email
//     * @param pwd
//     * @param uName
//     * @return
//     */
//    public static User getmInstance(Context context, String email, String pwd, String uName) {
//        if (mInstance == null) {
//            mInstance = new User(email, pwd, uName);
//        }
//
//        //Set these values in preferences
//        mPreferences = PreferenceManager.getmInstance(context);
//        if (TextUtils.isEmpty(mPreferences.getUserEmail())) {
//            mPreferences.setUserEmail(email);
//            mPreferences.setUserName(uName);
//            mPreferences.setUserPassword(pwd);
//            mPreferences.setCompanyDomain(Utils.getCompanyDomain(email));
//            mPreferences.setCompanyName(Utils.getCompanyName(email));
//        }
//        return mInstance;
//    }


        /**
         * Constructor with minimal required value
         * @param email
         * @param pwd
         * @param userName
         */
    public User(String email, String pwd, String userName){
        this.mEmail= email;
        this.mPassword = pwd;
        this.mUserName = userName;
        this.mCompanyName = Utils.getCompanyName(email);
        this.mCompanyDomainName = Utils.getCompanyDomain(email);
    }

    public void setmCompanyDomainName(String companyDomainName) {
        this.mCompanyDomainName = companyDomainName;
    }

    /**
     * This user is a pedestrian OR has a vehicle.
     * Todo:Later this can be modified to two wheeler and four wheeler option as well
     */
    public enum DMTO_CATEGORY {
        NO_VEHICLE("NO_VEHICLE"),
        BIKE("BIKE"),
        CAR("CAR");

        private String mName;

        private DMTO_CATEGORY(String name) {
            mName = name;
        }

        public String getName() {
            return mName;
        }

    }
    /**
     * Removing dependency from android Location's class
     */
    private class OfficeLocation {
        private double mLatitude;
        private double mLongitude;

        public OfficeLocation(double latitude, double longitude) {
            this.mLatitude = latitude;
            this.mLongitude = longitude;
        }

        public double getmLatitude() {
            return mLatitude;
        }

        public double getmLongitude() {
            return mLongitude;
        }
    }

    /*********************************************
     * Getters and Setters
     * *******************************************/
    //Getters
    public String getmUserName() {
        return mUserName;
    }

    public String getmPassword() {
        return mPassword;
    }

    public String getmEmail() {
        return mEmail;
    }

    public String getmName() {
        return mName;
    }

    public DMTO_CATEGORY getmVehicleCategory() {return mVehicleCategory;}

    public ILoginLogoutUser.SIGNIN_STATES getmUserCurrentState() {
        return mUserCurrentState;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public String getmCompanyName() {
        return mCompanyName;
    }

    public String getmVehicleNumber() {return mVehicleNumber; }

    public OfficeLocation getmOfficeLocation() {
        return mOfficeLocation;
    }
    public String getmTechParkName() {
        return mTechParkName;
    }

    public void setmTechParkName(String mTechParkName) {
        this.mTechParkName = mTechParkName;
//        mPreferences.setTechParkName(mTechParkName);
    }
//    /**
//     * Formulate personal channel name for this user
//     * eg userNameCompanyDomainTechParkName
//     * @return channel name
//     */
//    public String getPersonalCommunicationChannel(){
//        String channel = null;
//        if (!TextUtils.isEmpty(mCompanyDomainName) && !TextUtils.isEmpty(mTechParkName)) {
//            channel = mUserName + mCompanyDomainName + mTechParkName;
//        }
//        return channel;
//    }


//    /**
//     * The channel at which request will be sent by this user, i.e. to the channel at which
//     * other type of user is listening for new request
//     * @return
//     */
//    public String getRequestChannel(){
//        String channel = null;
//        String vehicleType = null;
//
//        switch (mVehicleCategory) {
//            case BIKE:
//            case CAR:
//                vehicleType = "pedestrians";
//                break;
//            default:
//                vehicleType = "vehicleOwners";
//        }
//
//        if (!TextUtils.isEmpty(mCompanyDomainName) && !TextUtils.isEmpty(mTechParkName)) {
//            channel = vehicleType + mCompanyDomainName + mTechParkName;
//        }
//        return channel;
//    }


//    /**
//     * Get common communication channel name for pedestrians OR vehicle owners
//     * vehicleOwneersCompanyDomainTechParkName
//     * eg vehicleOwneersaricentjpmorgantechpark OR pedestriansaricentjpmorgantechpark
//     * @return groupCommonSubscription channel
//     */
//    public String getCommonCommunicationChannel(){
//        String channel = null;
//        String vehicleType = null;
//
//        switch (mVehicleCategory) {
//            case BIKE:
//            case CAR:
//                vehicleType = "vehicleOwners";
//                break;
//            default:
//                vehicleType = "pedestrians";
//        }
//
//        if (!TextUtils.isEmpty(mCompanyDomainName) && !TextUtils.isEmpty(mTechParkName)) {
//            channel = vehicleType + mCompanyDomainName + mTechParkName;
//        }
//        return channel;
//    }
    //Setters
    public void setmCompanyName(String companyName) {
        this.mCompanyName = companyName;
    }

    public void setmOfficeLocation(double latitude,double longitude) {
        this.mOfficeLocation = new OfficeLocation(latitude, longitude);
//        mPreferences.setOfficeLocation(this.mOfficeLocation);
    }

    public void setmVehicleCategory(DMTO_CATEGORY mVehicleCategory,String vechicleNumber) {
        this.mVehicleCategory = mVehicleCategory;
        if (!mVehicleCategory.equals(DMTO_CATEGORY.NO_VEHICLE)) {
            mVehicleNumber = vechicleNumber;
//            mPreferences.setVehicleNumber(vechicleNumber);
        }
    }

    public void setmUserCurrentState(ILoginLogoutUser.SIGNIN_STATES mUserCurrentState) {
        this.mUserCurrentState = mUserCurrentState;
    }
}

