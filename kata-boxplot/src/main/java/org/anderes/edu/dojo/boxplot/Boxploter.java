package org.anderes.edu.dojo.boxplot;

import java.io.IOException;
import java.io.OutputStream;

public class Boxploter {

    private Double minValue;
    private Double maxValue;
    private int stepwith;

    public Boxploter(final Boxplot boxplot) {
        this.minValue = boxplot.min();
        this.maxValue = boxplot.max();
    }

    public void writeTo(final OutputStream out, int wide) throws IOException {
        calcStepwith(wide);
        writeHeader(out, wide, minValue, stepwith);
        write(out, System.lineSeparator());
        writeRaster(out, wide, minValue, stepwith);
    }

    private void calcStepwith(int wide) {
        double r = this.maxValue - this.minValue + 1;
        this.stepwith = (int) (wide / r);
    }

    private void writeHeader(final OutputStream out, int wide, double minValue, int stepwith) throws IOException {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < wide; i += stepwith) {
            final String value = String.format("%.0f", minValue++);
            final String space = buildSpacer(stepwith - value.length(), " ");
            builder.append(value).append(space);
        }
        write (out, builder.toString());
    }

    private void writeRaster(final OutputStream out, int wide, double minValue, int stepwith) throws IOException {
        final StringBuilder builder = new StringBuilder();
        builder.append("┌").append(buildSpacer(stepwith - 1, "─"));
        final String value = "┬";
        for (int i = stepwith; i < wide-stepwith; i += stepwith) {
            final String space = buildSpacer(stepwith - value.length(), "─");
            builder.append(value).append(space);
        }
        builder.append("┐");
        write (out, builder.toString());
    }
    
    private void write(final OutputStream out, final String text) throws IOException {
        out.write(text.getBytes());
    }
    
    private String buildSpacer(int count, final String c) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(c);
        }
        return sb.toString();
    }
}
