package com.dmto;

import com.dmto.utilities.Utils;

import junit.framework.TestSuite;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class UtilsUnitTests {

    /**
     * Tests if domain name is fetched properly from the user's email
     */
    @Test
    public void fetchComapnyDomainNameFromEmail_positive() {
        assertThat(Utils.getCompanyDomain("ankitkhare@intel.com"),is("intel.com"));
    }

    @Test
    public void fetchComapnyDomainNameFromEmail_emailWithoutAtTheRate() {
        assertNull(Utils.getCompanyDomain("ankitkhare"));
    }
    @Test
    public void fetchComapnyDomainNameFromEmail_emailWithOnlyDotCom() {
        assertNull(Utils.getCompanyDomain("intel.com"));
    }


    @Test
    public void fetchComapnyDomainNameFromEmail_emptyEmail() {
        assertNull(Utils.getCompanyDomain(""));
    }

    @Test
    public void fetchComapnyDomainNameFromEmail_nullEmail() {
        assertNull(Utils.getCompanyDomain(null));
    }
    /**
     * Tests if company name is fetched properly from email.
     */
    @Test
    public void fetchCompanyNameFromEmail_positive(){
        assertThat(Utils.getCompanyName("ankitkhare@intel.com"),is("intel"));
    }

    @Test
    public void fetchComapnyNameFromEmail_emailWithoutAtTheRate() {
        assertNull(Utils.getCompanyDomain("ankitkhare"));
    }
    @Test
    public void fetchComapnyNameFromEmail_emailWithOnlyDotCom() {
        assertNull(Utils.getCompanyDomain("intel.com"));
    }

    @Test
    public void fetchCompanyNameFromEmail_emptyEmail() {
        assertNull(Utils.getCompanyDomain(null));
    }
    @Test
    public void fetchCompanyNameFromEmail_nullEmail() {
        assertNull(Utils.getCompanyDomain(null));
    }
}