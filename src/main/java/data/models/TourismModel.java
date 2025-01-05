package data.models;

import data.abstraction.Model;
import data.annotations.Bind;

public class TourismModel implements Model {
    @Bind private int LL;             // number of years
    @Bind private int[] LATA;         // years [from, to]

    @Bind private double[] twTourismGrowth;  // growth rate of tourist arrivals
    @Bind private double[] twTourismRevenueGrowth; // growth rate of tourism revenue
    @Bind private double[] touristArrivals;  // number of tourist arrivals
    @Bind private double[] tourismRevenue;   // tourism revenue
    @Bind private double[] marketingExpenses; // marketing expenses

    @Bind private double[] twMarketingExpensesGrowth; // growth rate of marketing expenses
    @Bind private double[] netRevenue;       // calculated net revenue from tourism


    public TourismModel() { }

    @Override
    public void run() {
        netRevenue[0] = tourismRevenue[0] - marketingExpenses[0];
        for (int t = 1; t < LL; t++) {
            touristArrivals[t] = touristArrivals[t - 1] * twTourismGrowth[t];
            tourismRevenue[t] = tourismRevenue[t - 1] * twTourismRevenueGrowth[t];
            marketingExpenses[t] = marketingExpenses[t - 1] * twMarketingExpensesGrowth[t];
            netRevenue[t] = tourismRevenue[t] - marketingExpenses[t];
        }
    }
}