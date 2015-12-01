package com.android.research.researchproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.research.researchproject.utility.CommonUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

/**
 * Created by B0089798 on 11/26/2015.
 */
public class ForgotPasswordActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private Button mBtnSingIn;
    private Bundle bundle;
    private EditText mEtEmailId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        try {
            bundle = getIntent().getBundleExtra("bundle");
        } catch (Exception e) {
            e.printStackTrace();
        }
        findViewsById();
    }

    private void findViewsById() {

        mEtEmailId = (EditText) findViewById(R.id.et_email);

        try {
            if (null != bundle)
                mEtEmailId.setText(bundle.getString("email"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //toolbar work
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        TextView back = (TextView) mToolbar.findViewById(R.id.back);
        back.setOnClickListener(this);
        TextView logout = (TextView) mToolbar.findViewById(R.id.logout);
        logout.setText("");
        mBtnSingIn = (Button) findViewById(R.id.btn_req_new_pass);
        mBtnSingIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_req_new_pass:

                String msg = "";
                String email = mEtEmailId.getText().toString();
                if (!CommonUtils.checkForNull(email)) {

                    msg = "Email mandatory.";
                    mEtEmailId.setError(msg);
                } else if (!CommonUtils.isEmailValid(email)) {
                    msg = "Email Invalid.";
                    mEtEmailId.setError(msg);
                } else {

                    requestResetPassword(mEtEmailId.getText().toString());

                }

                break;


            case R.id.back:
                finish();
                break;
        }
    }

    public static final String FORGOT_PASS_MSG = "msg";

    private void requestResetPassword(String emailId) {
        showDotsProgress(ForgotPasswordActivity.this);
        ParseUser.requestPasswordResetInBackground(emailId,
                new RequestPasswordResetCallback() {
                    public void done(ParseException e) {
                        cancelDotsProgress();
                        if (e == null) {
                            // An email was successfully sent with reset instructions.
                            finishWithResult("Congratulations");
                        } else {
                            // Something went wrong. Look at the ParseException to see what's up.
                            Toast.makeText(ForgotPasswordActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void finishWithResult(String message) {
//        Bundle conData = new Bundle();
        bundle.putString(FORGOT_PASS_MSG, message);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(111, intent);
        finish();
    }
}
