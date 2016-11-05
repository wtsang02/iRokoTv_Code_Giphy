package com.wtsang01.iroko;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.wtsang01.iroko.api.ImageRequestQueue;
import com.wtsang01.iroko.api.giphy.GiphyImageResult;
import com.wtsang01.iroko.api.giphy.GiphyResult;
import com.wtsang01.iroko.client.Gif;
import com.wtsang01.iroko.client.GifRecyclerViewAdapter;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

public class IRokoGridGifActivity extends AppCompatActivity {
    private static final String TAG = IRokoGridGifActivity.class.getSimpleName();
    private List<Gif> mGifList = new LinkedList<Gif>();
    private boolean mLoading;
    private GifRecyclerViewAdapter mAdapter = null;
    private String mUnderlyingQuery = "funny+cat";
    private static final String PRE_QUERY_URL = "http://api.giphy.com/v1/gifs/search?q=";
    private static final String POST_QUERY_URL = "&limit=10&api_key=dc6zaTOxFJmzC&offset=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iroko_grid_gif_activity);
        mLoading = false;

        //Build tool bar items
        Toolbar myToolbar = (Toolbar) findViewById(R.id.tbToolBar);
        setSupportActionBar(myToolbar);
        final EditText etSearch = (EditText) findViewById(R.id.etQuery);
        Button btSearch = (Button) findViewById(R.id.btSearch);
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoading = true;
                search(etSearch.getText().toString().trim());
                etSearch.clearFocus();
                etSearch.setText("");
            }
        });
        reBuildGrid();
    }

    private void lazyLoadImagesASync(int offset) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, getEncodedQueryURL() + Integer.toString(offset), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        GiphyResult results = gson.fromJson(response.toString(), GiphyResult.class);
                        for (GiphyImageResult result : results.getResults()) {
                            Gif gif = new Gif(result.getStillImageURL(),result.getOriginalImageURL());
                            mGifList.add(gif);
                        }
                        Log.d(TAG, "LOAD DONE  size:" + mGifList.size());
                        mAdapter.notifyDataSetChanged();
                        mLoading = false;
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        ImageRequestQueue.getInstance(getBaseContext()).addToRequestQueue(jsObjRequest);

    }

    public void search(String query) {
        this.mUnderlyingQuery = query;
        mGifList = new LinkedList<>();
        ImageRequestQueue.getInstance(getBaseContext()).getRequestQueue().cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
        reBuildGrid();
        mAdapter.notifyDataSetChanged();
        hideKeyboard();
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void reBuildGrid(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvGif);
        recyclerView.setHasFixedSize(true);

        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new GifRecyclerViewAdapter(getBaseContext(), mGifList);
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int pastVisibleItems, visibleItemCount, totalItemCount;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                int[] firstVisibleItems = null;
                firstVisibleItems = layoutManager.findFirstVisibleItemPositions(firstVisibleItems);
                if (firstVisibleItems != null && firstVisibleItems.length > 0) {
                    pastVisibleItems = firstVisibleItems[0];
                }
                // Log.d(TAG, "Load state: "+mLoading);
                if (!mLoading) {
                    //  Log.d(TAG, "visibleItemCount: "+visibleItemCount+"   pastVisibleItems:"+pastVisibleItems+"   totalItemCount: "+totalItemCount);

                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        mLoading = true;
                        lazyLoadImagesASync(totalItemCount);
                        //Log.d(TAG, "LOAD NEXT ITEM");
                    } else {
                    }
                }
            }
        });
        if (!mLoading || mGifList.size() == 0) {
            mLoading = true;
            lazyLoadImagesASync(0);
        }
    }

    private String getEncodedQueryURL() {
        StringBuilder sb = new StringBuilder();
        sb.append(PRE_QUERY_URL);
        try {
            sb.append(URLEncoder.encode(mUnderlyingQuery, "utf-8"));
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        sb.append(POST_QUERY_URL);
        //Log.d(TAG, "building URL: " +sb.toString().trim());
        return sb.toString().trim();
    }



}
