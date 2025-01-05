cumulativeTR = new double[LL]
cumulativeTR[0] = tourismRevenue[0]
for (t = 1; t < LL; t++) {
    cumulativeTR[t] = cumulativeTR[t - 1] + tourismRevenue[t]
}

