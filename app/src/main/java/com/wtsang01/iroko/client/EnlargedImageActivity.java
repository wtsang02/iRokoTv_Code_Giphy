package com.wtsang01.iroko.client;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.wtsang01.iroko.R;


/**
 * Created by wtsang01 on 11/5/2016.
 */

public class EnlargedImageActivity extends Activity {
public static final String GIF_URL_EXTRA="GifUrl";
    private String mGifUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enlarge_image_activity);
        final ImageView ivImage= (ImageView) findViewById(R.id.ivEnlargeImage);

        if(getIntent().hasExtra(GIF_URL_EXTRA)){
            Bundle extras = getIntent().getExtras();
            mGifUrl = extras.getString(GIF_URL_EXTRA);
        }

        Glide
                .with(getBaseContext())
                .load(mGifUrl)
                .asGif()
                .placeholder(R.drawable.giphy_horiz_logo)
                .fitCenter()
                .into(new SimpleTarget<GifDrawable>() {
                    @Override
                    public void onResourceReady(GifDrawable resource, GlideAnimation<? super GifDrawable> glideAnimation) {
                        resource.start();
                        ivImage.setImageDrawable(resource);

                    }
                });
    }
}
