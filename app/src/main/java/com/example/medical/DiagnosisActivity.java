package com.example.medical;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.example.medical.Diagnosis.initDiagnosis;

public class DiagnosisActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Diagnosis> diagnosis;
    Button newDiagnoseB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);

        recyclerView = findViewById(R.id.problems_recycler);
        newDiagnoseB = findViewById(R.id.newDiagnose);

        newDiagnoseB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), IndicatorsActivity.class));
            }
        });

        ArrayList<String> problems = getIntent().getStringArrayListExtra("Diagnosis");
        ArrayList<String> treatments = getIntent().getStringArrayListExtra("Treatment");
        diagnosis = initDiagnosis(problems, treatments);

        setRecyclerView();


    }


    private void setRecyclerView() {

        DiagnosisAdapter diagnosisAdapter = new DiagnosisAdapter(diagnosis);
        recyclerView.setAdapter(diagnosisAdapter);
        recyclerView.setHasFixedSize(true);

    }


}