package com.wtsang01.iroko.client;

/**
 * Created by wtsang01 on 11/3/2016.
 */

public class Gif {

    private String mURL;
    private String mStillUrl;

    public Gif(String stillUrl, String URL) {
        mStillUrl = stillUrl;
        mURL = URL;
    }

    public String getURL() {
        return mURL;
    }

    public void setURL(String URL) {
        mURL = URL;
    }


    public String getStillUrl() {
        return mStillUrl;
    }

    public void setStillUrl(String stillUrl) {
        mStillUrl = stillUrl;
    }


}
