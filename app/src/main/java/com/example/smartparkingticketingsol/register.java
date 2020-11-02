package com.example.smartparkingticketingsol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class register extends AppCompatActivity {
     public EditText emailId,password,confpass,name;
     Button btnsignUp;
     FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mFirebaseAuth=FirebaseAuth.getInstance();
        emailId=findViewById(R.id.EmailId);
        password=findViewById(R.id.editTextTextPassword2);
        confpass=findViewById(R.id.editTextTextPassword3);
        btnsignUp=findViewById(R.id.Register);
        name=findViewById(R.id.editTextTextPersonName);
        btnsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=emailId.getText().toString();
                String pass=password.getText().toString();
                String conf=confpass.getText().toString();
                String Name=name.getText().toString();
                if(Name.isEmpty())
                {
                    name.setError("Please Enter Your Name");
                    name.requestFocus();
                }
                else if(email.isEmpty())
                {
                    emailId.setError("Please Enter Email");
                    emailId.requestFocus();
                }
                else if(pass.isEmpty())
                {
                    password.setError("Please Enter Password");
                    password.requestFocus();
                }
                else if(email.isEmpty() && pass.isEmpty())
                {
                    Toast.makeText(register.this,"Fields Are Empty!",Toast.LENGTH_SHORT).show();
                }
               else if(conf.isEmpty())
                {
                    confpass.setError("Please Retype Your Password");
                    confpass.requestFocus();
                }
                else if(!(Name.isEmpty() && email.isEmpty() && pass.isEmpty() && conf.isEmpty()))
                {
                    mFirebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful())
                            {
                                Toast.makeText(register.this,"You have already Registered!",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(register.this,"You have Registered successfully",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(register.this,Home.class));
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(register.this,"Error Occurred",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}