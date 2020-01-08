package com.example.helpme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {
    private Button customerBtn;
    private Button workerBtn;
    private Button aboutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        startIntents();


    }
    private void initViews(){
        customerBtn=(Button)findViewById(R.id.customerBTN);
        workerBtn=(Button)findViewById(R.id.workerBTN);
        aboutBtn=(Button)findViewById(R.id.aboutBTN);

    }
    private void startIntents(){
        customerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StartActivity.this,CustomerLogIn.class);
                startActivity(intent);
            }
        });
        workerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StartActivity.this,WorkerLogIn.class);
                startActivity(intent);
            }
        });
        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StartActivity.this,About.class);
                startActivity(intent);
            }
        });


    }
}
