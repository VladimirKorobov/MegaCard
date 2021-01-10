package com.mega.megacards;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class ViewActivity extends AppCompatActivity {

    float dencity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dencity =  getResources().getDisplayMetrics().density;;
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_view);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        String fileName = getIntent().getExtras().getString("fileName");
        String title = getIntent().getExtras().getString("Title");
        setTitle(title);
        Bitmap bitmap = BitmapFactory.decodeFile(fileName, null);
        ImageView imageView = getWindow().getDecorView().findViewById(R.id.viewimage);
        if(imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            if(bitmap.getWidth() > bitmap.getHeight()) {
                float scale = (float)bitmap.getWidth() / bitmap.getHeight();
                imageView.setRotation(90);
                imageView.setScaleX(scale);
                imageView.setScaleY(scale);
            }
            imageView.setImageBitmap(bitmap);
        }
    }
}