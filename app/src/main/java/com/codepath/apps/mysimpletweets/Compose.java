package com.codepath.apps.mysimpletweets;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
    private TextView countWord;

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
        countWord = (TextView) findViewById(R.id.tvCountWord);

        String name = getIntent().getStringExtra("name");
        String screenName = getIntent().getStringExtra("screenName");
        String profilePicture = getIntent().getStringExtra("picture");

        tvUser.setText(name);
        tvId.setText(screenName);
        iv.setImageResource(0);
        Picasso.with(getApplicationContext()).load(profilePicture).into(iv);


        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int remainingMsgLength = 140 - s.toString().length();
                countWord.setText(Integer.toString(remainingMsgLength));

                if ((remainingMsgLength == 140) || (remainingMsgLength < 0)) {
//                    btnTweet.setEnabled(false);
//                    btnTweet.getBackground().setAlpha(50);
                } else {
//                    btnTweet.setEnabled(true);
//                    btnTweet.getBackground().setAlpha(255);
                }
            }
        });
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
