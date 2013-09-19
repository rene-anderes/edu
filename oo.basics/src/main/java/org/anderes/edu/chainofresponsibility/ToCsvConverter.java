package org.anderes.edu.chainofresponsibility;

public interface ToCsvConverter {

    public String convert(StructuredFile structuredFile);

    public void setSuccessor(final ToCsvConverter successor);
}