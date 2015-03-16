package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.util.Log;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1/"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "Pz4vOLXMFgKy1rVhhtK1FuMYa";       // Change this
	public static final String REST_CONSUMER_SECRET = "zC3DvMgdH5D0rPe45EgIh00sjrNKquIHFMwBYmZUTK8UGX2lfQ"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://desysimpletweets"; // Change this (here and in manifest)

    public static final int TWEETS_PER_PAGE = 25;

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	// CHANGE THIS
	// DEFINE METHODS for different API endpoints here
	public void getInterestingnessList(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("format", "json");
		client.get(apiUrl, params, handler);
	}

    //METHOD == ENDPOINT

    //    GET statuses/home_timeline.json
    //    count=25
    //    since_id=1
    public void getHomeTimeline(int page, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        //Specify the params
        RequestParams params = new RequestParams();
        params.put("count", TWEETS_PER_PAGE);
        int since_id = ((page - 1)*TWEETS_PER_PAGE)+1;
        //params.put("since_id", ((page - 1)*TWEETS_PER_PAGE)+1);
        long prev_max_id = Tweet.max_id -1;
//        params.put("count", 25);
//        params.put("since_id",1);
        params.put("max_id", prev_max_id);
        //Execute the request
        getClient().get(apiUrl, params, handler);
    }

    // compose tweet


    //HomeTimeLine - gets us the home timeline

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */


    // GET my profile
    //https://api.twitter.com/1.1/users/show.json?screen_name=danesyoo&user_id=2804203172
    public void getMyProfile(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("users/show.json");
        RequestParams params = new RequestParams();
        params.put("screen_name", "danesyoo");
        params.put("user_id", "2804203172");
        getClient().get(apiUrl, params, handler);

    }

    //Post talk in timeline
    public void postTweet(AsyncHttpResponseHandler handler, String tweetTalk) {
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", tweetTalk);
        getClient().post(apiUrl, params, handler);
    }


    // Rate limits
    // https://dev.twitter.com/rest/public/rate-limiting
    public void getRateLimits( AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("application/rate_limit_status.json");
        RequestParams params = new RequestParams();
        params.put("resources", "statuses"); //help,users,search,statuses
        Log.e("Twitter Client", "Sending out network request:" + apiUrl);
        getClient().get(apiUrl, params, handler);
    }

    public void getMentionTimeline(int page, JsonHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/mentions_timeline.json");
        //Specify the params
        RequestParams params = new RequestParams();
        params.put("count", TWEETS_PER_PAGE);
        int since_id = ((page - 1)*TWEETS_PER_PAGE)+1;
        //params.put("since_id", ((page - 1)*TWEETS_PER_PAGE)+1);
        long prev_max_id = Tweet.max_id -1;
//        params.put("count", 25);
//        params.put("since_id",1);
        params.put("max_id", prev_max_id);
        //Execute the request
        getClient().get(apiUrl, params, handler);
    }


    public void getUserTimeline(String screenName, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/user_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", 25);
        params.put("screen_name", screenName);
        getClient().get(apiUrl, params, handler);
    }

    public void getUserInfo(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("account/verify_credentials.json");
        getClient().get(apiUrl, null, handler);
    }

}