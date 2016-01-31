package com.dmto.server;

/**
 * Created by ankitkha on 22-Dec-15.
 */
public interface IResponseCallback<T,U> {
    /**
     * Login,signup,userName lookup will be async calls,
     * when response comes it needs to be identified response is for which recently called APIs.
     * Responses can be :
     * <li><b>USERNAME_LOOKUP</b></li>
     * <li><b>SIGNUP</b></li>
     * <li><b>LOGIN</b></li>

    public enum RESPONSE_FOR {
        USERNAME_LOOKUP,
        SIGNUP,
        LOGIN;
    }*/
    /**
     * Successful response for http request
     * @param t
     */
        public void onSuccess(T t);
        public void onFailure(U u);
    }

