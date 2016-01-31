package com.dmto;

import com.dmto.server.IResponseCallback;
import com.dmto.user.parse.ParseUserSignup;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import static org.mockito.Mockito.mock;

/**
 * Created by ankitkha on 26-Dec-15.
 */
public class ParseSignupTest {

    private ParseUserSignup mUserSignup;

    @Before
    public void setUp() {
        mUserSignup = new ParseUserSignup();
    }

    @Test
    public void testSignup() {
        IResponseCallback<Boolean,String> callback = new IResponseCallback<Boolean, String>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                assertTrue(aBoolean);
            }

            @Override
            public void onFailure(String s) {
                fail();
            }
        };
        callback.onSuccess(false);
        }

       // mUserSignup.isUsernameAvailable("akhare", new IResponseCallback<Boolean, String>() {



}
