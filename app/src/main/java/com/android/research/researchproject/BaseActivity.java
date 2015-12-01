package com.android.research.researchproject;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

/**
 * 
 * @author B0089798
 *
 */
public class BaseActivity extends FragmentActivity {
	public String TAG = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		LogUtils.logD(TAG, "onCreate");
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		LogUtils.logD(TAG, "onResume");
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
//		LogUtils.logD(TAG, "onPause");
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
//		LogUtils.logD(TAG, "onStart");
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
//		LogUtils.logD(TAG, "onStop");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		LogUtils.logD(TAG, "onDestroy");
	}



    ProgressDialog mDotsProgressDialog;

    public void showDotsProgress(Context context) {

        mDotsProgressDialog = new ProgressDialog(
                context);
        mDotsProgressDialog.setCancelable(false);
        mDotsProgressDialog.show();
        mDotsProgressDialog.setContentView(R.layout.progress_dots_anim);
        hangoutTvOne = (TextView) mDotsProgressDialog
                .findViewById(R.id.hangoutTvOne);
        hangoutTvTwo = (TextView) mDotsProgressDialog
                .findViewById(R.id.hangoutTvTwo);
        hangoutTvThree = (TextView) mDotsProgressDialog
                .findViewById(R.id.hangoutTvThree);
        waveAnimation();
    }

    public void cancelDotsProgress() {
//        LogUtils.logD(TAG, "cancelling mDotsProgressDialog");
        if (null != mDotsProgressDialog && mDotsProgressDialog.isShowing()) {
//            LogUtils.logD(TAG, "dismissing...");
            // progressDialog.dismiss();
            mDotsProgressDialog.cancel();
        }
    }

    private TextView hangoutTvOne;
    private TextView hangoutTvTwo;
    private TextView hangoutTvThree;
    private ObjectAnimator waveOneAnimator;
    private ObjectAnimator waveTwoAnimator;
    private ObjectAnimator waveThreeAnimator;

    public void waveAnimation() {
        PropertyValuesHolder tvOne_Y = PropertyValuesHolder.ofFloat(
                hangoutTvOne.TRANSLATION_Y, -40.0f);
        PropertyValuesHolder tvOne_X = PropertyValuesHolder.ofFloat(
                hangoutTvOne.TRANSLATION_X, 0);
        waveOneAnimator = ObjectAnimator.ofPropertyValuesHolder(hangoutTvOne,
                tvOne_X, tvOne_Y);
        waveOneAnimator.setRepeatCount(-1);
        waveOneAnimator.setRepeatMode(ValueAnimator.REVERSE);
        waveOneAnimator.setDuration(300);
        waveOneAnimator.start();

        PropertyValuesHolder tvTwo_Y = PropertyValuesHolder.ofFloat(
                hangoutTvTwo.TRANSLATION_Y, -40.0f);
        PropertyValuesHolder tvTwo_X = PropertyValuesHolder.ofFloat(
                hangoutTvTwo.TRANSLATION_X, 0);
        waveTwoAnimator = ObjectAnimator.ofPropertyValuesHolder(hangoutTvTwo,
                tvTwo_X, tvTwo_Y);
        waveTwoAnimator.setRepeatCount(-1);
        waveTwoAnimator.setRepeatMode(ValueAnimator.REVERSE);
        waveTwoAnimator.setDuration(300);
        waveTwoAnimator.setStartDelay(100);
        waveTwoAnimator.start();

        PropertyValuesHolder tvThree_Y = PropertyValuesHolder.ofFloat(
                hangoutTvThree.TRANSLATION_Y, -40.0f);
        PropertyValuesHolder tvThree_X = PropertyValuesHolder.ofFloat(
                hangoutTvThree.TRANSLATION_X, 0);
        waveThreeAnimator = ObjectAnimator.ofPropertyValuesHolder(
                hangoutTvThree, tvThree_X, tvThree_Y);
        waveThreeAnimator.setRepeatCount(-1);
        waveThreeAnimator.setRepeatMode(ValueAnimator.REVERSE);
        waveThreeAnimator.setDuration(300);
        waveThreeAnimator.setStartDelay(200);
        waveThreeAnimator.start();
    }


}
