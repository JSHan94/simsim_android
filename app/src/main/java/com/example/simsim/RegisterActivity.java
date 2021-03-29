package com.example.simsim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;

    private EditText username, password;
    private TextView signup,banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        username = (EditText) findViewById(R.id.register_username);
        password = (EditText) findViewById(R.id.register_password);

        signup = (TextView) findViewById(R.id.signup);
        signup.setOnClickListener(this);

        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signup :
                registerUser();
                break;
            case R.id.banner :
                startActivity(new Intent(this,LoginActivity.class));
                break;
        }
    }

    private void registerUser() {
        String email = username.getText().toString().trim();
        String pwd = password.getText().toString().trim();

        if(email.isEmpty()){
            username.setError("Email is required!");
            username.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            username.setError("Email format is wrong!");
            username.requestFocus();
            return;
        }
        if(pwd.isEmpty() || pwd.length()<6){
            password.setError("Password is required!");
            password.requestFocus();
        }
        mAuth.createUserWithEmailAndPassword(email,pwd)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        if(task.isSuccessful()){
                            User user = new User(email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this,"User has been registered successfully!",Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(RegisterActivity.this,"Failed to register! Try again",Toast.LENGTH_LONG).show();
                                    }

                                }
                            });
                        }else{
                            Toast.makeText(RegisterActivity.this,"Failed to register!",Toast.LENGTH_LONG).show();
                        }
                    }
        });
    }
}