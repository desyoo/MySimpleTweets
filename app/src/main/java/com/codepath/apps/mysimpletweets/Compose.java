package com.codepath.apps.mysimpletweets;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

public class Compose extends ActionBarActivity {
    private ImageView iv;
    private TextView tvUser;
    private TextView tvId;
    private EditText etInput;

    public Compose() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        initialize();

    }

    public void initialize() {
        iv = (ImageView) findViewById(R.id.ivProfileImage);
        tvUser = (TextView) findViewById(R.id.tvUserName);
        tvId = (TextView) findViewById(R.id.tvID);
        etInput = (EditText) findViewById(R.id.etInput);

        String name = getIntent().getStringExtra("name");
        String screenName = getIntent().getStringExtra("screenName");
        String profilePicture = getIntent().getStringExtra("picture");

        tvUser.setText(name);
        tvId.setText(screenName);
        iv.setImageResource(0);
        Picasso.with(getApplicationContext()).load(profilePicture).into(iv);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.compose) {
            String talk = etInput.getText().toString();
            TwitterClient client = new TwitterClient(this.getApplicationContext());
            client.postTweet(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);

                    Log.d("DEBUG3", response.toString());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);

                    Log.d("DEBUG3", responseString.toString());
                }
            }, talk);
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // ActivityTwo.java
    public void onSubmit(View v) {
        // closes the activity and returns to first screen
        this.finish();
    }
}
