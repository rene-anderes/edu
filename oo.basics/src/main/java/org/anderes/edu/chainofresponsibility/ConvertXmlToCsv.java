package org.anderes.edu.chainofresponsibility;

public class ConvertXmlToCsv implements ToCsvConverter {
    private ToCsvConverter successor;

    @Override
    public String convert(final StructuredFile structuredFile) {
        if (canConvert(structuredFile)) {
            return convert(structuredFile.getContent());
        }
        if (successor != null) {
            return successor.convert(structuredFile);
        }
        throw new RuntimeException("Unbekannter Dateitype!");
    }

    @Override
    public void setSuccessor(final ToCsvConverter successor) {
        this.successor = successor;
    }

    private boolean canConvert(final StructuredFile structuredFile) {
        return "xml".equals(structuredFile.getSuffix());
    }

    private String convert(final String content) {
        return "xml to csv";
    }
}