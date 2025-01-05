package data.models;

import data.abstraction.Model;
import data.annotations.Bind;

public class EconomyModel implements Model {
    @Bind private int LL;           // number of years

    @Bind private int[] LATA;      //years [from, to]

    @Bind private double[] twKI;    //the growth rate of private consumption
    @Bind private double[] twKS;    //the growth rate of public consumption
    @Bind private double[] twINV;   //investment growth
    @Bind private double[] twEKS;   //export growth
    @Bind private double[] twIMP;   //import growth

    @Bind private double[] KI;      //private consumption
    @Bind private double[] KS;      //public consumption
    @Bind private double[] INV;     //investment
    @Bind private double[] EKS;     //export
    @Bind private double[] IMP;     //import
    @Bind private double[] PKB;     //Gross domestic product


    public EconomyModel() { }

    @Override
    public void run() {
        PKB[0] = KI[0] + KS[0] + INV[0] + EKS[0] - IMP[0];
        for (int t = 1; t < LL; t++) {
            KI[t] = twKI[t] * KI[t - 1];
            KS[t] = twKS[t] * KS[t - 1];
            INV[t] = twINV[t] * INV[t - 1];
            EKS[t] = twEKS[t] * EKS[t - 1];
            IMP[t] = twIMP[t] * IMP[t - 1];
            PKB[t] = KI[t] + KS[t] + INV[t] + EKS[t] - IMP[t];
        }
    }
}