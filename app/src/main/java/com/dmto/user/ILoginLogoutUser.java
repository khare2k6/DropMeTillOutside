package com.dmto.user;

import com.dmto.server.IResponseCallback;

/**
 * Created by ankitkha on 11-Dec-15.
 */
public interface ILoginLogoutUser {

    public void loginUser(String uname, String pwd,
                                   IResponseCallback<SIGNIN_STATES,SIGNIN_STATES>callback);
    public void logoutUser();

    /**
     * Possible values for user states:
     * <br>
     *     <li>LOGIN_SUCCCESSFUL</li>
     *     <li>NO_SUCH_USER</li>
     *     <li>CODE_VALIDATION_PENDING</li>
     *     <li>WRONG_PASSWORD</li>
     *     <li>SERVER_ERROR</li>
     */
    public enum SIGNIN_STATES{
        LOGIN_SUCCESSFUL ("LOGIN_SUCCESSFUL"),
        NO_SUCH_USER("NO_SUCH_USER"),
        CODE_VALIDATION_PENDING("CODE_VALIDATION_PENDING"),
        WRONG_PASSWORD("WRONG_PASSWORD"),
        NETWORK_ERROR("NETWORK_ERROR"),
        SERVER_ERROR("SERVER_ERROR"),
        USERNAME_OR_PASSWORD_INVALID("USERNAME_OR_PASSWORD_INVALID"),
        SOMETHING_ELSE_WRONG("SOMETHING_WEIRD");

        private String mState;
        public String getName(){
            return mState;
        }

        private SIGNIN_STATES(String state) {
            mState = state;
        }
    }
}
