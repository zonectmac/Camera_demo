package com.example.camera_demo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageShowActivity extends Activity {
	private ImageView imageView1;
	public static final String PATH_IAMGE = "image";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_show);
		imageView1 = (ImageView) findViewById(R.id.imageView1);
		Intent intent = getIntent();
		String pathName = intent.getStringExtra(PATH_IAMGE);
		// Bitmap bitmap = BitmapFactory.decodeFile(pathName);
		Bitmap bitmap = BitmapLinUtils.getImage(pathName, imageView1);
		System.out.println("---------bitmap-" + bitmap.getWidth());
		System.out.println("---------bitmap-" + bitmap.getHeight());
		imageView1.setImageBitmap(bitmap);
	}
}
