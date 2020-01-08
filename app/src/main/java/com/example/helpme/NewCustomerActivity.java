package com.example.helpme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class NewCustomerActivity extends AppCompatActivity {
    private final String TAG="NewCustomerActivity";
    private final String VERIFY_CODE="Verify Code";
    private Button send_verification_code_btn;
    private Button verify_code_btn;
    private EditText cellPhoneNumber;
    private EditText verifyEditText;
    private EditText prefix;
    private ProgressBar progress_bar_phone_num;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;
    private FirebaseAuth mFireBaseAuth;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer);

        //init FireBase
        mFireBaseAuth=FirebaseAuth.getInstance();
        mFireBaseAuth.setLanguageCode("fr");
        initViews();
        init_mCallBacks();
        buttonListeners();


    }



    private void initViews(){
        send_verification_code_btn=(Button)findViewById(R.id.send_verification_code_btn);
        verify_code_btn=(Button)findViewById(R.id.verify_code_btn);
        verify_code_btn.setEnabled(false);
        verify_code_btn.setVisibility(View.INVISIBLE);
        verifyEditText=(EditText)findViewById(R.id.verify_edit_text);
        verifyEditText.setVisibility(View.INVISIBLE);
        prefix=(EditText)findViewById(R.id.prefix);
        cellPhoneNumber=(EditText)findViewById(R.id.phone_num);
        progress_bar_phone_num=(ProgressBar)findViewById(R.id.progress_bar_phone_num);
        progress_bar_phone_num.setVisibility(View.INVISIBLE);

    }


    private void init_mCallBacks(){
        mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

                signInWithPhoneAuthCredential(credential);


            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                super.onCodeSent(verificationId,token);
                if(verificationId!=null){
                    progress_bar_phone_num.setVisibility(View.INVISIBLE);
                }
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                verifyEditText.setVisibility(View.VISIBLE);
                send_verification_code_btn.setVisibility(View.INVISIBLE);
                send_verification_code_btn.setEnabled(false);
                verify_code_btn.setVisibility(View.VISIBLE);
                verify_code_btn.setEnabled(true);

            }
        };
    }



    private void buttonListeners(){

        send_verification_code_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendVerificationCode();
                }
            });

        verify_code_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifySignIn();
            }
        });

    }




    private void sendVerificationCode(){
        if ((prefix.getText().toString() + cellPhoneNumber.getText().toString()).length() >= 10) {
            cellPhoneNumber.setEnabled(false);
            prefix.setEnabled(false);
            progress_bar_phone_num.setVisibility(View.VISIBLE);
            Log.d(TAG, "sendVerificationCode: immmhereee");
            String cellPhoneNum = prefix.getText().toString() + cellPhoneNumber.getText().toString();
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    cellPhoneNum,        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    NewCustomerActivity.this,               // Activity (for callback binding)
                    mCallBacks);        // OnVerificationStateChangedCallbacks/

        } else if ((prefix.getText().toString() + cellPhoneNumber.getText().toString()).length() < 10) {
            cellPhoneNumber.setError("Please enter your cellPhone number");
            cellPhoneNumber.requestFocus();
        }
    }


    private void verifySignIn(){
        String code = verifyEditText.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mFireBaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();

                            Toast.makeText(NewCustomerActivity.this,
                                    "Login Successful", Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(NewCustomerActivity.this,ListPlacesActivity.class);
                            startActivity(intent);
                            finish();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getApplicationContext(),
                                        "Incorrect Verification Code ", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

}
