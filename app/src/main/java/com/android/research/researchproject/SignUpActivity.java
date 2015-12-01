package com.android.research.researchproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.research.researchproject.utility.CommonUtils;
import com.android.research.researchproject.utility.ServerUtils;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by amit on 11/21/2015.
 */
public class SignUpActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private TextView mTvClickHere;
    private Button mBtnSingIn;
    private EditText mEtPhoneNumber, mEtEmailId, mEtPassword;
    Bundle bundle;
    String name, email, id, locale, gender, first_name, last_name, timezone;
    Boolean isartist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        try {
            bundle = getIntent().getBundleExtra("bundle");
        } catch (Exception e) {
            e.printStackTrace();
        }

       // findViewsByIds();
        showDotsProgress(this);
        RequestData();

    }

    private void findViewsByIds() {
        mEtPhoneNumber = (EditText) findViewById(R.id.et_phone);
        mEtEmailId = (EditText) findViewById(R.id.et_email);
        try {
            if (null != bundle)
                mEtEmailId.setText(bundle.getString("email"));
            name = bundle.getString("name");
            locale = bundle.getString("locale");
            gender = bundle.getString("gender");
            first_name = bundle.getString("first_name");
            last_name = bundle.getString("last_name");
            timezone = bundle.getString("timezone");
            id = bundle.getString("id");
            email = bundle.getString("email");
            isartist = bundle.getBoolean("isartist");
        } catch (Exception e) {
            e.printStackTrace();
        }
        mEtPassword = (EditText) findViewById(R.id.et_password);
        //toolbar work
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        TextView back = (TextView) mToolbar.findViewById(R.id.back);
        TextView logout = (TextView) mToolbar.findViewById(R.id.logout);

        back.setOnClickListener(this);
        logout.setOnClickListener(this);

        //clickHere var
        mTvClickHere = (TextView) findViewById(R.id.tv_click_here);
        mTvClickHere.setOnClickListener(this);
        mBtnSingIn = (Button) findViewById(R.id.btn_signup);
        mBtnSingIn.setOnClickListener(this);
        cancelDotsProgress();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_click_here:
                startLoginActivity();
                break;

            case R.id.btn_signup:
                validateInput();
                break;

            case R.id.back:
                LoginManager.getInstance().logOut();
                Intent back = new Intent(SignUpActivity.this, MainActivity.class);
                back.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(back);
                break;
            case R.id.logout:
                LoginManager.getInstance().logOut();
                Intent login = new Intent(SignUpActivity.this, MainActivity.class);
                login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(login);
                break;
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


                            bundle.putString("name", json.getString("name"));
                            bundle.putString("email", json.getString("email"));
                            bundle.putString("profilePic", json.getString("id"));
                            bundle.putString("locale", json.getString("locale"));
                            bundle.putString("gender", json.getString("gender"));
                            bundle.putString("first_name", json.getString("first_name"));
                            bundle.putString("last_name", json.getString("last_name"));
                            bundle.putString("timezone", "null");

                            findViewsByIds();

//                            openSingUp(json.getString("name"), json.getString("email"), json.getString("id"),
//                                    json.getString("locale"), json.getString("gender"), json.getString("first_name"),
//                                    json.getString("last_name"), json.getString("timezone"));
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
    public void onBackPressed() {
//        super.onBackPressed();
        LoginManager.getInstance().logOut();
        Intent back = new Intent(SignUpActivity.this, MainActivity.class);
        back.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(back);
    }

    private void startLoginActivity() {
        Intent in = new Intent(SignUpActivity.this, LoginActivity.class);
        bundle.putString("from","Signup");
        in.putExtra("bundle", bundle);
        startActivityForResult(in, 100);
        //finish();
    }

    private void validateInput() {
        String phoneNumber = mEtPhoneNumber.getText().toString();
        String email = mEtEmailId.getText().toString();
        String password = mEtPassword.getText().toString();
        boolean isValid = true;
        String msg = "";
        if (!CommonUtils.checkForNull(phoneNumber)) {
            isValid = false;
            msg = "Phone Number is mandatory.";
            mEtPhoneNumber.setError(msg);
        } else if (!CommonUtils.isValidPhoneNumber(phoneNumber)) {
            isValid = false;
            msg = "invalid Phone Number.";
            mEtPhoneNumber.setError(msg);
        }

        if (isValid && !CommonUtils.checkForNull(email)) {
            isValid = false;
            msg = "Email mandatory.";
            mEtEmailId.setError(msg);
        } else if (isValid && !CommonUtils.isEmailValid(email)) {
            isValid = false;
            msg = "Email Invalid.";
            mEtEmailId.setError(msg);
        }

        if (isValid && !CommonUtils.checkForNull(password)) {
            isValid = false;
            msg = "Password is invalid .";
            mEtPassword.setError(msg);
        } else if (isValid && password.length() < 6) {
            isValid = false;
            msg = "Password should be minimum 6 digits .";
            mEtPassword.setError(msg);
        }

        if (isValid) {
            requestSignUp();
        }
    }

    private void requestSignUp() {
        bundle.putString("phone", mEtPhoneNumber.getText().toString());
        //show Dots Progress
        showDotsProgress(SignUpActivity.this);

        ParseUser user = new ParseUser();
        user.setUsername(mEtEmailId.getText().toString());
        user.setPassword(mEtPassword.getText().toString());
        user.setEmail(mEtEmailId.getText().toString());


// other fields can be set just like with ParseObject
        user.put("phone", mEtPhoneNumber.getText().toString());

        JSONObject artFilterObjKey = new JSONObject();
        try {
            artFilterObjKey.put("talent", "Testtalent");
            artFilterObjKey.put("genre", "Test genre");
            artFilterObjKey.put("location", "Test location");
            artFilterObjKey.put("isArtist", "Test isArtist");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        JSONObject personalObjKey = new JSONObject();
        try {
            personalObjKey.put("stageName", name);
            personalObjKey.put("location", "null");
            personalObjKey.put("email", mEtEmailId.getText().toString());
            personalObjKey.put("mobile", mEtPhoneNumber.getText().toString());
            personalObjKey.put("password", mEtPassword.getText().toString());
            personalObjKey.put("isArtist", isartist);


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        JSONObject fbObjKey = new JSONObject();
        try {
            fbObjKey.put("fbId", id);
            fbObjKey.put("fbFullName", name);
            fbObjKey.put("fbFirstName", first_name);
            fbObjKey.put("fbLastName", last_name);
            fbObjKey.put("fbEmail", email);
            fbObjKey.put("fbLocation", timezone);
            fbObjKey.put("fbGender", gender);
            fbObjKey.put("fbBirthDay", "null");


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        user.put("artFilterObjKey", artFilterObjKey);
        user.put("personalObjKey", personalObjKey);
        user.put("fbObjKey", fbObjKey);


        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                cancelDotsProgress();
                if (e == null) {
                    // Hooray! Let them use the app now.
                    savePreferences("registered","yes");

                    createAlert(SignUpActivity.this, "Welcome", "Signup Succesfull", false, bundle);
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    try {
                        String localisedMsg = e.getLocalizedMessage();
                        String msg = e.getMessage();
                        int code = e.getCode();

                        Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    } catch (Exception er) {
                        er.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            //open home screen
        } else if (requestCode == 101) {
            //open home screen
        }
    }


    public void createAlert(final Context context, String title, String msg, final boolean finish, final Bundle bundle) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle(title);

        // set dialog message
        alertDialogBuilder
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        dialog.cancel();
                        if (isartist) {
                            Intent in = new Intent(context, UserDetailsActivity.class);
                            in.putExtra("bundle", bundle);
                            ((Activity) context).startActivityForResult(in, 101);
                            ((Activity) context).finish();
                        } else {
                            Intent in = new Intent(context, DummyHomeScreen.class);
                            in.putExtra("bundle", bundle);
                            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            ((Activity) context).startActivity(in);
                            ((Activity) context).finish();
                        }


                        if (finish) {
                            // ((Activity) context).finish();

                        }

                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        if (!((Activity) context).isFinishing()) {
            // show it
            alertDialog.show();
        }

    }

    public void savePreferences(String key, String value) {

        SharedPreferences sharedPreferences = PreferenceManager

                .getDefaultSharedPreferences(this);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(key, value);

        editor.commit();

    }

}

