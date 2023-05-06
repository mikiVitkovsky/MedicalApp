package com.example.medical;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Diagnosis {

    private String problem, solution;
    private boolean expendable;
    static List<Diagnosis> diagnosis;

    public Diagnosis(String problem, String solution) {
        this.problem = problem;
        this.solution = solution;
        this.expendable = false;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public boolean isExpendable() {
        return expendable;
    }

    public void setExpendable(boolean expendable) {
        this.expendable = expendable;
    }

    public static List<Diagnosis> initDiagnosis(ArrayList<String> problems, ArrayList<String> treatments) {

        /*
        Getting the Medical diagnosis results and making sure that there are no duplicates
         */

        diagnosis = new ArrayList<>();

        Set<String> set1 = new LinkedHashSet<>();
        set1.addAll(problems);
        problems.clear();
        problems.addAll(set1);
        Set<String> set2 = new LinkedHashSet<>();
        set2.addAll(treatments);
        treatments.clear();
        treatments.addAll(set2);
        for (int i = 0; i < treatments.size(); i++)
            diagnosis.add(new Diagnosis(problems.get(i), treatments.get(i)));
        return diagnosis;
    }


}