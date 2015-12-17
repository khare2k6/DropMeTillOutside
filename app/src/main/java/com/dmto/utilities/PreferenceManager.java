package com.dmto.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.text.TextUtils;

import com.dmto.user.IUserLogin;

/**
 * Created by ankitkha on 11-Dec-15.
 */
public class PreferenceManager {
    private SharedPreferences mPreferences;
    private static PreferenceManager mInstance;

    public static final String KEY_UNAME = "userName";
    public static final String KEY_USER_STATE = "userState";
    public static final String KEY_USER_PASSWORD = "password";
    public static final String KEY_LOGGED_IN_STATE = "userState";
    public static final String KEY_USER_EMAIL = "email";
    public static final String KEY_COMPANY_NAME = "companyName";
    public static final String KEY_COMPANY_DOMAIN = "companyDomain";
    public static final String KEY_OFFICE_LOCATION = "officeName";
    private static final String KEY_OFFICE_LOCATION_LATITUDE = "officeLatitude";
    private static final String KEY_OFFICE_LOCATION_LONGITUDE = "officeLongitude";
    public static final String KEY_VEHICLE_NUMBER = "vehicleNumber";


    public static final String BLANK= "";
    private static final int NEGATIVE_INT = -1;
    private static final float NEGATIVE_FLOAT = (float) -1.0;


    /**
     * Private Constructor for singleton maintainence
     * @param context
     */
    private PreferenceManager(Context context){
        mPreferences = (SharedPreferences) android.preference.PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static PreferenceManager getmInstance(Context context){
        if(mInstance == null){
            mInstance = new PreferenceManager(context);
        }
        return mInstance;
    }


    /**
     * set the password in preferences for future login
     * ToDo: Either save encrypted password or save token instead
     * @param pwd
     */
    public void setUserPassword(String pwd) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(KEY_USER_PASSWORD, pwd);
        editor.commit();
    }

    /**
     * get currently logged in userName
     * @return userName of logged in user
     */
    public String getUserPassword() {
        return mPreferences.getString(KEY_USER_PASSWORD, BLANK);
    }

    /**
     * set the logged in user after successful login
     * @param userName
     */
    public void setUserName(String userName) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(KEY_UNAME, userName);
        editor.commit();
    }

    /**
     * get currently logged in userName
     * @return userName of logged in user
     */
    public String getUserName() {
        return mPreferences.getString(KEY_UNAME, BLANK);
    }

    /**
     * Saves the current user state in preferences
     * @param userState {@link com.dmto.user.IUserLogin.SIGNIN_STATES}
     */
    public void setCurrentUserState(IUserLogin.SIGNIN_STATES userState) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(KEY_USER_STATE, userState.getName());
        editor.commit();
    }

    /**
     * Gets the last user state from preferences
     * @return one of the states from {@link com.dmto.user.IUserLogin.SIGNIN_STATES}
     */
    public IUserLogin.SIGNIN_STATES getCurrentUserState() {
        IUserLogin.SIGNIN_STATES currentState = null;
        String state = mPreferences.getString(KEY_LOGGED_IN_STATE, BLANK);

        if (!TextUtils.isEmpty(state)) {
            currentState = IUserLogin.SIGNIN_STATES.valueOf(state);
        }
        return currentState;
    }

    /**
     * Put user email in preferences
     * @param email
     */
    public void setUserEmail(String email) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(KEY_USER_EMAIL, email);
        editor.commit();
    }

    /**
     * Get user's email from preferences
     * @return
     */
    public String getUserEmail() {
        return mPreferences.getString(KEY_USER_EMAIL, BLANK);
    }

    /**
     * Persisit office location in preferences
     * @param location
     */
    public void setOfficeLocation(Location location) {
        setStringValueInPrefrences(KEY_OFFICE_LOCATION_LATITUDE, String.valueOf(location.getLatitude()));
        setStringValueInPrefrences(KEY_OFFICE_LOCATION_LONGITUDE, String.valueOf(location.getLongitude()));
    }

    /**
     * Get office location from preferences
     * @return
     */
    public Location getOfficeLocation() {
        Location location = new Location("GPS");
        location.setLatitude(Double.valueOf(getStringValueFromPreferences(KEY_OFFICE_LOCATION_LATITUDE)));
        location.setLongitude(Double.valueOf(getStringValueFromPreferences(KEY_OFFICE_LOCATION_LONGITUDE)));
        return location;
    }

    /**
     * Set company name in preferences
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(KEY_COMPANY_NAME, companyName);
        editor.commit();
    }

    /**
     * Get company Name from preferences
     * @return
     */
    public String getCompanyName() {
        return mPreferences.getString(KEY_COMPANY_NAME, BLANK);
    }

    /**
     * Set company domain name in preferences for email communications
     * @param companyDomain example aricent.com , linkdin.in
     */
    public void setCompanyDomain(String companyDomain) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(KEY_COMPANY_DOMAIN, companyDomain);
        editor.commit();
    }

    /**
     * Sets vehicle number in preferences only if user has selected DMTO category {@link com.dmto.schema.User.DMTO_CATEGORY}
     * @param vehicleNumber
     */
    public void setVehicleNumber(String vehicleNumber) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(KEY_VEHICLE_NUMBER, vehicleNumber);
        editor.commit();
    }

    /**
     * Gets vehicle number of this user from preferences
     * @return
     */
    public String getVehicleNumber() {
        return mPreferences.getString(KEY_VEHICLE_NUMBER, BLANK);
    }
    /**
     * Get company Domain name from preferences for email communications
     * @return company domain name eg. aricnent.com, linkdin.in
     */
    public String getCompanyDomain() {
        return mPreferences.getString(KEY_COMPANY_DOMAIN, BLANK);
    }

    /**
     * Helper method to set String value in preferences
     * @param key
     * @param value
     */
    public void setStringValueInPrefrences(String key, String value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * Helper method to set Integer value in preferences
     * @param key
     * @param value
     */
    public void setIntValueInPrefrences(String key, int value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * Helper method to set Float value in preferences
     * @param key
     * @param value
     */
    public void setFloatValueInPrefrences(String key, float value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    /**
     * Helper method to get String value from preferences
     * @param key
     * @return value or BLANK string otherwise
     */
    public String getStringValueFromPreferences(String key) {
        return mPreferences.getString(key, BLANK);
    }

    /**
     * Helper method to get Integer value from preferences
     * @param key
     * @return value or -1 if not set yet
     */
    public int getIntValueFromPreferences(String key) {
        return mPreferences.getInt(key, NEGATIVE_INT);
    }

    /**
     * Helper method to get Float value from preferences
     * @param key
     * @return value or -1.0 if not set yet
     */
    public float getFloatValueFromPreferences(String key) {
        return mPreferences.getFloat(key, (float) NEGATIVE_FLOAT);
    }



}
