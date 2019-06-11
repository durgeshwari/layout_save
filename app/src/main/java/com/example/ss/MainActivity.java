package com.example.ss;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import static android.content.ContentValues.TAG;


public class MainActivity extends Activity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl);
        final Button btn = (Button) findViewById(R.id.btn);
//
//        ActionBar ab = getActionBar();
//        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF54A9EB")));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isWriteStoragePermissionGranted();
                // Take the screenshot
                Bitmap screenShot = TakeScreenShot(rl);

                if(screenShot!=null){
                    MediaStore.Images.Media.insertImage(getContentResolver(),screenShot,
                            "IMAGE","Captured Screenshot");

                 }

                Toast.makeText(getApplicationContext(), "Screen Captured.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted2");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked2");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        } else {
            //permission is automatically granted on sdk<23 upon installation
            //sdk <23 ise otomatik olarak izin verilir
            Log.v(TAG, "Permission is granted2");
            return true;
        }

    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2:
                Log.d(TAG, "External storage2");
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
                    // resume tasks needing this permission


                } else {
                }
                break;

            case 3:
                Log.d(TAG, "External storage1");
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
                    //resume tasks needing this permission

                } else {
                }
                break;
        }}



    // Custom method to take screenshot
    public Bitmap TakeScreenShot(View rootView)
    {

        Bitmap bitmap = Bitmap.createBitmap(rootView.getWidth(),rootView.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        rootView.draw(canvas);
        return bitmap;
    }
}