package com.wtsang01.iroko.api.giphy;

/**
 * Created by wtsang01 on 11/4/2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Model results of giphy image results with meta data. But meta data is not yet captured as it is unneeded for this project.
 */
public class GiphyImageResult {

    @SerializedName("images")
    @Expose
   private GiphyImageCollectionResult mGiphyImageCollectionResult;

    public GiphyImageCollectionResult getGiphyImageCollectionResult() {
        return mGiphyImageCollectionResult;
    }

    public void setGiphyImageCollectionResult(GiphyImageCollectionResult giphyImageCollectionResult) {
        mGiphyImageCollectionResult = giphyImageCollectionResult;
    }

    public String getOriginalImageURL(){
        return this.getGiphyImageCollectionResult().getGiphyOriginalImage().getUrl();
    }
    public String getStillImageURL(){
        return this.getGiphyImageCollectionResult().getGiphyStillImage().getUrl();
    }
}
