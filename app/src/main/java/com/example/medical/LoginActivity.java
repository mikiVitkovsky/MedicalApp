package com.example.medical;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    ConstraintLayout constraintLayout;
    EditText email, password;
    Button loginB, signUpB;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        constraintLayout = findViewById(R.id.cont);
        email = findViewById(R.id.userName);
        password = findViewById(R.id.loginPass);
        loginB = findViewById(R.id.loginB);
        signUpB = findViewById(R.id.signUpB);

        mAuth = FirebaseAuth.getInstance();

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    loginB.setEnabled(false);
                    email.requestFocus();
                    email.setError("Email is required");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(s.toString().trim()).matches()) {
                    loginB.setEnabled(false);
                    loginB.requestFocus();
                    email.setError("Invalid Email address");
                } else {
                    loginB.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().trim().length() < 8 || s.toString().trim().length() > 10 || s.toString().trim().isEmpty()) {
                    loginB.setEnabled(false);
                    password.requestFocus();
                    password.setError("Password must have 8-10 characters ");
                } else
                    loginB.setEnabled(true);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        signUpB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login();
                hideKeyboard(LoginActivity.this);
            }
        });
    }

    private void login() {

        mAuth.signInWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Login success
                            Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, IndicatorsActivity.class));
                            finish();
                        } else {
                            // If login fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Details do not exist in the system. Please Sign-Up", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public EditText getEmail() {
        return email;
    }

    public EditText getPassword() {
        return password;
    }
}