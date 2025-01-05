package data.models;

import data.abstraction.Model;
import data.annotations.Bind;

public class ConsumptionModel implements Model {
    @Bind private int LL;                       // Number of years
    @Bind private int[] LATA;                   // Years [from, to]

    @Bind private double[] twElectricityGrowth; // Growth rate of electricity consumption
    @Bind private double[] twGasGrowth;         // Growth rate of gas consumption
    @Bind private double[] electricityUsage;    // Electricity usage
    @Bind private double[] gasUsage;            // Gas usage

    @Bind private double[] twRenewablesGrowth;  // Growth rate of renewables usage
    @Bind private double[] renewablesUsage;     // Renewable energy usage
    @Bind private double[] totalConsumption;    // Total energy consumption

    public ConsumptionModel() { }

    @Override
    public void run() {
        totalConsumption[0] = electricityUsage[0] + gasUsage[0] + renewablesUsage[0];
        for (int t = 1; t < LL; t++) {
            electricityUsage[t] = electricityUsage[t - 1] * twElectricityGrowth[t];
            gasUsage[t] = gasUsage[t - 1] * twGasGrowth[t];
            renewablesUsage[t] = renewablesUsage[t - 1] * twRenewablesGrowth[t];
            totalConsumption[t] = electricityUsage[t] + gasUsage[t] + renewablesUsage[t];
        }
    }
}