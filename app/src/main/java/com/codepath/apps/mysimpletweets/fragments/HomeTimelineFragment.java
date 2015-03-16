package com.codepath.apps.mysimpletweets.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.codepath.apps.mysimpletweets.JsonErrorHttpResponseHandler;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.TwitterUtils;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by desy on 3/15/15.
 */
public class HomeTimelineFragment extends TweetsListFragment {

    private TwitterClient client;
    public static final int FIRST_PAGE = 1;
    private SwipeRefreshLayout swipeContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get the client
        client = TwitterApplication.getRestClient(); //singleton client
        //set up refresh functionality
        //onSwipeListener();

        populateTimeline(FIRST_PAGE);


    }




    // Send an API request to get the timeline json
    // fill the listview by creating the tweet objects from the
    private void populateTimeline(int page) {
        boolean isNetworkActive = isNetworkAvailable();

//        if (page == FIRST_PAGE) {
//            Tweet.max_id = Long.MAX_VALUE;
//            if (!isNetworkActive) {
//                // load from local storage
//                Toast.makeText(this, "No intenet, connecting from local cache..", Toast.LENGTH_SHORT).show();
//                aTweets.addAll(Tweet.getAll());
//                swipeContainer.setRefreshing(false);
//                return;
//            }
//            else {
//                // invalidate the cache
//                Toast.makeText(this, "Back online.", Toast.LENGTH_SHORT).show();
//                new Delete().from(Tweet.class).execute();
//                new Delete().from(User.class).execute();
//            }
//        } else {
//            if (!isNetworkActive) {
//                // next page, nothing can be done, sorry!!
//                return;
//            }
//        }
        client.getHomeTimeline(page, new JsonHttpResponseHandler() {
            // SUCCESS
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d("DEBUG", json.toString());
                //JSON here
                //DESERIALIZE JSON
                //CREATE MODELS AND ADD THEM TO THE ADAPTER
                //LOAD THE MODEL DATA INTO LISTVIEW
                ArrayList<Tweet> tweets = Tweet.fromJSONArray(json);
                addAll(tweets);
                //Log.d("DEBUG", aTweets.toString());
            }

            // FAILURE
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
                swipeContainer.setRefreshing(false);
                TwitterUtils.handleReqFailure(getActivity(), client, errorResponse, new JsonErrorHttpResponseHandler(getActivity()));
            }
        });
    }



    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public void fetchTimelineAsync(int page) {
        //aTweets.clear();
        populateTimeline(page);
        swipeContainer.setRefreshing(false);

    }


}
