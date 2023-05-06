package com.example.medical;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

import java.util.Arrays;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    private String userName, email, password;
    private int Id;
    EditText user_name, emailF, ID, user_password;
    Button sign_upB;
    FirebaseAuth fAuth;

    public SignUpActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        user_name = findViewById(R.id.user_name);
        emailF = findViewById(R.id.email);
        ID = findViewById(R.id.idNum);
        user_password = findViewById(R.id.pass_signUp);
        sign_upB = findViewById(R.id.signB);

        fAuth = FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), IndicatorsActivity.class));
        }
        signNewUser();

    }

    public void signNewUser() {
        //Signing up new user after validating all the inputted data

        emailF.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    sign_upB.setEnabled(false);
                    emailF.requestFocus();
                    emailF.setError("Email is required");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(s.toString().trim()).matches()) {
                    sign_upB.setEnabled(false);
                    emailF.requestFocus();
                    emailF.setError("Invalid Email address");
                } else {
                    sign_upB.setEnabled(true);
                    setEmail(s.toString().trim());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().trim().isEmpty()) {
                    sign_upB.setEnabled(false);
                    ID.requestFocus();
                    ID.setError("ID is required");
                }
                if (s.toString().trim().length() != 9) {
                    sign_upB.setEnabled(false);
                    ID.requestFocus();
                    ID.setError("Id number must have 9 digits");
                } else {
                    sign_upB.setEnabled(true);
                    setId(Integer.parseInt(s.toString().trim()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        user_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int counter = 0;

                for (char currentChar : s.toString().trim().toCharArray())
                    if (Character.isDigit(currentChar))
                        counter++;

                if (s.toString().trim().length() < 6 || s.toString().trim().length() > 8) {
                    sign_upB.setEnabled(false);
                    user_name.requestFocus();
                    user_name.setError("User name must have 6-8 characters ");
                } else if (counter > 2) {
                    sign_upB.setEnabled(false);
                    user_name.requestFocus();
                    user_name.setError("User name can't have more than 2 numbers");
                } else {
                    sign_upB.setEnabled(true);
                    setUserName(s.toString().trim());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        user_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int counter1 = 0, counter2 = 0, counter3 = 0;

                for (char currentChar : s.toString().trim().toCharArray()) {

                    if (Character.isAlphabetic(currentChar))
                        counter1++;
                    if (Character.isDigit(currentChar))
                        counter2++;
                    if ((currentChar < 'A' && currentChar > '9') || (currentChar < '0' && currentChar > ' ') || (currentChar > 'Z' && currentChar < 'a'))
                        counter3++;
                }

                if (s.toString().trim().length() < 8 || s.toString().trim().length() > 10 || s.toString().trim().isEmpty()) {
                    sign_upB.setEnabled(false);
                    user_password.requestFocus();
                    user_password.setError("Password must have 8-10 characters ");
                } else if (counter1 == 0) {
                    sign_upB.setEnabled(false);
                    if (counter3 == 0) {
                        user_password.requestFocus();
                        user_password.setError("Password must have at least 1 letter and 1 special character");
                    } else if (counter2 == 0) {
                        user_password.requestFocus();
                        user_password.setError("Password must have at least 1 letter and 1 digit");

                    } else {
                        user_password.requestFocus();
                        user_password.setError("Password must have at least 1 letter");
                    }
                } else if (counter2 == 0) {
                    sign_upB.setEnabled(false);
                    if (counter3 == 0) {
                        user_password.requestFocus();
                        user_password.setError("Password must have at least 1 digit and 1 special character");
                    } else if (counter1 == 0) {
                        user_password.requestFocus();
                        user_password.setError("Password must have at least 1 letter and 1 digit");
                    } else {
                        user_password.requestFocus();
                        user_password.setError("Password must have at least 1 digit");

                    }
                } else if (counter3 == 0) {
                    sign_upB.setEnabled(false);

                    if (counter1 == 0) {
                        user_password.requestFocus();
                        user_password.setError("Password must have at least 1 letter and 1 special character");
                    } else if (counter2 == 0) {
                        user_password.requestFocus();
                        user_password.setError("Password must have at least 1 special character and 1 digit");

                    }
                    user_password.setError("Password must have at least 1 special character");
                    user_password.requestFocus();
                } else {

                    sign_upB.setEnabled(true);
                    setPassword(s.toString().trim());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        sign_upB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideKeyboard(SignUpActivity.this);
                fAuth.createUserWithEmailAndPassword(getEmail(), getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "User created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), IndicatorsActivity.class));
                        } else
                            Toast.makeText(SignUpActivity.this, "Error!" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    public static void hideKeyboard(Activity activity) {
        //hiding the keyboard
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}