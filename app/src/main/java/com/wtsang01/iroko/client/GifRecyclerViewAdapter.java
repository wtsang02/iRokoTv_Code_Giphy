package com.wtsang01.iroko.client;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.wtsang01.iroko.R;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by wtsang01 on 11/3/2016.
 */

public class GifRecyclerViewAdapter extends RecyclerView.Adapter {
    private static final String TAG = GifRecyclerViewAdapter.class.getSimpleName();
    private List<Gif> mGifList;
    private Context mContext;

    public GifRecyclerViewAdapter(Context context, List itemList) {
        this.mGifList = itemList;
        this.mContext = context;
    }



    /**
     * First loads the still image for quick preview. THen loads the original .gif image
     * to animate.
     *
     * @param gHold
     */
    private void loadImage(final GifHolder gHold){
        Glide
                .with(mContext)
                .load(gHold.getGif().getStillUrl())
                .asBitmap()
                .fitCenter()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        gHold.getImageViewGif().setImageBitmap(resource);
                        loadOriginalImage(gHold);
                    }


                });
    }

    private void loadOriginalImage(final GifHolder gHold) {
        Glide
                .with(mContext)
                .load(gHold.getGif().getURL())
                .asGif()
                .fitCenter()
                .into(new SimpleTarget<GifDrawable>() {
                    @Override
                    public void onResourceReady(GifDrawable resource, GlideAnimation<? super GifDrawable> glideAnimation) {
                        Log.d(TAG,"Starting gif");
                        resource.start();
                        gHold.getImageViewGif().setImageDrawable(resource);


                    }
                });
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final GifHolder gHold = (GifHolder) holder;
        gHold.setGif(this.mGifList.get(position));
        loadImage(gHold);
        gHold.getContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newActivityIntent = new Intent(mContext, EnlargedImageActivity.class);
                newActivityIntent.putExtra(EnlargedImageActivity.GIF_URL_EXTRA,gHold.getGif().getURL() );
                newActivityIntent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(newActivityIntent);

            }
        });

    }

    @Override
    public GifHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, null, false);
        return new GifHolder(layoutView);
    }
    @Override
    public int getItemCount() {
        return this.mGifList.size();
    }

}
