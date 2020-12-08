package com.nayan.takingpic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_CODE = 1000 ;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    Button clk;
    ImageView imv;
    TextView tv;
    Uri image_uri;
    ImageView m_ivCaptureImage;
    String m_curentDateandTime;
    String m_imagePath;
    Bitmap m_bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clk=(Button) findViewById(R.id.click);
        imv = (ImageView) findViewById(R.id.img);
        tv = (TextView) findViewById(R.id.tv);



        clk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED|| checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED)
                    {
                        String[] perission = {Manifest.permission.CAMERA , Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(perission,PERMISSION_CODE);
                    }
                    else {
                        //permission already granted
                        openCamera();
                        

                    }
                }
                else {
                    //system os< marshmellow

                }
            }
        });
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);

    }



    //handling permission

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openCamera();
                }
                else {
                    Toast.makeText(this,"permission denied",Toast.LENGTH_LONG).show();

                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK){
            if (data != null) {
                m_bitmap = (Bitmap) data.getExtras().get("data");
                imv.setImageBitmap(m_bitmap); /* this is image view where you want to set image*/
            }
        }
    }

}
