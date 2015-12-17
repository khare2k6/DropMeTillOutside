package com.dmto.user;

/**
 * Created by ankitkha on 11-Dec-15.
 */
public interface IUserLogin {

    public SIGNIN_STATES loginUser(String uname,String pwd);

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
        SERVER_ERROR("SERVER_ERROR");

        private String mState;
        public String getName(){
            return mState;
        }

        private SIGNIN_STATES(String state) {
            mState = state;
        }
    }
}
