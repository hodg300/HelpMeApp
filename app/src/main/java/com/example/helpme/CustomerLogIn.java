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
import android.widget.TextView;
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

public class CustomerLogIn extends AppCompatActivity {
    private final String TAG="CustomerLogIn";
    private Button loginBtn;
    private EditText cellPhoneNumber;
    private EditText prefix;
    private ProgressBar progress_bar_phone_num;
    private FirebaseAuth mFireBaseAuth;
    private String mVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_log_in);

        mFireBaseAuth= FirebaseAuth.getInstance();
        initViews();
        init_mCallBacks();
        buttonListeners();
    }
    private void initViews() {
        loginBtn=(Button)findViewById(R.id.loginBtn);
        prefix=(EditText)findViewById(R.id.prefix);
        cellPhoneNumber=(EditText)findViewById(R.id.phone_num);
        progress_bar_phone_num=(ProgressBar)findViewById(R.id.progress_bar_phone_num);
        progress_bar_phone_num.setVisibility(View.INVISIBLE);

    }


    private void buttonListeners() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logIn();
            }
        });
    }

    private void init_mCallBacks() {
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
            }
        };
    }
    private void logIn(){
        if ((prefix.getText().toString() + cellPhoneNumber.getText().toString()).length() >= 10) {
            cellPhoneNumber.setEnabled(false);
            prefix.setEnabled(false);
            progress_bar_phone_num.setVisibility(View.VISIBLE);
            String cellPhoneNum = prefix.getText().toString() + cellPhoneNumber.getText().toString();
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    cellPhoneNum,        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    CustomerLogIn.this,               // Activity (for callback binding)
                    mCallBacks);        // OnVerificationStateChangedCallbacks/

        } else if ((prefix.getText().toString() + cellPhoneNumber.getText().toString()).length() < 10) {
            cellPhoneNumber.setError("Please enter your cellPhone number");
            cellPhoneNumber.requestFocus();
        }
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

                            Toast.makeText(CustomerLogIn.this,
                                    "Code verification and login succeeded", Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(CustomerLogIn.this,ListPlacesActivity.class);
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
