package com.android.research.researchproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.research.researchproject.utility.CameraUtilities;
import com.android.research.researchproject.utility.CommonUtils;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by B0089798 on 11/21/2015.
 */
public class UserDetailsActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private Button mBtnSkip, mBtnSave, mBtnTakePicture;
    private EditText mEtUserName, mEtEmailId, mEtPhoneNumber, mEtLocation;
    private ProfilePictureView mImageViewProfilePic;
    private ImageView mImageViewProfilePiccam;
    Bundle bundle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        bundle = getIntent().getBundleExtra("bundle");
        findViewsByIds();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        LoginManager.getInstance().logOut();
        Intent back = new Intent(UserDetailsActivity.this, MainActivity.class);
        back.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(back);
    }


    private void findViewsByIds() {
        mImageViewProfilePic = (ProfilePictureView) findViewById(R.id.imv_profile_pic);
        mImageViewProfilePic.setVisibility(View.VISIBLE);
        mImageViewProfilePiccam = (ImageView) findViewById(R.id.imv_profile_pic_cam);
        mImageViewProfilePic.setProfileId(bundle.getString("profilePic"));
        mBtnSave = (Button) findViewById(R.id.btn_save);
        mBtnSave.setOnClickListener(this);
        mBtnSkip = (Button) findViewById(R.id.btn_skip);
        mBtnSkip.setOnClickListener(this);
        mBtnTakePicture = (Button) findViewById(R.id.btn_take_picture);
        mBtnTakePicture.setOnClickListener(this);
        mEtPhoneNumber = (EditText) findViewById(R.id.et_phone);
        mEtPhoneNumber.setText(bundle.getString("phone"));
        mEtEmailId = (EditText) findViewById(R.id.et_email);
        mEtEmailId.setText(bundle.getString("email"));
        mEtUserName = (EditText) findViewById(R.id.et_user_name);
        mEtUserName.setText(bundle.getString("name"));
        mEtLocation = (EditText) findViewById(R.id.et_location);
        //toolbar work
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        TextView back = (TextView) mToolbar.findViewById(R.id.back);
        back.setText("Home");
        TextView logout = (TextView) mToolbar.findViewById(R.id.logout);
        logout.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_click_here:
                startLoginActivity();
                break;

            case R.id.btn_signup:

                break;

            case R.id.back:
                Toast.makeText(UserDetailsActivity.this, "Home", Toast.LENGTH_LONG).show();
                openHomeScreen();
                break;

            case R.id.btn_save:

                Intent in = new Intent(this, DummyHomeScreen.class);
                in.putExtra("bundle", bundle);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                ((Activity) this).startActivity(in);
                ((Activity) this).finish();
                //saveUserDetail();
                break;

            case R.id.btn_skip:
//                openHomeScreen();
                Toast.makeText(UserDetailsActivity.this, "Skip", Toast.LENGTH_LONG).show();
                break;

            case R.id.btn_take_picture:
                Toast.makeText(UserDetailsActivity.this, "Take picture", Toast.LENGTH_LONG).show();
                takePicture();
                break;
            case R.id.logout:
                LoginManager.getInstance().logOut();
                Intent login = new Intent(UserDetailsActivity.this, MainActivity.class);
                login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(login);
                break;
        }
    }

    private void saveUserDetail() {
        validateInput();
    }


    /**
     * package - Uri for images
     */
    private Uri mRequestPicUri, mCurrentPicUri, mCroppedPicUri;


    private void takePicture() {
        mRequestPicUri = CameraUtilities.capturePicture(UserDetailsActivity.this);
    }

    private void startLoginActivity() {
//        Intent in = new Intent(UserDetailsActivity.this, LoginActivity.class);
//        startActivityForResult(in, 100);
//        finish();
    }

    private void validateInput() {
        String phoneNumber = mEtPhoneNumber.getText().toString();
        String email = mEtEmailId.getText().toString();
        String userName = mEtUserName.getText().toString();

        boolean isValid = true;
        String msg = "";


        if (!CommonUtils.checkForNull(userName)) {
            isValid = false;
            msg = "User Name is invalid .";
            mEtUserName.setError(msg);
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


        if (isValid && !CommonUtils.checkForNull(phoneNumber)) {
            isValid = false;
            msg = "Phone Number is mandatory.";
            mEtPhoneNumber.setError(msg);
        } else if (isValid && !CommonUtils.isValidPhoneNumber(phoneNumber)) {
            isValid = false;
            msg = "invalid Phone Number.";
            mEtPhoneNumber.setError(msg);
        }


        if (isValid && !CommonUtils.checkForNull(phoneNumber)) {
            isValid = false;
            msg = "Phone Number is mandatory.";
            mEtPhoneNumber.setError(msg);
        } else if (isValid && !CommonUtils.isValidPhoneNumber(phoneNumber)) {
            isValid = false;
            msg = "invalid Phone Number.";
            mEtPhoneNumber.setError(msg);
        }


        try {
            File file = new File(mCroppedPicUri.getPath());
            if (!file.exists()) {
//                isValid = false;
//                Toast.makeText(UserDetailsActivity.this, "Please click a picture", Toast.LENGTH_LONG).show();
            }
        } catch (NullPointerException e) {
//            isValid = false;
//            Toast.makeText(UserDetailsActivity.this, "Please click a picture", Toast.LENGTH_LONG).show();
        }


        if (isValid) {
            Toast.makeText(UserDetailsActivity.this, "saveUserDetail", Toast.LENGTH_LONG).show();
//            openHomeScreen();
           // saveDetails();
        }


    }

    private void saveDetails() {
        ParseObject gameScore = new ParseObject("UserDetail");
        gameScore.put("username", mEtUserName.getText().toString());
        gameScore.put("email", mEtEmailId.getText().toString());
        gameScore.put("phone", mEtPhoneNumber.getText().toString());
        gameScore.put("location", mEtLocation.getText().toString());
        gameScore.saveInBackground();
        try {
            getDetails(gameScore);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getDetails(ParseObject gameScore) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameScore");
        query.getInBackground(gameScore.getObjectId(), new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    // object will be your UserDetail
                } else {
                    // something went wrong
                }
            }
        });
    }

    private void openHomeScreen() {
      //  Toast.makeText(this, "SignUp Successfully done!!!", Toast.LENGTH_LONG).show();
        Intent in = new Intent(this, DummyHomeScreen.class);
        in.putExtra("bundle", bundle);
        ((Activity) this).startActivity(in);
        ((Activity) this).finish();
//        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        {
            try {
                if (resultCode == RESULT_OK) {
                    if (requestCode == CameraUtilities.CAMERA_CAPTURE) {
                        if (data != null && data.getData() != null) {
                            mCurrentPicUri = data.getData();
                        } else {
                            mCurrentPicUri = mRequestPicUri;
                        }
                        if (mCurrentPicUri != null) {
                            try {
                                mCroppedPicUri = CameraUtilities.performCrop(UserDetailsActivity.this, mCurrentPicUri);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (requestCode == CameraUtilities.PIC_CROP) {
                        mImageViewProfilePiccam.setVisibility(View.VISIBLE);
                        mImageViewProfilePic.setVisibility(View.GONE);
                        mImageViewProfilePiccam.setImageURI(mCroppedPicUri);
//                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mCroppedPicUri);

//                        mImageViewProfilePic.setDefaultProfilePicture(bitmap);
                        mCurrentPicUri = mCroppedPicUri;

                    }
                } else {
                    setResult(RESULT_CANCELED);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Bitmap loadBitmap(String url) {
        Bitmap bm = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        try {
            URLConnection conn = new URL(url).openConnection();
            conn.connect();
            is = conn.getInputStream();
            bis = new BufferedInputStream(is, 8192);
            bm = BitmapFactory.decodeStream(bis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bm;
    }
}

