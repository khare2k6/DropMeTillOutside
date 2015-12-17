package com.dmto.user;

/**
 * Created by ankitkha on 11-Dec-15.
 */
public interface IUserSignup {
    /**
     * When user is signing up, check in DB if userName is avaialble
     * @param uName
     * @return true if available
     */
    public boolean isUsernameAvailable(String uName);

    /**
     * Signup API
     * @param userName desired userName
     * @param email email for registration <b>(official email as of now)</b>
     * @param pwd
     * @return one of the possible states
     * {@link SIGNUP_ERROR_STATES}
     */
    public SIGNUP_ERROR_STATES newUserSignup(String userName,String email,String pwd);

    /**
     * Incase user has forgotten his password,call this
     * API to receive password reset link on email
     */
    public void forgotPassword(String email);

    /**
     * Should show user the status of signup request
     * @param errorState
     */
    public void showErrorToUser(SIGNUP_ERROR_STATES errorState);

    /**
     * Defines possible error states when user is signing up
     * <br>
     * <li>USERNAME_TAKEN</li>
     * <li>EMAIL_ALREADY_REGISTERED</li>
     * <li>PASSWORD_TOO_LONG</li>
     * <li>SERVER_ERROR</li>
     * <li>NO_ERROR</li>
     * <li>CODE_VALIDATION_PENDING</li>
     */
    public enum SIGNUP_ERROR_STATES{
        USERNAME_TAKEN,
        EMAIL_ALREADY_REGISTERED,
        PASSWORD_TOO_LONG,
        CODE_VALIDATION_PENDING,
        SERVER_ERROR,
        NO_ERROR
    }
}
