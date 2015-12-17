package com.dmto.server;

/**
 * Created by ankitkha on 16-Dec-15.
 */
public interface IUpdateCompanyDetails {

    /**
     * When new user's company is not listed in the listView
     * @param newCompanyName
     */
    public void addNewCompany(String newCompanyName);

    /**
     * Sync company lists with ths server to be shown in drop down list to the user.
     * Todo:What should be the return type?
     */
    public void updateCompanyList();

}
