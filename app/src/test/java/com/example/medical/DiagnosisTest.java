package com.example.medical;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DiagnosisTest {

    @Test
    public void test_diagnosis_get_set() {
        String input_problem = "Anemia";
        String input_solution = "2 pills of B12";
        Diagnosis diagnosis = new Diagnosis("", "");
        diagnosis.setProblem(input_problem);
        diagnosis.setSolution(input_solution);
        String output_problem = diagnosis.getProblem();
        String output_solution = diagnosis.getSolution();

        assertEquals(input_problem, output_problem);
        assertEquals(input_solution, output_solution);
    }

    @Test
    public void test_expendable() {
        Diagnosis diagnosis = new Diagnosis("", "");
        assertFalse(diagnosis.isExpendable());
        diagnosis.setExpendable(true);
        assertTrue(diagnosis.isExpendable());
    }

    @Test
    public void test_init_diagnosis() {
        ArrayList<String> problems = new ArrayList<>();
        ArrayList<String> treatments = new ArrayList<>();
        problems.add("Anemia");
        treatments.add("2 pills");
        problems.add("infection");
        treatments.add("antibiotic");
        List<Diagnosis> diagnosis = new ArrayList<>();
        diagnosis.add(new Diagnosis(problems.get(0), treatments.get(0)));
        diagnosis.add(new Diagnosis(problems.get(1), treatments.get(1)));
        List<Diagnosis> output_diagnosis;
        output_diagnosis = Diagnosis.initDiagnosis(problems, treatments);

        assertEquals(diagnosis.get(0).getProblem(), output_diagnosis.get(0).getProblem());
        assertEquals(diagnosis.get(0).getSolution(), output_diagnosis.get(0).getSolution());

        assertEquals(diagnosis.get(1).getProblem(), output_diagnosis.get(1).getProblem());
        assertEquals(diagnosis.get(1).getSolution(), output_diagnosis.get(1).getSolution());
    }


}