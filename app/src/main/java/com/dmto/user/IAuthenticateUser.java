package com.dmto.user;

import com.dmto.server.IResponseCallback;

/**
 * Created by ankitkha on 11-Dec-15.
 */
public interface IAuthenticateUser {
    /**
     * Send authentication code OR link to user's email address
     * with which user will confirm his identity
     * @param userEmail with which user is registered
     * @return true if successfully sent.
     * ToDo: Server should send authentication code to user's email, this API probably not needed in client
     */
    public boolean getAuthenticationCodeToEmail(String userEmail);

    /**
     * User sends the code to server which is received on his email.
     * @param myEmail user's registration email address
     * @param code sent by server
     * @return one of the possible states of {@link AUTHENTICATION_ERRORS}
     */
    public AUTHENTICATION_ERRORS authenticateThisUser(String myEmail, String code);

    /**
     * User have not yet authenticated email and lost track of authentication email.
     * This api should re-trigger authentication email to user's email
     * @param email
     */
    public void resendEmailAuthentication(String email, IResponseCallback<String,String>callback);
    /**
     * Should show user the status of authentication request
     * @param errorState
     * Todo:Might not be required as using callback now {@link IResponseCallback}
     */
    public void showErrorToUser(AUTHENTICATION_ERRORS errorState);

    /**
     * Possible error stats enum in authenticating a user.
     * <li>NO_ERROR</li>
     * <li>NETWORK_ERROR</li>
     * <li>SERVER_ERROR</li>
     * <li>CODE_MISMATCH</li>
     * <li>CODE_MATCH_SUCCESSFUL</li>
     */
    public enum AUTHENTICATION_ERRORS{
        NO_ERROR,
        NETWORK_ERROR,
        SERVER_ERROR,
        CODE_MISMATCH,
        CODE_MATCH_SUCCESSFUL
    }
}
