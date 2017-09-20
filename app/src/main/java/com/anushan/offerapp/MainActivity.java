package com.anushan.offerapp;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.anushan.offerapp",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }


        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_main);
        loginButton = (LoginButton) findViewById(R.id.login_button);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {


            @Override
            public void onSuccess(LoginResult loginResult) {
                Profile profile = Profile.getCurrentProfile();
                //ProfilePictureView profilePictureView;
                // profilePictureView = (ProfilePictureView) findViewById(R.id.pro);
                // profilePictureView.setProfileId(loginResult.getAccessToken().getUserId());
                // Bitmap bitmap = new RetrieveFeedTask().doInBackground(loginResult.getAccessToken().getUserId());
                //new RetrieveFeedTask().execute(loginResult.getAccessToken().getUserId());
                //Bitmap bitmap = getFacebookProfilePicture();
                Log.d("get me profile", profile.getFirstName());
                Intent myIntent = new Intent(MainActivity.this, Main2Activity.class);
                myIntent.putExtra("bitmap", loginResult.getAccessToken().getUserId());
                String x = "Anushan";
                myIntent.putExtra("name", x);
                startActivityForResult(myIntent, 200);


            }

            @Override
            public void onCancel() {
                Log.d("LOGIN_CANCEL", "Cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("LOGIN_ERROR", "Error");
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("Its working", "d");
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) {
            finish();
        }

    }


    protected class RetrieveFeedTask extends AsyncTask<String, Void, Bitmap> {

        private Exception exception;

        protected Bitmap doInBackground(String... userID) {
            try {
                URL imageURL = null;
                try {
                    imageURL = new URL("https://graph.facebook.com/" + Arrays.toString(userID) + "/picture?type=large");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                Bitmap bitmap = null;
                try {
                    assert imageURL != null;
                    bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return bitmap;
            } catch (Exception e) {
                this.exception = e;

                return null;
            }
        }


        protected void onPostExecute(Bitmap feed) {

        }
    }


}
