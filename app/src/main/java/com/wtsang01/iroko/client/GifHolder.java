package com.wtsang01.iroko.client;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wtsang01.iroko.R;

/**
 * Created by wtsang01 on 11/3/2016.
 */

public class GifHolder extends RecyclerView.ViewHolder {
    private Gif mGif;
    private ImageView mImageViewGif;

    private View container;

    public GifHolder(View itemView) {
        super(itemView);
        mImageViewGif = (ImageView) itemView.findViewById(R.id.ivGif);
        container = itemView.findViewById(R.id.card_view);
    }

    public Gif getGif() {
        return mGif;
    }

    public void setGif(Gif gif) {
        mGif = gif;
    }

    public ImageView getImageViewGif() {
        return mImageViewGif;
    }

    public View getContainer() {
        return container;
    }

    public void setImageViewGif(ImageView imageViewGif) {
        mImageViewGif = imageViewGif;
    }


    public void setContainer(View container) {
        this.container = container;
    }


}
