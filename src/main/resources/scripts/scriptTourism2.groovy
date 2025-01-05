NTGrowth = new double[LL]
NTGrowth[0] = 0
for (t = 1; t < LL; t++) {
    if (netRevenue[t - 1] != 0) {
        NTGrowth[t] = ((netRevenue[t] - netRevenue[t - 1]) / netRevenue[t - 1]) * 100
    } else {
        NTGrowth[t] = 0
    }
}
