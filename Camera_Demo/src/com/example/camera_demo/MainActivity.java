package com.example.camera_demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {

	private Camera camera;
	private SurfaceView surfaceView1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		findViewById(R.id.button1).setOnClickListener(this);
		surfaceView1 = (SurfaceView) findViewById(R.id.surfaceView1);
		surfaceView1.getHolder().addCallback(callBack);

	}

	@Override
	protected void onResume() {
		super.onResume();
		camera = Camera.open();// 打开相机
	}

	private SurfaceHolder.Callback callBack = new SurfaceHolder.Callback() {

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			camera.stopPreview();
			camera.release();
			camera = null;
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			try {
				camera.setPreviewDisplay(holder);// 用于为相机指定一个用来显示相机预览画面的SurfaceView
				camera.setDisplayOrientation(90);// 使相机旋转90度
				camera.startPreview();// 开始预览
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {

		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			camera.takePicture(null, null, new PictureCallback() {

				private Bitmap bitmap;

				@Override
				public void onPictureTaken(byte[] data, Camera camera) {
					bitmap = BitmapFactory
							.decodeByteArray(data, 0, data.length);
					System.out.println("----bitmap:" + bitmap.getWidth());
					System.out.println("----bitmap:" + bitmap.getHeight());
					Matrix matrix = new Matrix();
					matrix.setRotate(90);
					Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0,
							bitmap.getWidth(), bitmap.getHeight(), matrix, true);
					System.out.println("----bitmap2:" + bitmap2.getWidth());
					System.out.println("----bitmap2:" + bitmap2.getHeight());
					File file = new File(Environment
							.getExternalStorageDirectory().getPath()
							+ "/tupian/at15.jpg");
					if (!file.exists()) {
						file.getParentFile().mkdirs();
					}
					try {
						FileOutputStream fos = new FileOutputStream(file);
						bitmap2.compress(CompressFormat.JPEG, 80, fos);
						Intent intent = new Intent(MainActivity.this,
								ImageShowActivity.class);
						intent.putExtra(ImageShowActivity.PATH_IAMGE,
								file.getPath());
						startActivity(intent);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			});
			break;
		}
	}
}
