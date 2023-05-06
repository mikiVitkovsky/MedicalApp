package com.example.medical;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class IndicatorsActivity extends AppCompatActivity {

    private double corpuscle, neut, lymph, urea, hb, creatinine, hdl, hct;
    private ArrayList<String> problem = new ArrayList<>();
    private ArrayList<String> treatment = new ArrayList<>();
    private List<Diagnosis> diagnoses_list = new ArrayList<>();
    private String gender = "Male";
    private int ID, age, leukocytes, iron, alklinePos;
    EditText IdE, ageE, whiteCellsE, redCellsE, ironE, hbE, hctE, ureaE, hdlE, alk_posE, neutE, lymphE, creatinineE;
    Button submitB;
    RadioGroup qustion_15, question_16, question_17, question_18, question_19, question_20;
    private boolean isSmoking = false;
    private boolean hasFever = false;
    private boolean isEastern = false;
    private boolean isEthiopian = false;
    private boolean isCathartic = false;
    String file_path = "";
    String pattern = "-?\\d+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicators);
        init();
        file_path = "Medical Diagnoses";

        if (!isExternalStorageAvailable())
            Toast.makeText(IndicatorsActivity.this, "can not write file to sd", Toast.LENGTH_SHORT).show();

        validateData();

        submitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setIndicators();
                createAndSaveFile();
                Intent intent = new Intent(getApplicationContext(), DiagnosisActivity.class);
                intent.putStringArrayListExtra("Diagnosis", problem);
                intent.putStringArrayListExtra("Treatment", treatment);
                startActivity(intent);
            }
        });
    }

    private void validateData() {

        // validating the input data from the user

        IdE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    submitB.setEnabled(false);
                    IdE.requestFocus();
                    IdE.setError("Patient's Id is required");
                }
                if (s.toString().trim().length() != 9) {
                    submitB.setEnabled(false);
                    IdE.requestFocus();
                    IdE.setError("Id number must have 9 digits");
                } else {
                    submitB.setEnabled(true);
                    setID(Integer.parseInt(IdE.getText().toString().trim()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        ageE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    submitB.setEnabled(false);
                    ageE.requestFocus();
                    ageE.setError("Patient's age is required");
                }
                if (!s.toString().trim().isEmpty() && s.toString().trim().matches(pattern) && s.toString().trim().length() <= 3) {
                    int age = Integer.parseInt(s.toString().trim());
                    if (age <= 0 || age > 120) {

                        submitB.setEnabled(false);
                        ageE.requestFocus();
                        ageE.setError("wrong value");
                    } else {
                        submitB.setEnabled(true);
                        setAge(age);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        whiteCellsE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty() && s.toString().trim().matches(pattern) && s.toString().trim().length() <= 5) {

                    int whiteCells = Integer.parseInt(s.toString().trim());

                    if (whiteCells <= 1000 || whiteCells >= 60000) {
                        submitB.setEnabled(false);
                        whiteCellsE.requestFocus();
                        whiteCellsE.setError("Wrong value");

                    } else
                        submitB.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        neutE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty() && s.toString().trim().matches(pattern) && s.toString().trim().length() <= 5) {

                    double neut = Double.parseDouble(s.toString().trim());

                    if (neut < 0.0 || neut >= 1.0) {
                        submitB.setEnabled(false);
                        neutE.requestFocus();
                        neutE.setError("wrong value-should be: 0.0-0.99");
                    } else
                        submitB.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lymphE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty() && s.toString().trim().matches(pattern) && s.toString().trim().length() <= 5) {

                    double lymph = Double.parseDouble(s.toString().trim());

                    if (lymph < 0.0 || lymph >= 1.0) {
                        submitB.setEnabled(false);
                        lymphE.requestFocus();
                        lymphE.setError("Wrong value");
                    } else
                        submitB.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        redCellsE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty() && s.toString().trim().matches(pattern) && s.toString().trim().length() <= 5) {

                    double redCells = Double.parseDouble(s.toString().trim());

                    if (redCells <= 0.0 || redCells > 20.0) {
                        submitB.setEnabled(false);
                        redCellsE.requestFocus();
                        redCellsE.setError("Wrong value");
                    } else
                        submitB.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        hbE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty() && s.toString().trim().matches(pattern) && s.toString().trim().length() <= 5) {

                    double hb = Double.parseDouble(s.toString().trim());

                    if (hb <= 0 || hb > 50.5) {
                        submitB.setEnabled(false);
                        hbE.requestFocus();
                        hbE.setError("Wrong value");
                    } else
                        submitB.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ironE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty() && s.toString().trim().matches(pattern) && s.toString().trim().length() <= 5) {

                    int iron = Integer.parseInt(s.toString().trim());

                    if (iron <= 0 || iron >= 500) {
                        submitB.setEnabled(false);
                        ironE.requestFocus();
                        ironE.setError("Wrong value");
                    } else
                        submitB.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        hctE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty() && s.toString().trim().matches(pattern) && s.toString().trim().length() <= 5) {

                    double hct = Double.parseDouble(s.toString().trim());

                    if (hct <= 0 || hct >= 1.0) {
                        submitB.setEnabled(false);
                        hctE.requestFocus();
                        hctE.setError("Wrong value");
                    } else
                        submitB.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        hdlE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty() && s.toString().trim().matches(pattern) && s.toString().trim().length() <= 5) {

                    int hdl = Integer.parseInt(s.toString().trim());

                    if (hdl <= 0 || hdl >= 200) {
                        submitB.setEnabled(false);
                        hdlE.requestFocus();
                        hdlE.setError("Wrong value");
                    } else
                        submitB.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        ureaE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty() && s.toString().trim().matches(pattern) && s.toString().trim().length() <= 5) {

                    int urea = Integer.parseInt(s.toString().trim());

                    if (urea < 1 || urea > 100) {
                        submitB.setEnabled(false);
                        ureaE.requestFocus();
                        ureaE.setError("Wrong value");
                    } else
                        submitB.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        creatinineE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty() && s.toString().trim().matches(pattern) && s.toString().trim().length() <= 5) {

                    double creatinine = Double.parseDouble(s.toString().trim());
                    if (creatinine <= 0 || creatinine >= 10.0) {
                        submitB.setEnabled(false);
                        creatinineE.requestFocus();
                        creatinineE.setError("Wrong value");
                    } else
                        submitB.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        alk_posE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty() && s.toString().trim().matches(pattern) && s.toString().trim().length() <= 5) {

                    int alk = Integer.parseInt(s.toString().trim());

                    if (alk <= 0 || alk > 500) {
                        submitB.setEnabled(false);
                        alk_posE.requestFocus();
                        alk_posE.setError("Wrong value");
                    } else
                        submitB.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    public void createAndSaveFile() {

        File myExternalLFile = new File(getExternalFilesDir(file_path), getID() + " diagnosis.txt");
        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(myExternalLFile);
            outputStream.write(("1.Patient's Id: " + getID() + "\n").getBytes());
            outputStream.write(("2.Patient's Age: " + getAge() + "\n").getBytes());
            outputStream.write(("3.Gender: " + getGender() + "\n").getBytes());
            outputStream.write(("4.Quantity of white blood cells: " + getLeukocytes() + "\n").getBytes());
            outputStream.write(("5.Quantity of red blood cells:" + getCorpuscle() + "\n").getBytes());
            outputStream.write(("6.Hemoglobin:" + getHb() + "\n").getBytes());
            outputStream.write(("7.Iron:" + getIron() + "\n").getBytes());
            outputStream.write(("8.Phosphatase:" + getAlklinePos() + "\n").getBytes());
            outputStream.write(("9.HCT:" + getHct() * 100 + "%\n").getBytes());
            outputStream.write(("10.Urea: " + getUrea() + "\n").getBytes());
            outputStream.write(("11.Creatinine: " + getCreatinine() + "\n").getBytes());
            outputStream.write(("12.HDL: " + getHdl() + "\n").getBytes());
            outputStream.write(("13.Neutrophil: " + getNeut() * 100 + "%\n").getBytes());
            outputStream.write(("14.Lymphocytes: " + getLymph() * 100 + "%\n").getBytes());
            if (isSmoking())
                outputStream.write(("15.The patient is smoking cigarettes\n").getBytes());
            else outputStream.write(("15.The patient is not smoking cigarettes\n").getBytes());
            if (isHasFever()) outputStream.write(("16.The patient has fever\n").getBytes());
            else outputStream.write(("16.The patient doesn't have fever\n").getBytes());
            if (isEastern())
                outputStream.write(("17.The patient is from eastern communities\n").getBytes());
            else
                outputStream.write(("17.The patient is not from eastern communities\n").getBytes());
            if (isEthiopian())
                outputStream.write(("18.The patient is immigrant from ethiopia\n").getBytes());
            else
                outputStream.write(("18.The patient is not immigrant from ethiopia\n").getBytes());
            if (isCathartic())
                outputStream.write(("19.The patient suffers from Diarrhea/Vomiting\n").getBytes());
            else
                outputStream.write(("19.The patient does not suffer from Diarrhea/Vomiting\n").getBytes());
            for (int i = 0, j = 20; i < treatment.size(); i++, j++)
                outputStream.write((j + ".Possible disease:" + problem.get(i) + "\n" + "Possible treatment:" + treatment.get(i) + "\n").getBytes());
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(IndicatorsActivity.this, "failed to save file", Toast.LENGTH_SHORT).show();

        }
        Toast.makeText(IndicatorsActivity.this, "file saved successfully", Toast.LENGTH_SHORT).show();


    }


    public void setIndicators() {
        RadioButton selectedB1, selectedB2, selectedB3, selectedB4, selectedB5, selectedB6;

        //getting all radio groups selections

        int selectedAns1 = qustion_15.getCheckedRadioButtonId();
        int selectedAns2 = question_16.getCheckedRadioButtonId();
        int selectedAns3 = question_17.getCheckedRadioButtonId();
        int selectedAns4 = question_18.getCheckedRadioButtonId();
        int selectedAns5 = question_19.getCheckedRadioButtonId();
        int selectedAns6 = question_20.getCheckedRadioButtonId();

        //saving all the answers
        if (selectedAns1 != -1) {
            //if the patient is smoking
            selectedB1 = findViewById(selectedAns1);
            String answer = selectedB1.getText().toString();
            if (answer.equals("Yes"))
                setSmoking(true);
        }
        if (selectedAns2 != -1) {
            //patient's gender
            selectedB2 = findViewById(selectedAns2);
            String answer = selectedB2.getText().toString();
            if (answer.equals("Female"))
                setGender("Female");

        }
        if (selectedAns3 != -1) {
            selectedB3 = findViewById(selectedAns3);
            String answer = selectedB3.getText().toString();
            if (answer.equals("Yes"))
                setHasFever(true);
        }

        if (selectedAns4 != -1) {
            //if the patient is from eastern communities

            selectedB4 = findViewById(selectedAns4);
            String answer = selectedB4.getText().toString();
            if (answer.equals("Yes"))
                setEastern(true);
        }

        if (selectedAns5 != -1) {
            //if the patient is ethiopian
            selectedB5 = findViewById(selectedAns5);
            String answer = selectedB5.getText().toString();
            if (answer.equals("Yes"))
                setEthiopian(true);
        }

        if (selectedAns6 != -1) {
            //if the patient has Diarrhea/Vomiting
            selectedB6 = findViewById(selectedAns6);
            String answer = selectedB6.getText().toString();
            if (answer.equals("Yes"))
                setCathartic(true);
        }

        // getting all edit texts's values

        //WBC
        if (!whiteCellsE.getText().toString().isEmpty()) {
            setLeukocytes(Integer.parseInt(whiteCellsE.getText().toString().trim()));
            checkWbc();
        }

        //Neutrophil
        if (!neutE.getText().toString().isEmpty()) {
            setNeut(Double.parseDouble(neutE.getText().toString().trim()));
            checkNeut();
        }
        //Lymphocytes
        if (!lymphE.getText().toString().isEmpty()) {
            setLymph(Double.parseDouble(lymphE.getText().toString().trim()));
            checkLymph();
        }
        //RBC
        if (!redCellsE.getText().toString().isEmpty()) {
            setCorpuscle(Double.parseDouble(redCellsE.getText().toString().trim()));
            checkRbc();
        }
        //Hemoglobin
        if (!hbE.getText().toString().isEmpty()) {
            setHb(Double.parseDouble(hbE.getText().toString().trim()));
            checkHb();
        }
        //Iron
        if (!ironE.getText().toString().isEmpty()) {
            setIron(Integer.parseInt(ironE.getText().toString().trim()));
            checkIron();
        }
        //HCT
        if (!hctE.getText().toString().isEmpty()) {
            setHct(Double.parseDouble(hctE.getText().toString().trim()));
            checkHct();
        }
        //High Density Lipoprotein
        if (!hdlE.getText().toString().isEmpty()) {
            setHdl(Integer.parseInt(hdlE.getText().toString().trim()));
            checkHdl();
        }

        //Urea
        if (!ureaE.getText().toString().isEmpty()) {
            setUrea(Integer.parseInt(ureaE.getText().toString().trim()));
            checkUrea();
        }
        //Creatinine
        if (!creatinineE.getText().toString().isEmpty()) {
            setCreatinine(Double.parseDouble(creatinineE.getText().toString().trim()));
            checkCreatinine();
        }
        //Alkaline Phosphatase
        if (!alk_posE.getText().toString().isEmpty()) {
            setAlklinePos(Integer.parseInt(alk_posE.getText().toString().trim()));
            checkAlk();
        }
    }

    public void checkAlk() {
        /*
        normal ranges->(unites)
        1)eastern communities->60-120
        2)for all->30-90
         */
        if (isEastern) {
            if (getAlklinePos() < 60) {
                problem.add("Poor nutrition that lacks protein");
                treatment.add("Schedule an appointment with a nutritionist");
                problem.add("Vitamin deficiency");
                treatment.add("Refer for a blood test to identify the missing vitamins");
            } else if (getAlklinePos() > 120) {
                if (getGender().equals("Female") && (getAge() >= 18)) {
                    problem.add("Pregnancy");
                    treatment.add("Preform pregnancy test");
                }
                problem.add("Liver disease");
                treatment.add("Refer to a specific diagnosis for treatment");
                problem.add("Diseases of the biliary tract");
                treatment.add("Refer for surgical treatment");
                problem.add("Overactive thyroid gland");
                treatment.add("Ropylthiouracil to reduce thyroid activity");
                problem.add("Use of various medications");
                treatment.add("Refer to the family doctor for a match between the medications");
            }
        } else {
            if (getAlklinePos() < 30) {
                problem.add("Poor nutrition that lacks protein");
                treatment.add("Schedule an appointment with a nutritionist");
                problem.add("Vitamin deficiency");
                treatment.add("Refer for a blood test to identify the missing vitamins");
            } else if (getAlklinePos() > 90) {
                if (getGender().equals("Female") && (getAge() >= 18)) {
                    problem.add("Pregnancy");
                    treatment.add("Preform pregnancy test");
                }
                problem.add("Liver disease");
                treatment.add("Refer to a specific diagnosis for treatment");
                problem.add("Diseases of the biliary tract");
                treatment.add("Refer for surgical treatment");
                problem.add("Overactive thyroid gland");
                treatment.add("Ropylthiouracil to reduce thyroid activity");
                problem.add("Use of various medications");
                treatment.add("Refer to the family doctor for a match between the medications");
            }
        }
    }

    public void checkHdl() {
        /*
        normal ranges->milligrams
        1)for ethiopian->men->34.8-74.4,women->40.8-98.4
        2)for all->men->29-62,women->34-82
         */
        if (getGender().equals("Female") && (getAge() >= 18)) {
            if (isEthiopian) {
                if (getHdl() < 40.8) {
                    problem.add("Adult diabetes ");
                    treatment.add("Insulin adjustment for the patient");
                    problem.add("Hyperlipidemia");
                    treatment.add("Simobil 5 mg pill a day for a week\n" + "Schedule an appointment with a nutritionist");
                    problem.add("Risk of heart disease");
                    treatment.add("Schedule an appointment with a nutritionist");

                } else if (getHdl() > 98.5) {
                    problem.add("Sports activities");
                    treatment.add("Keep training");
                }
            } else {
                if (getHdl() < 34.0) {
                    problem.add("Adult diabetes ");
                    treatment.add("Insulin adjustment for the patient");
                    problem.add("Hyperlipidemia");
                    treatment.add("Simobil 5 mg pill a day for a week\n" + "Schedule an appointment with a nutritionist");
                    problem.add("Risk of heart disease");
                    treatment.add("Schedule an appointment with a nutritionist");

                } else if (getHdl() > 82.0) {
                    problem.add("Sports activities");
                    treatment.add("Keep training");
                }
            }
        } else if (getGender().equals("Male") && (getAge() >= 18)) {
            if (isEthiopian) {
                if (getHdl() < 34.8) {
                    problem.add("Adult diabetes ");
                    treatment.add("Insulin adjustment for the patient");
                    problem.add("Hyperlipidemia");
                    treatment.add("Simobil 5 mg pill a day for a week\n" + "Schedule an appointment with a nutritionist");
                    problem.add("Risk of heart disease");
                    treatment.add("Schedule an appointment with a nutritionist");

                } else if (getHdl() > 74.4) {
                    problem.add("Sports activities");
                    treatment.add("Keep training");
                }
            } else {
                if (getHdl() < 29.0) {
                    problem.add("Adult diabetes ");
                    treatment.add("Insulin adjustment for the patient");
                    problem.add("Hyperlipidemia");
                    treatment.add("Simobil 5 mg pill a day for a week\n" + "Schedule an appointment with a nutritionist");
                    problem.add("Risk of heart disease");
                    treatment.add("Schedule an appointment with a nutritionist");

                } else if (getHdl() > 62.0) {
                    problem.add("Sports activities");
                    treatment.add("Keep training");
                }
            }
        }
    }

    public void checkIron() {
        /*
        normal ranges->
        1)for women->48-128
        2)for all*->60-160
         */
        //*
        if (getAge() >= 18) {
            if (getGender().equals("Female")) {
                if (getIron() < 48) {
                    problem.add("Pregnancy");
                    treatment.add("Preform pregnancy test");
                    problem.add("Bleeding");
                    treatment.add("Evacuate urgently to the hospital!!!!");
                    problem.add("Malnutrition");
                    treatment.add("Schedule an appointment with a nutritionist");


                } else if (getIron() > 128) {
                    problem.add("Iron poisoning");
                    treatment.add("Need to evacuate to hospital!!");
                }
            }
        } else {
            if (getIron() < 60) {
                problem.add("Bleeding");
                treatment.add("Evacuate urgently to the hospital!!!!");
                problem.add("Malnutrition");
                treatment.add("Schedule an appointment with a nutritionist");
            } else if (getIron() > 160) {
                problem.add("Iron poisoning");
                treatment.add("Need to evacuate to hospital!!");
            }
        }
    }

    public void checkCreatinine() {
        /*
        normal ranges for->
        1)Elderly(+60)->0.6-1.2
        2)Adult(18-59)->0.6-1
        3)Children+Teenagers(3-17)->0.5-1
        4)Toddlers(0-2)->0.2-0.5
         */
        if (getAge() >= 60) {
            if (getCreatinine() > 1.2) {
                if (isCathartic) {
                    problem.add("Vomiting/Diarrhea");
                    treatment.add("Anti-vomiting/Diarrhea pill");
                }
                problem.add("Kidney disease");
                treatment.add("Balance blood sugar levels");
                problem.add("Kidney failure(Severe Cases)");
                treatment.add("Refer for a kidney failure test");
                problem.add("Increased consumption of meat");
                treatment.add("Schedule an appointment with a nutritionist");
                problem.add("Muscle diseases");
                treatment.add("Two 5 mg pills of Altman c3 turmeric a day for a month");
            } else if (getCreatinine() < 0.6) {
                problem.add("Malnutrition");
                treatment.add("Schedule an appointment with a nutritionist");
            }
        } else if ((getAge() >= 18) && (getAge() <= 59)) {
            if (getCreatinine() > 1.0) {
                if (isCathartic) {
                    problem.add("Vomiting/Diarrhea");
                    treatment.add("Anti-vomiting/Diarrhea pill");

                }
                problem.add("Kidney disease");
                treatment.add("Balance blood sugar levels");
                problem.add("Kidney failure(Severe Cases)");
                treatment.add("Refer for a kidney failure test");
                problem.add("Increased consumption of meat");
                treatment.add("Schedule an appointment with a nutritionist");
                problem.add("Muscle diseases");
                treatment.add("Two 5 mg pills of Altman c3 turmeric a day for a month");
            } else if (getCreatinine() < 0.6) {
                problem.add("Malnutrition");
                treatment.add("Schedule an appointment with a nutritionist");
            }
        } else if ((getAge() >= 3) && (getAge() <= 17)) {
            if (getCreatinine() > 1.0) {
                if (isCathartic) {
                    problem.add("Vomiting/Diarrhea");
                    treatment.add("Anti-vomiting/Diarrhea pill");

                }
                problem.add("Kidney disease");
                treatment.add("Balance blood sugar levels");
                problem.add("Kidney failure(Severe Cases)");
                treatment.add("Refer for a kidney failure test");
                problem.add("Increased consumption of meat");
                treatment.add("Schedule an appointment with a nutritionist");
                problem.add("Muscle diseases");
                treatment.add("Two 5 mg pills of Altman c3 turmeric a day for a month");

            } else if (getCreatinine() < 0.5) {
                problem.add("Malnutrition");
                treatment.add("Schedule an appointment with a nutritionist");
            }
        } else if ((getAge() == 0) || (getAge() <= 2)) {
            if (getCreatinine() > 0.5) {
                if (isCathartic) {
                    problem.add("Vomiting/Diarrhea");
                    treatment.add("Anti-vomiting/Diarrhea pill");
                }
                problem.add("Kidney disease");
                treatment.add("Balance blood sugar levels");
                problem.add("Kidney failure(Severe Cases)");
                treatment.add("Refer for a kidney failure test");
                problem.add("Increased consumption of meat");
                treatment.add("Schedule an appointment with a nutritionist");
                problem.add("Muscle diseases");
                treatment.add("Two 5 mg pills of Altman c3 turmeric a day for a month");
            } else if (getCreatinine() < 0.2) {
                problem.add("Malnutrition");
                treatment.add("Schedule an appointment with a nutritionist");
            }
        }
    }

    public void checkHb() {
        /*
        normal ranges for->(milligrammes)
        1)men(+18)->12-18
        2)women(+18)->12-16
        3)children(4-17)->11.5-15.5
         */
        if (getAge() >= 18) {
            if (getHb() < 12) {
                problem.add("Anemia");
                treatment.add("Two 10 mg B12 pills a day for a month");
                problem.add("Bleeding");
                treatment.add("Evacuate urgently to the hospital!!!!");
                problem.add("Iron deficiency");
                treatment.add("Two 10 mg B12 pills a day for a month");
                problem.add("Hematological disorder");
                treatment.add("An injection of a hormone to encourage red blood cell production");
            }
        } else if ((getAge() >= 4) && (getAge() <= 17)) {
            if (getHb() < 11.5) {
                problem.add("Anemia");
                treatment.add("Two 10 mg B12 pills a day for a month");
                problem.add("Bleeding");
                treatment.add("Evacuate urgently to the hospital!!!!");
                problem.add("Iron deficiency");
                treatment.add("Two 10 mg B12 pills a day for a month");
                problem.add("Hematological disorder");
                treatment.add("An injection of a hormone to encourage red blood cell production");
            }
        }
    }

    public void checkWbc() {
        /*
        normal ranges for->
        1)adults(+18)->4,500-11,000
        2)children(4-17)->5,500-15,500
        3)toddlers(0-3)->6,000-17,500
         */

        if (getAge() >= 18) {

            if (getLeukocytes() < 4500) {

                problem.add("Virus");
                treatment.add("Stay home for rest");
                problem.add("Cancer(Rare Case)");
                treatment.add("Entrectinib");

            } else if (getLeukocytes() > 11000) {

                if (hasFever) {

                    problem.add("Infection");
                    treatment.add("Dedicated antibiotics");
                } else {
                    problem.add("Cancer(Rare Case)");
                    treatment.add("Entrectinib");
                    problem.add("Blood disease(Rare Case)");
                    treatment.add("A combination of cyclophosphamide and corticosteroids");
                }
            }
        } else if ((getAge() >= 4) && (getAge() <= 17)) {
            if (getLeukocytes() < 5500) {
                problem.add("Virus");
                treatment.add("Stay home for rest");
                problem.add("Cancer(Rare Case)");
                treatment.add("Entrectinib");
            } else if (getLeukocytes() > 15500) {
                if (hasFever) {

                    problem.add("Infection");
                    treatment.add("Dedicated antibiotics");
                } else {
                    problem.add("Cancer(Rare Case)");
                    treatment.add("Entrectinib");
                    problem.add("Blood disease(Rare Case)");
                    treatment.add("A combination of cyclophosphamide and corticosteroids");
                }
            }
        }
        if (getAge() == 0 || getAge() <= 3)
            if (getLeukocytes() < 6500) {
                problem.add("Virus");
                treatment.add("Stay home for rest");
                problem.add("Cancer(Rare Case)");
                treatment.add("Entrectinib");
            } else if (getLeukocytes() > 17500)
                if (hasFever) {
                    problem.add("Infection");
                    treatment.add("Dedicated antibiotics");
                } else {
                    problem.add("Cancer(Rare Case)");
                    treatment.add("Entrectinib");
                    problem.add("Blood disease(Rare Case)");
                    treatment.add("A combination of cyclophosphamide and corticosteroids");
                }
    }

    public void checkUrea() {
        /*
        1)for eastern communities-> 18.1-47.3
        2)normal range->17-43 milligrams
        */
        if (isEastern())
            if (getUrea() < 18.1) {
                if (getGender().equals("Female")) {
                    problem.add("Pregnancy");
                    treatment.add("Preform pregnancy test");
                }
                problem.add("Malnutrition");
                treatment.add("Schedule an appointment with a nutritionist");
                problem.add("Low protein diet");
                treatment.add("Schedule an appointment with a nutritionist");
                problem.add("Liver disease");
                treatment.add("Refer to a specific diagnosis for treatment");
            } else if (getUrea() > 47.3) {
                problem.add("Kidney disease");
                treatment.add("Balance blood sugar levels");
                problem.add("Dehydration");
                treatment.add("Complete rest will lying down\n" + "returning fluids in drinking");
                problem.add("Protein-rich diet");
                treatment.add("Schedule an appointment with a nutritionist");
            } else if (getUrea() < 17.0) {
                if (getGender().equals("Female")) {
                    problem.add("Pregnancy");
                    treatment.add("Preform pregnancy test");
                }
                problem.add("Malnutrition");
                treatment.add("Schedule an appointment with a nutritionist");
                problem.add("Low protein diet");
                treatment.add("Schedule an appointment with a nutritionist");
                problem.add("Liver disease");
                treatment.add("Refer to a specific diagnosis for treatment");
            } else if (getUrea() > 43.0) {
                problem.add("Kidney disease");
                treatment.add("Balance blood sugar levels");
                problem.add("Dehydration");
                treatment.add("Complete rest will lying down\n" + "returning fluids in drinking");
                problem.add("Protein-rich diet");
                treatment.add("Schedule an appointment with a nutritionist");
            }

    }


    public void checkHct() {

        /*
        normal range of-> the volume of red blood cells within the whole blood fluid
         1)men->37%-54%
         2)women->33%-47%
         */
        if (getGender().equals("Male") && (getAge() >= 18)) {

            if (getHct() < 0.37) {
                problem.add("Anemia");
                treatment.add("Two 10 mg B12 pills a day for a month");
                problem.add("Bleeding");
                treatment.add("Evacuate urgently to the hospital!!!!");
            } else if (getHct() > 0.54 && isSmoking()) {
                problem.add("Smoking Cigarettes");
                treatment.add("Stop smoking");
            }
        } else if (getGender().equals("Female") && getAge() >= 18) {

            if (getHct() > 0 && getHct() < 0.33) {
                problem.add("Anemia");
                problem.add("Bleeding");
                treatment.add("Two 10 mg B12 pills a day for a month");
                treatment.add("Evacuate urgently to the hospital!!!!");
            } else if (getHct() > 0.47 && isSmoking()) {
                problem.add("Smoking Cigarettes");
                treatment.add("Stop smoking");

            }
        }
    }

    public void checkRbc() {

        //normal range->4.5-6.0

        if (getCorpuscle() < 4.5) {
            problem.add("Anemia");
            problem.add("Severe bleeding");
            treatment.add("Two 10 mg B12 pills a day for a month");
            treatment.add("Evacuate urgently to the hospital!!!!");
        } else if (getCorpuscle() > 6.0) {
            if (isSmoking) {
                problem.add("Smoking Cigarettes");
                treatment.add("Stop smoking");
            } else {
                problem.add("Disorder of blood / blood cell formation");
                treatment.add("10 mg pill of B12 a day for a month\n" + "5 mg pill of folic acid a day for a month");
            }
            problem.add("Lung disease");
            treatment.add("Refer for an X-ray of the lungs");
        }

    }

    public void checkLymph() {

        //normal range->36%-52% from white blood cells

        if (getLymph() < 0.36) {
            problem.add("Disorder of blood / blood cell formation");
            treatment.add("10 mg pill of B12 a day for a month\n" + "5 mg pill of folic acid a day for a month");
        } else if (getLymph() > 0.52) {
            problem.add("Prolonged bacterial infection");
            treatment.add("Dedicated antibiotics");
            problem.add("Lymphoma cancer");
            treatment.add("Entrectinib");
        }
    }

    public void checkNeut() {

        //normal range->28%-54% from white blood cells

        if (getNeut() < 0.28) {
            problem.add("Disorder of blood / blood cell formation");
            treatment.add("10 mg pill of B12 a day for a month\n" + "5 mg pill of folic acid a day for a month");
            problem.add("Infection");
            treatment.add("Dedicated antibiotics");
            problem.add("Cancer(Rare Case)");
            treatment.add("Entrectinib");
        } else if (getNeut() > 0.54) {
            problem.add("Infection");
            treatment.add("Dedicated antibiotics");
        }
    }

    public void init() {
        IdE = findViewById(R.id.patientId);
        ageE = findViewById(R.id.age);
        whiteCellsE = findViewById(R.id.whiteCells);
        neutE = findViewById(R.id.neutL);
        redCellsE = findViewById(R.id.redCells);
        ironE = findViewById(R.id.ironL);
        hbE = findViewById(R.id.hemoglobinL);
        hctE = findViewById(R.id.hctL);
        ureaE = findViewById(R.id.ureaL);
        hdlE = findViewById(R.id.hdlL);
        creatinineE = findViewById(R.id.creatinineL);
        alk_posE = findViewById(R.id.alkPos);
        qustion_15 = findViewById(R.id.isSmokes);
        question_16 = findViewById(R.id.gender);
        question_17 = findViewById(R.id.fever);
        question_18 = findViewById(R.id.eastern);
        question_19 = findViewById(R.id.ethiopian);
        question_20 = findViewById(R.id.question20);
        submitB = findViewById(R.id.diagnoseB);
        lymphE = findViewById(R.id.lymphL);


    }


    public int getLeukocytes() {
        return leukocytes;
    }

    public void setLeukocytes(int leukocytes) {
        this.leukocytes = leukocytes;
    }


    public int getIron() {
        return iron;
    }

    public void setIron(int iron) {
        this.iron = iron;
    }


    public double getHct() {
        return hct;
    }

    public void setHct(double hct) {
        this.hct = hct;
    }


    public double getCorpuscle() {
        return corpuscle;
    }

    public void setCorpuscle(double corpuscle) {
        this.corpuscle = corpuscle;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public void setProblem(String problem) {
        this.problem.add(problem);
    }

    public void setTreatment(String treatment) {
        this.treatment.add(treatment);
    }


    public boolean isSmoking() {
        return isSmoking;
    }

    public void setSmoking(boolean smoking) {
        isSmoking = smoking;
    }

    public boolean isHasFever() {
        return hasFever;
    }

    public void setHasFever(boolean hasFever) {
        this.hasFever = hasFever;
    }

    public boolean isEastern() {
        return isEastern;
    }

    public void setEastern(boolean eastern) {
        isEastern = eastern;
    }

    public boolean isEthiopian() {
        return isEthiopian;
    }

    public void setEthiopian(boolean ethiopian) {
        isEthiopian = ethiopian;
    }

    public boolean isCathartic() {
        return isCathartic;
    }

    public void setCathartic(boolean cathartic) {
        isCathartic = cathartic;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getNeut() {
        return neut;
    }

    public void setNeut(double neut) {
        this.neut = neut;
    }

    public double getLymph() {
        return lymph;
    }

    public void setLymph(double lymph) {
        this.lymph = lymph;
    }

    public double getUrea() {
        return urea;
    }

    public void setUrea(double urea) {
        this.urea = urea;
    }

    public double getHb() {
        return hb;
    }

    public void setHb(double hb) {
        this.hb = hb;
    }


    public double getCreatinine() {
        return creatinine;
    }

    public void setCreatinine(double creatinine) {
        this.creatinine = creatinine;
    }

    public double getHdl() {
        return hdl;
    }

    public void setHdl(double hdl) {
        this.hdl = hdl;
    }

    public int getAlklinePos() {
        return alklinePos;
    }

    public void setAlklinePos(int alklinePos) {
        this.alklinePos = alklinePos;
    }

    public List<Diagnosis> getDiagnoses_list() {
        return diagnoses_list;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}