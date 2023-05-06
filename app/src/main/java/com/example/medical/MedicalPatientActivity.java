package com.example.medical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class MedicalPatientActivity extends AppCompatActivity {

    Button nextB;
    EditText idField, ageField;
    RadioGroup qustion_4, question_5, question_6, question_7, question_8;
    private boolean isSmoking = false;
    private static boolean hasFever = false;
    private boolean isEastern = false;
    private boolean isEthiopian = false;
    private boolean isCathartic = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical);

        idField = findViewById(R.id.patientId);
        ageField = findViewById(R.id.age);
        qustion_4 = findViewById(R.id.question4);
        question_5 = findViewById(R.id.question5);
        question_6 = findViewById(R.id.question6);
        question_7 = findViewById(R.id.question7);
        question_8 = findViewById(R.id.question8);
        nextB = findViewById(R.id.nextB);
    }
}