package com.codepath.apps.mysimpletweets.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by desy on 3/8/15.
 */
public class Profile {
    private String name;
    private String screen_name;
    private String profile_image_url;

    public String getName() {
        return name;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public static Profile fromJSON(JSONObject jsonObject) {
        Profile tweet = new Profile();
        //Extract the values from the json, store them
        try {
            tweet.name = jsonObject.getString("name");
            tweet.screen_name = jsonObject.getString("screen_name");
            tweet.profile_image_url = jsonObject.getString("profile_image_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Return the tweet object
        return tweet;

    }
}
