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

import java.util.ArrayList;

public class CustomerLogIn extends AppCompatActivity {
    private final String CUSTOMER_NAME="customerName";
    private final String NAME_OF_PLACE="nameOfPlace";
    private Button connectBtn;
    private TextView nameEditText;
    private ListView listView;
    private boolean isChoosePlace=false;
    private String nameOfChosePlace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_log_in);
        initViews();
        choosePlace();
        startCustomerLogInActivity();
    }



    private void initViews(){
        connectBtn=(Button)findViewById(R.id.customerConnectBTN);
        nameEditText=(EditText)findViewById(R.id.customerEditName);
        listView=(ListView)findViewById(R.id.listView);
        createListViews();
    }

    private void createListViews() {
        ArrayList<String> arrayList=new ArrayList<>();
        arrayList.add("Renuar");
        arrayList.add("Castro");
        arrayList.add("Zara");
        ArrayAdapter arrayAdapter=
                new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);

    }

    private void choosePlace() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nameOfChosePlace = listView.getItemAtPosition(position).toString();
                view.setBackgroundColor(Color.CYAN);
                isChoosePlace=true;
                Log.d("test", "onItemClick: " + nameOfChosePlace);
            }
        });
    }

    private void startCustomerLogInActivity(){
        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isChoosePlace) {
                    Intent intent = new Intent(CustomerLogIn.this, CustomerMain.class);
                    intent.putExtra(CUSTOMER_NAME, nameEditText.getText().toString());
                    intent.putExtra(NAME_OF_PLACE,nameOfChosePlace);
                    startActivity(intent);
                }else{
                    Toast.makeText(CustomerLogIn.this,
                            "Choose the place where you are", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}
