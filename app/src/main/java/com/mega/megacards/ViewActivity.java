package com.mega.megacards;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

public class ViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_view);

        String fileName = getIntent().getExtras().getString("fileName");
        Bitmap bitmap = BitmapFactory.decodeFile(fileName, null);
        ImageView imageView = getWindow().getDecorView().findViewById(R.id.viewimage);
        if(imageView != null) {
            imageView.setImageBitmap(bitmap);
        }
    }
}