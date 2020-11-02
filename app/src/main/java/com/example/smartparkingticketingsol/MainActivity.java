package com.example.smartparkingticketingsol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    public EditText emailId,password;
    Button btnsignIn,btn;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth=FirebaseAuth.getInstance();
        emailId=findViewById(R.id.editTextTextEmailAddress);
        password=findViewById(R.id.editTextTextPassword);
        btnsignIn=findViewById(R.id.signin);
        mAuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser= mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser!=null)
                {
//                    Toast.makeText(MainActivity.this,"You are logged successfully!",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(MainActivity.this,Home.class);
                    startActivity(i);
                }
//                else
//                {
//                    Toast.makeText(MainActivity.this,"Please Try Again or yor are not registered!",Toast.LENGTH_SHORT).show();
//                }
            }
        };
        btnsignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=emailId.getText().toString();
                String pass=password.getText().toString();
                   if(email.isEmpty())
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
                    Toast.makeText(MainActivity.this,"Fields Are Empty!",Toast.LENGTH_SHORT).show();
                }
                else if(!(email.isEmpty() && pass.isEmpty()))
                   {
                       mFirebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               if(!task.isSuccessful())
                               {
                                   Toast.makeText(MainActivity.this,"You are not registered! Please Try Again",Toast.LENGTH_SHORT).show();
                               }
                               else
                               {
                                   Toast.makeText(MainActivity.this,"Login Successfully!",Toast.LENGTH_SHORT).show();
                                   Intent intoHome=new Intent(MainActivity.this,Home.class);
                                   startActivity(intoHome);
                               }
                           }
                       });
                   }
                else
                   {
                       Toast.makeText(MainActivity.this,"Error Occurred! Please Try Again",Toast.LENGTH_SHORT).show();
                   }
            }
        });
         btn=findViewById(R.id.signup);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,register.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}