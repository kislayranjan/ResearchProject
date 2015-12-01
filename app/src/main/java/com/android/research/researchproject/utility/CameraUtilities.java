package com.android.research.researchproject.utility;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraUtilities {

	private static final String TAG = CameraUtilities.class.getSimpleName();
	public static final  int CAMERA_CAPTURE = 1;
	public static final  int PIC_CROP = 2;
	public static final  int PIC_SIZE = 1024;
	public static final String PIC_EXTRA = "image";

	/**
	 * Create a file Uri for saving an image or video
	 */
	public static Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	/**
	 * Create a File for saving an image or video
	 */
	private static File getOutputMediaFile(int type) {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

		File mediaStorageDir = new File(
				Environment.getExternalStorageDirectory(), "PromoterApp");
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.e(TAG, "failed to create directory");
				return null;
			}
		}

		File noMediaFile = new File(Environment.getExternalStorageDirectory()
				+ File.separator + "promoter" + File.separator + ".nomedia");

		if (!noMediaFile.exists()) {
			try {
				if (!noMediaFile.createNewFile()) {
					Log.e(TAG, "failed to create file");
				}
			} catch (IOException e) {
				Log.e(TAG, "failed to create file");
			}
		}
		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		File mediaFile;
		// if (type == MEDIA_TYPE_IMAGE){
		mediaFile = new File(mediaStorageDir.getPath() + File.separator
				+ "IMG_" + timeStamp + ".jpg");
		return mediaFile;
	}
	

	public static Uri performCrop(Activity context, Uri currentPicUri ) {
		{
			Uri croppedPicUri = null;
			try {
				Intent cropIntent = new Intent("com.android.camera.action.CROP");
				// indicate image type and Uri
				cropIntent.setDataAndType(currentPicUri, "image/*");
				// set crop properties
				cropIntent.putExtra("crop", true);
				cropIntent.putExtra("scale", true);
				// indicate output X and Y
				cropIntent.putExtra("outputX", PIC_SIZE);
				cropIntent.putExtra("outputY", PIC_SIZE);
				// retrieve data on return
				croppedPicUri = CameraUtilities.getOutputMediaFileUri(0);
				cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, croppedPicUri);
				context.startActivityForResult(cropIntent, PIC_CROP);
//				context.overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
			} catch (ActivityNotFoundException anfe) {
				Toast.makeText(context, "Take picture", Toast.LENGTH_LONG).show();
			}
			return croppedPicUri;
		}
	}

	public static Uri performCropFromFragment(Fragment context, Uri currentPicUri ) {
		{
			Uri croppedPicUri = null;
			try {
				Intent cropIntent = new Intent("com.android.camera.action.CROP");
				// indicate image type and Uri
				cropIntent.setDataAndType(currentPicUri, "image/*");
				// set crop properties
				cropIntent.putExtra("crop", true);
				cropIntent.putExtra("scale", true);
				// indicate output X and Y
				cropIntent.putExtra("outputX", PIC_SIZE);
				cropIntent.putExtra("outputY", PIC_SIZE);
				// retrieve data on return
				croppedPicUri = CameraUtilities.getOutputMediaFileUri(0);
				cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, croppedPicUri);
				context.startActivityForResult(cropIntent, PIC_CROP);
//				context.overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
			} catch (ActivityNotFoundException anfe) {
				Toast.makeText(context.getActivity(), "Take picture", Toast.LENGTH_LONG).show();
			}
			return croppedPicUri;
		}
	}

	public static Uri capturePicture(Activity context) {
		Uri requestPicUri = null;
		Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		requestPicUri = CameraUtilities.getOutputMediaFileUri(0);
		captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, requestPicUri);
		captureIntent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, PIC_SIZE);
		captureIntent.putExtra("outputX", PIC_SIZE);
		captureIntent.putExtra("outputY", PIC_SIZE);
		captureIntent.putExtra("aspectX", PIC_SIZE);
		captureIntent.putExtra("aspectY", PIC_SIZE);
		captureIntent.putExtra("scale", true);
		captureIntent.putExtra("scaleUpIfNeeded", false);
		context.startActivityForResult(captureIntent, CAMERA_CAPTURE);
//		context.overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
		return requestPicUri;
	}



	public static Uri capturePictureFromFragment(Fragment context) {
		Uri requestPicUri = null;
		Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		requestPicUri = CameraUtilities.getOutputMediaFileUri(0);
		captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, requestPicUri);
		captureIntent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, PIC_SIZE);
		captureIntent.putExtra("outputX", PIC_SIZE);
		captureIntent.putExtra("outputY", PIC_SIZE);
		captureIntent.putExtra("aspectX", PIC_SIZE);
		captureIntent.putExtra("aspectY", PIC_SIZE);
		captureIntent.putExtra("scale", true);
		captureIntent.putExtra("scaleUpIfNeeded", false);
		context.startActivityForResult(captureIntent, CAMERA_CAPTURE);
//		context.overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
		return requestPicUri;
	}
}
