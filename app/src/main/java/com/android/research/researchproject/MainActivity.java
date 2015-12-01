package com.android.research.researchproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    private boolean isGuestUser = false;
    private boolean isArtistUser = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewsById();


        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("com.android.research.researchproject", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }


    }


    TextView mTvWelcome, mTvSkip;
    LoginButton mBtnFbLogin;
    ImageView mImgVGuest, mImgArtist;
    CallbackManager callbackManager;

    private void findViewsById() {
//        tv_label_welcome
        mTvWelcome = (TextView) findViewById(R.id.tv_label_welcome);
//        tv_label_skip
        mTvSkip = (TextView) findViewById(R.id.tv_label_skip);
        callbackManager = CallbackManager.Factory.create();

        mTvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //btn_fb_login
        mBtnFbLogin = (LoginButton) findViewById(R.id.btn_fb_login);
        mBtnFbLogin.setReadPermissions("public_profile email");

        mTvSkip.setEnabled(false);
        mTvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent home = new Intent(MainActivity.this, DummyHomeScreen.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", "");
                bundle.putString("email", "");
                bundle.putString("profilePic", "");
                bundle.putString("locale", "");
                bundle.putString("gender", "");
                bundle.putString("first_name", "");
                bundle.putString("last_name", "");
                bundle.putString("timezone", "null");
                bundle.putBoolean("isartist", isArtistUser);
                bundle.putBoolean("skip",true);
                home.putExtra("bundle", bundle);
                home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(home);

            }
        });
        mBtnFbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AccessToken.getCurrentAccessToken() != null) {
//                    share.setVisibility(View.INVISIBLE);
//                    details.setVisibility(View.INVISIBLE);
//                    profile.setProfileId(null);
                }
            }
        });


        // mBtnFbLogin.setOnClickListener(this);
        mBtnFbLogin.setEnabled(false);
        //img_guest
        mImgVGuest = (ImageView) findViewById(R.id.img_guest);
        mImgVGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//enable skip
                mTvSkip.setEnabled(true);
                mTvSkip.setAlpha(1f);
                mBtnFbLogin.setAlpha(1f);
                mImgVGuest.setAlpha(1f);
                mImgArtist.setAlpha(0.5f);
                mBtnFbLogin.setEnabled(true);
                isGuestUser = true;
                isArtistUser = false;
            }
        });
        //img_artist
        mImgArtist = (ImageView) findViewById(R.id.img_artist);
        mImgArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //disable click
                mTvSkip.setEnabled(false);
                mTvSkip.setAlpha(0.5f);
                mBtnFbLogin.setAlpha(1f);
                mImgVGuest.setAlpha(0.5f);
                mImgArtist.setAlpha(1f);
                mBtnFbLogin.setEnabled(true);
                isGuestUser = false;
                isArtistUser = true;
            }
        });

        mBtnFbLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                if (AccessToken.getCurrentAccessToken() != null) {
                   // RequestData();
                    openSingUp("", "", "","","","","","");

//                    share.setVisibility(View.VISIBLE);
//                    details.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancel() {
                Log.d("FB", "on cancel");

            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("FB", exception.toString());

            }
        });
    }


    private void openSingUp(String name, String email, String profilePic, String locale, String gender, String first_name,
                            String last_name, String timezone) {

        if (isGuestUser) {

        } else if (isArtistUser) {

        }
        String str = loadSavedPreferences("registered");
        if(str.equals("yes")){

            Intent home = new Intent(MainActivity.this, DummyHomeScreen.class);
            Bundle bundle = new Bundle();
            bundle.putString("name", name);
            bundle.putString("email", email);
            bundle.putString("profilePic", profilePic);
            bundle.putString("locale", locale);
            bundle.putString("gender", gender);
            bundle.putString("first_name", first_name);
            bundle.putString("last_name", last_name);
            bundle.putString("timezone", "null");
            bundle.putBoolean("isartist", isArtistUser);
            home.putExtra("bundle", bundle);
            home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(home);
        }else {

            Intent in = new Intent(MainActivity.this, SignUpActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("name", name);
            bundle.putString("email", email);
            bundle.putString("profilePic", profilePic);
            bundle.putString("locale", locale);
            bundle.putString("gender", gender);
            bundle.putString("first_name", first_name);
            bundle.putString("last_name", last_name);
            bundle.putString("timezone", "null");
            bundle.putBoolean("isartist", isArtistUser);
            in.putExtra("bundle", bundle);
            startActivityForResult(in, 100);
        }
    }


    public void RequestData() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                JSONObject json = response.getJSONObject();

                try {
                    if (json != null) {
                        String text = "<b>Name :</b> " + json.getString("name") + "<br><br><b>Email :</b> " + json.getString("email") + "<br><br><b>Profile link :</b> " + json.getString("link");
//                        details_txt.setText(Html.fromHtml(text));
//                        profile.setProfileId(json.getString("id"));

                        try {
//Bundle bundle = new Bundle();
//                            bundle.putString("name","");
//                            bundle.putString("email","");
//                            bundle.putString("","");
//                            bundle.putString("","");
//                            bundle.putString("","");
//                            bundle.putString("","");
//                            bundle.putString("","");
//                            bundle.putString("","");
//                            bundle.putString("","");

                            openSingUp(json.getString("name"), json.getString("email"), json.getString("id"),
                                    json.getString("locale"), json.getString("gender"), json.getString("first_name"),
                                    json.getString("last_name"), json.getString("timezone"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture,locale,gender,first_name,last_name,timezone");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    public String loadSavedPreferences(String key) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);

        String value = sharedPreferences.getString(key, "no");
        return value;
    }

}
