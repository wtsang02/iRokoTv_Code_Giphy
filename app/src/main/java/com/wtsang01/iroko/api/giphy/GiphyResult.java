package com.wtsang01.iroko.api.giphy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wtsang01.iroko.client.Gif;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wtsang01 on 11/4/2016.
 */

public class GiphyResult {
    @SerializedName("data")
    @Expose
 private GiphyImageResult[] results;

    public GiphyImageResult[] getResults() {
        return results;
    }

    public void setResults(GiphyImageResult[] results) {
        this.results = results;
    }

}
