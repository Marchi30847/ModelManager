totalCostGrowth = new double[LL]
totalCostGrowth[0] = 0
for (t = 1; t < LL; t++) {
    if (totalHealthcareCost[t - 1] != 0) {
        totalCostGrowth[t] = ((totalHealthcareCost[t] - totalHealthcareCost[t - 1]) / totalHealthcareCost[t - 1]) * 100
    } else {
        totalCostGrowth[t] = 0
    }
}