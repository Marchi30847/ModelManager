package data.models;

import data.abstraction.Model;
import data.annotations.Bind;

public class HealthcareModel implements Model {
    @Bind private int LL;                   // Number of years
    @Bind private int[] LATA;              // Years [from, to]

    @Bind private double[] twPatientGrowth; // Growth rate of patient count
    @Bind private double[] twCostPerPatientGrowth; // Growth rate of cost per patient
    @Bind private double[] patientCount;    // Number of patients
    @Bind private double[] costPerPatient;  // Cost per patient

    @Bind private double[] totalHealthcareCost; // Total healthcare cost
    @Bind private double[] governmentSpending;  // Government spending
    @Bind private double[] outOfPocketSpending; // Out-of-pocket spending

    public HealthcareModel() { }

    @Override
    public void run() {
        totalHealthcareCost[0] = patientCount[0] * costPerPatient[0];
        outOfPocketSpending[0] = totalHealthcareCost[0] - governmentSpending[0];
        for (int t = 1; t < LL; t++) {
            patientCount[t] = patientCount[t - 1] * twPatientGrowth[t];
            costPerPatient[t] = costPerPatient[t - 1] * twCostPerPatientGrowth[t];
            totalHealthcareCost[t] = patientCount[t] * costPerPatient[t];
            outOfPocketSpending[t] = totalHealthcareCost[t] - governmentSpending[t];
        }
    }
}