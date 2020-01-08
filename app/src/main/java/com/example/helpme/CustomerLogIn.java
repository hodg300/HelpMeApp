package com.example.helpme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class CustomerLogIn extends AppCompatActivity {

    private Button connectBtn;



    private EditText cellPhoneNumber;
    private FirebaseAuth mFireBaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_log_in);
        initViews();
        connectAndStartListPlacesActivity();
    }



    private void initViews(){
        connectBtn=(Button)findViewById(R.id.customerConnectBTN);

        cellPhoneNumber=(EditText)findViewById(R.id.phone_num);
        mFireBaseAuth=FirebaseAuth.getInstance();

    }



    private void connectAndStartListPlacesActivity(){
        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cellPhoneNumber.getText().toString().length()>=10) {
                    Intent intent = new Intent(CustomerLogIn.this, ListPlacesActivity.class);
                    startActivity(intent);
                }else if(cellPhoneNumber.getText().toString().length()<10){
                    cellPhoneNumber.setError("Please enter your cellPhone number");
                    cellPhoneNumber.requestFocus();
                }
                else{
                    Toast.makeText(CustomerLogIn.this,
                            "Choose the place where you are", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}
