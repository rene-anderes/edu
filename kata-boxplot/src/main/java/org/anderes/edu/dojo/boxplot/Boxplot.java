package org.anderes.edu.dojo.boxplot;

import java.util.List;

import org.apache.commons.math3.stat.descriptive.rank.Max;
import org.apache.commons.math3.stat.descriptive.rank.Median;
import org.apache.commons.math3.stat.descriptive.rank.Min;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;

public class Boxplot {

    private final double[] values;

    public Boxplot(final List<Double> values) {
        this.values = new double[values.size()];
        for (int i = 0; i < values.size(); i++) {
            this.values[i] = values.get(i);
        }
    }

    public Double median() {
        final Median median = new Median();
        return median.evaluate(values);
    }

    public Double min() {
        final Min min = new Min();
        return min.evaluate(values);
    }

    public Double max() {
        final Max max = new Max();
        return max.evaluate(values);
    }

    public Double lowerQuartile() {
        final Percentile percentile = new Percentile(25d);
        return percentile.evaluate(values);
    }

    public Double upperQuartile() {
        final Percentile percentile = new Percentile(75d);
        return percentile.evaluate(values);
    }
}
