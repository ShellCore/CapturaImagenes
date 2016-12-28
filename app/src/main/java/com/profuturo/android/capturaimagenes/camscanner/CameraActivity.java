package com.profuturo.android.capturaimagenes.camscanner;


import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;

import com.profuturo.android.capturaimagenes.R;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;



public class CameraActivity extends FragmentActivity {

	private Camera camera;
	private CameraPreview mPreview;
	private PhotoHandler photoHandler;

	private Button btnCapture;

	public static final String DEBUG_TAG = "CamActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera_layout);

		btnCapture = (Button) findViewById(R.id.btnCaptura);

		// Create an instance of Camera
		camera = isCameraAvailiable();

		// Create our Preview view and set it as the content of our activity.
		mPreview = new CameraPreview(this, camera);
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		preview.addView(mPreview);
		
		photoHandler = new PhotoHandler();
		
		btnCapture.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if (camera != null) {
					camera.takePicture(null, null, photoHandler);
				}
			}
		});
	}

	@Override
	protected void onResume() {
		if (camera == null) {
			isCameraAvailiable();
		}
		super.onResume();
	}

	@Override
	protected void onPause() {
		if (camera != null) {
			camera.release();
			camera = null;
		}
		
		super.onPause();
	}

	public static Camera isCameraAvailiable() {
		Camera object = null;
		try {
			object = Camera.open();
		} catch (Exception e) {
		}
		return object;
	}

	public class PhotoHandler implements PictureCallback {
		private String imgUrl;

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			File pictureFileDir = getDir();

			if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {
				return;
			}

			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyymmddhhmmss", Locale.US);
			String date = dateFormat.format(new Date());
			String photoFile = "Picture_" + date + ".jpg";

			String filename = pictureFileDir.getPath() + File.separator
					+ photoFile;
			imgUrl = filename;

			File pictureFile = new File(filename);

			try {
				FileOutputStream fos = new FileOutputStream(pictureFile);
				fos.write(data);
				fos.close();
				Intent i = new Intent();
				i.putExtra("imgUrl", photoHandler.getUrl());
				i.putExtra("imgNombre", photoFile);				
				setResult(RESULT_OK, i);
				finish();
			} catch (Exception error) {
			}
		}

		private File getDir() {
			return new File(Util.getDirectorioFotos(getApplication(), Constantes.DIRNAME));
		}
		

		public String getUrl() {
			return imgUrl;
		}
	}
}
