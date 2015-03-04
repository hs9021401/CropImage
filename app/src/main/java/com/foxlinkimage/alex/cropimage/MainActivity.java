package com.foxlinkimage.alex.cropimage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;


public class MainActivity extends ActionBarActivity {
    ImageView img;
    Button btnCrop;
    File file;
    private static final int PHOTO_REQUEST_CUT = 2;// 從相冊中選擇

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = (ImageView)findViewById(R.id.img);
        btnCrop = (Button)findViewById(R.id.btnCrop);
        btnCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File pic_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String name = "20150225_101325.jpg";
                file = new File(pic_path,name);

                Uri uri2 = null;
                uri2.fromFile(file);

                crop(uri2);
            }
        });
    }

    private void crop(Uri uri)
    {
        Intent it = new Intent("com.android.camera.action.CROP");
        it.setDataAndType(uri,"image/*");
        it.putExtra("crop",true);
        it.putExtra("aspectX",4);
        it.putExtra("aspectY",3);
        it.putExtra("outputFormat", "JPEG");
        it.putExtra("return-data", false);
        startActivityForResult(it, PHOTO_REQUEST_CUT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch(requestCode)
        {
            case PHOTO_REQUEST_CUT:
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    Bitmap photo = bundle.getParcelable("data");
                    if (photo == null)
                    {
                        Toast.makeText(MainActivity.this,"Null photo",Toast.LENGTH_SHORT).show();
                    }else
                    {
                        img.setImageBitmap(photo);
                    }

                }
                break;

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
