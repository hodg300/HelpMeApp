package com.example.helpme;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.Serializable;

public class Employee implements Serializable {
    private String name;
    private String id;
    private String token;
    private String phone;
    private boolean isConnected = false;
    private WorkPlace workPlace;
    private String Uid;

    public Employee(String name, String mail, String phone) {
        this.name = name;
        this.id = mail;
        this.phone = phone;
        //token of employee
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if(task.isSuccessful()){
                    token = task.getResult().getToken();
                }
                else
                {}
            }
        });
    }

    public Employee(String mail, String token) {
        this.id = mail;
        this.token = token;
    }

    public Employee() {

    }

    private void createCellPhoneNumber(String phone) {
        if(phone!=null) {
            String number = phone.trim();
            if (number.isEmpty() || number.length() < 10) {
                this.phone = "";
            }
            if (number.charAt(0) == '0') {
                number = number.substring(1);
            }
            this.phone = "+972" + number;
        }
    }

    public void setToken (String token){
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setUid(String uid){this.Uid = uid;}

    public String getUid() {
        return Uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken(){return token;}

    public void setPhone (String num){
        phone +=  num;
    }

    public String getPhone(){
        return phone;
    }

    public String getId() {
        return id;
    }

    public WorkPlace getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(WorkPlace workPlace) {
        this.workPlace = workPlace;
    }

    public void setId(String id) {
        this.id = id;
    }
}
