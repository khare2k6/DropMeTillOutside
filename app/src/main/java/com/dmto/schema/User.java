package com.dmto.schema;

import android.content.Context;
import android.location.Location;
import android.text.TextUtils;

import com.dmto.user.IUserLogin;
import com.dmto.utilities.PreferenceManager;

/**
 * Created by ankitkha on 11-Dec-15.
 */
public class User /*implements Parcelable*/{
    private String mName;
    private String mEmail;
    private String mPassword;
    private String mUserName;
    private String mPhoneNumber;
    private String mCompanyName;
    private String mVehicleNumber;
    private Location mOfficeLocation;
    private DMTO_CATEGORY mVehicleCategory;
    private IUserLogin.SIGNIN_STATES mUserCurrentState;
    //Todo:User image has to be accomodated.
    private static PreferenceManager mPreferences;
    private static User mInstance ;

    /**
     * Instance of logged in user
     * @param context
     * @return
     */
    public static User getmInstance(Context context) {
        if( mInstance == null){
            mPreferences = PreferenceManager.getmInstance(context);
            mInstance = new User(mPreferences.getUserEmail(), mPreferences.getUserPassword(), mPreferences.getUserName());
        }
        return mInstance;
    }

    /**
     * When user logs in for the first time , use this API, these will be stored in preferences for
     * future use.
     *
     * @param context
     * @param email
     * @param pwd
     * @param uName
     * @return
     */
    public static User getmInstance(Context context, String email, String pwd, String uName) {
        if (mInstance == null) {
            mInstance = new User(email, pwd, uName);
        }

        //Set these values in preferences
        mPreferences = PreferenceManager.getmInstance(context);
        if (TextUtils.isEmpty(mPreferences.getUserEmail())) {
            mPreferences.setUserEmail(email);
            mPreferences.setUserName(uName);
            mPreferences.setUserPassword(pwd);
        }
        return mInstance;
    }
        /**
         * Constructor with minimal required value
         * @param email
         * @param pwd
         * @param userName
         */
    private User(String email, String pwd, String userName){
        this.mEmail= email;
        this.mPassword = pwd;
        this.mUserName = userName;
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

    public IUserLogin.SIGNIN_STATES getmUserCurrentState() {
        return mUserCurrentState;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public String getmCompanyName() {
        return mCompanyName;
    }

    public String getmVehicleNumber() {return mVehicleNumber; }

    public Location getmOfficeLocation() {
        return mOfficeLocation;
    }

    //Setters
    public void setmCompanyName(String mCompanyName) {
        this.mCompanyName = mCompanyName;
    }

    public void setmOfficeLocation(Location officeLocation) {
        this.mOfficeLocation = officeLocation;
        mPreferences.setOfficeLocation(this.mOfficeLocation);
    }

    public void setmVehicleCategory(DMTO_CATEGORY mVehicleCategory,String vechicleNumber) {
        this.mVehicleCategory = mVehicleCategory;
        if (!mVehicleCategory.equals(DMTO_CATEGORY.NO_VEHICLE)) {
            mVehicleNumber = vechicleNumber;
            mPreferences.setVehicleNumber(vechicleNumber);
        }
    }

    public void setmUserCurrentState(IUserLogin.SIGNIN_STATES mUserCurrentState) {
        this.mUserCurrentState = mUserCurrentState;
    }
}

