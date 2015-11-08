package uk.org.urbanroots.www.myapplication;


import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;

public class Camer extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private Button uploadButton;
    Bitmap photo;
    Uri img=null;
    String fileName = "photo";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camer);
        imageView = (ImageView) findViewById(R.id.imageView1);

        uploadButton = (Button) findViewById(R.id.uploadButton);

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mail skata =new Mail();
              try {
                    skata.send(Camer.this.getFilesDir().getPath()+"/"+fileName);
                } catch (Exception e) {
                    e.printStackTrace();
                }



            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            createDirectoryAndSaveFile(photo,fileName);
        }
    }

    private void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) {

       // File direct = new File(Environment.getExternalStorageDirectory() + "/DirName");
    //  File direct = new File(this.getFilesDir(), "photo");


        File file = new File(new File(this.getFilesDir().getPath()), fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(Camer.this, MainActivity.class);
        Camer.this.startActivity(intent);
    }
}


