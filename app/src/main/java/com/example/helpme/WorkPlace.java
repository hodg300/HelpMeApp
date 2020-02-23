package com.example.helpme;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class WorkPlace implements Comparable {
    private final String UPLOADS = "uploads";
    private String name;
    private String code;
    private int numOfWorkers = 0;
    private ArrayList<Call> uploads;
    private Employee manager;
    private Map<String,Employee> employees;

    public WorkPlace(String name, String code, int maxWorkers, Employee manager, ArrayList<Call> uploads) {
        this.code = code;
        this.name = name;
        this.uploads = uploads;
        this.manager = manager;
        employees = new HashMap<>();
    }

    public WorkPlace() {

    }

    public void addWorker(Employee e){
        if(employees==null)
            employees = new HashMap<>();
        employees.put(e.getId(),e);
        numOfWorkers++;
    }

    public void removeWorker(Employee e){
        employees.remove(e.getId());
        numOfWorkers--;
    }

    public void addCall(String customerPhone, ImageView pic, Uri mImap){
        Bitmap bitmap = ((BitmapDrawable) pic.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        StartActivity.storageRef.child(this.name).child("workPlaceCalls").child(customerPhone).putBytes(data);
        Log.d("miamp", "addCall: " + mImap);
        StartActivity.mDatabaseReferencePlaces.child(this.name).child(UPLOADS).setValue(new Call(customerPhone,mImap));
        if(employees==null){
            return;
        }
        for(Employee e : employees.values()){
            sendAlert(e);
        }
    }

    private void sendAlert(Employee e) {
        //send alert all over the employee
    }

    public String getName() {
        return name;
    }

    public Collection<Employee> getEmployees() {
        if(employees!=null)
            return employees.values();
        else
            return null;
    }

    public void setEmployees(Map<String, Employee> employees) {
        this.employees = employees;
    }


    public Employee getManager() {
        return manager;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public int getNumOfWorkers() {
        return numOfWorkers;
    }

    public ArrayList<Call> getWorkPlaceCalls() {
        return uploads;
    }

    public void responseCall(Employee worker){
        //uploads.g().Response(worker.getName());
    }

    @Override
    public int compareTo(Object place) {
        WorkPlace other = (WorkPlace) place;
        if (this.name.charAt(0) > other.name.charAt(0))
            return 1;
        else
            return -1;
    }

}
