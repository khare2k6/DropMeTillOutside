package com.dmto.webservice;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

/**
 * Created by ankitkha on 15-Feb-16.
 */
public class Constants {
    public static final String DOMAIN_NAME = "dmto";
    public static final String COLON = ":";
    public static final String FORWARD_SLASH = "/";
    public static final String PORT = "3300";
    public static final String SERVER = "http://" + DOMAIN_NAME + ".com" + COLON + PORT + FORWARD_SLASH;

    //PARAMS
    public static final String PARAM_EMAIL = "email";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_CODE = "securityCode";

    //APIs
    public static final String API_SIGNUP_NEW_USER = SERVER+"signup";
    public static final String API_AUTHENTICATE_NEW_USER = SERVER+"authenticate";

    public static AbstractXMPPConnection getXmppConnection(String uname,String pwd) {
        XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                .setUsernameAndPassword(uname, pwd)
                .setServiceName("dmto.com")
                .setHost("192.168.1.102")
                .build();
        AbstractXMPPConnection connection = new XMPPTCPConnection(config);
        return connection;
    }
}
