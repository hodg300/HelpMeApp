package com.example.helpme;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class CustomerMain extends AppCompatActivity {
    private final String Hi="Hi ";
    private final String CUSTOMER_NAME="customerName";
    private Button cameraBtn;
    private TextView sendBtn;
    private ImageView returnPhoto;
    private TextView name;
    private boolean photoExists=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);
        initViews();
        getNameFromCustomerMain();
        clickToTakeAPhoto();
        sendAlertToWorker();
    }


    private void initViews(){
        cameraBtn =(Button)findViewById(R.id.cameraBTN);
        sendBtn   =(TextView)findViewById(R.id.sendBTN);
        returnPhoto=(ImageView)findViewById(R.id.photo_image_view);
        name=(TextView)findViewById(R.id.name_textView);
    }
    private void getNameFromCustomerMain(){
        name.setText(Hi + getIntent().getStringExtra(CUSTOMER_NAME));
    }

    private void clickToTakeAPhoto(){
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });
    }

    private void sendAlertToWorker() {
            sendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(photoExists) {
                        Toast.makeText(CustomerMain.this,
                                "Your request has been sent", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(CustomerMain.this,
                                "You must take a photo", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    //func for camera activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(data!=null) {
            super.onActivityResult(requestCode, resultCode, data);
            Bitmap bitmap=(Bitmap)data.getExtras().get("data");
            returnPhoto.setImageBitmap(bitmap);
            photoExists = true;
        }else{
            Intent intent=new Intent(this,CustomerMain.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
