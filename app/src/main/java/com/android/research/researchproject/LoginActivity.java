package com.android.research.researchproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.research.researchproject.utility.CommonUtils;
import com.android.research.researchproject.utility.ServerUtils;
import com.facebook.login.LoginManager;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Created by B0089798 on 11/21/2015.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private TextView mTvClickHere, mTvForgotPassword;
    private Button mBtnSingIn;
    private EditText mEtEmailId, mEtPassword;
    private Bundle bundle;
    String str_from;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try {
            bundle = getIntent().getBundleExtra("bundle");
        } catch (Exception e) {
            e.printStackTrace();
        }
        str_from=bundle.getString("from");
        findViewsByIds();
    }

    private void findViewsByIds() {
        //et_email
        //et_password
        mEtEmailId = (EditText) findViewById(R.id.et_email);
        mEtEmailId.setText(bundle.getString("email"));
        mEtPassword = (EditText) findViewById(R.id.et_password);
        //toolbar work
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        TextView back = (TextView) mToolbar.findViewById(R.id.back);
        back.setOnClickListener(this);
        TextView logout = (TextView) mToolbar.findViewById(R.id.logout);
        logout.setOnClickListener(this);

        //clickHere var
        mTvClickHere = (TextView) findViewById(R.id.tv_click_here);
        mTvClickHere.setOnClickListener(this);

        mTvForgotPassword = (TextView) findViewById(R.id.tv_forgot_password);
        mTvForgotPassword.setOnClickListener(this);

        mBtnSingIn = (Button) findViewById(R.id.btn_signin);
        mBtnSingIn.setOnClickListener(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 111) {
            createAlert(LoginActivity.this, "Congratulations", "An Email has been sent to registered email");
        }
    }


    private void createAlert(final Context context, String title, String msg) {
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


                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        if (!((Activity) context).isFinishing()) {
            // show it
            alertDialog.show();
        }

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_click_here:
//                Intent in = new Intent(LoginActivity.this, SignUpActivity.class);
//                in.putExtra("bundle", bundle);
//                startActivityForResult(in, 100);
               if (str_from.equalsIgnoreCase("Signup")){

            }else{
                   Intent in = new Intent(LoginActivity.this, SignUpActivity.class);
                   in.putExtra("bundle", bundle);
                   startActivity(in);

               }
                finish();
                break;

            case R.id.btn_signin:
                validateInput();
                break;


            case R.id.back:
                finish();
                break;

            case R.id.logout:
                LoginManager.getInstance().logOut();
                Intent login = new Intent(LoginActivity.this, MainActivity.class);
                login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(login);
                break;

            case R.id.tv_forgot_password:
                Intent in = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                in.putExtra("bundle", bundle);
                startActivityForResult(in, 111);
                break;

        }
    }

    private void validateInput() {
        boolean isValid = true;
        String msg = "";
        String email = mEtEmailId.getText().toString();
        String password = mEtPassword.getText().toString();

        if (!CommonUtils.checkForNull(email)) {
            isValid = false;
            msg = "Email mandatory.";
            mEtEmailId.setError(msg);
        } else if (!CommonUtils.isEmailValid(email)) {
            isValid = false;
            msg = "Email Invalid.";
            mEtEmailId.setError(msg);
        }


        if (isValid && !CommonUtils.checkForNull(password)) {
            isValid = false;
            msg = "Password is invalid .";
            mEtEmailId.setError(msg);
        }

        if (isValid) {
            requestLogin();
        }


    }

    private void requestLogin() {
        //request login service
        showDotsProgress(LoginActivity.this);
        ParseUser.logInInBackground(mEtEmailId.getText().toString(), mEtPassword.getText().toString(), new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                cancelDotsProgress();
                if (user != null) {
                    // Hooray! The user is logged in.
                    createAlert(LoginActivity.this, "Congrats", "Login Successful", false);
                } else {
                    // Signup failed. Look at the ParseException to see what happened.
                    String msg = "";
                    try {
                        msg = e.getMessage();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        msg = "Login UnSuccessful";
                    }
                    if (CommonUtils.checkForNull(msg)) {
                        msg = "Login UnSuccessful";
                    }
                    createAlert(LoginActivity.this, "Error", msg, false);
                }
            }
        });

    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
    }


    public static void createAlert(final Context context, String title, String msg, final boolean finish) {
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


}
