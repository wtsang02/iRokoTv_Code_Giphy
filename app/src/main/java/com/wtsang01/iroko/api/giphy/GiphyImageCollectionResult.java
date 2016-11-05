package com.wtsang01.iroko.api.giphy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by wtsang01 on 11/4/2016.
 */

public class GiphyImageCollectionResult {
    @SerializedName("original")
    @Expose
    private GiphyOriginalImage mGiphyOriginalImage;
    @SerializedName("original_still")
    @Expose
    private GiphyStillImage mGiphyStillImage;

    public GiphyOriginalImage getGiphyOriginalImage() {
        return mGiphyOriginalImage;
    }

    public void setGiphyOriginalImage(GiphyOriginalImage giphyOriginalImage) {
        mGiphyOriginalImage = giphyOriginalImage;
    }

    public GiphyStillImage getGiphyStillImage() {
        return mGiphyStillImage;
    }

    public void setGiphyStillImage(GiphyStillImage giphyStillImage) {
        mGiphyStillImage = giphyStillImage;
    }
}
