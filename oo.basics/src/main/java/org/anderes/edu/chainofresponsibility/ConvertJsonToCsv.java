package org.anderes.edu.chainofresponsibility;

public class ConvertJsonToCsv implements ToCsvConverter {
    private ToCsvConverter successor;

    @Override
    public String convert(final StructuredFile structuredFile) {
        if (canConvert(structuredFile)) {
            return convert(structuredFile.getContent());
        }
        if (successor != null) {
            return successor.convert(structuredFile);
        }
        throw new RuntimeException("Unbekannter Dateitypel");
    }

    public void setSuccessor(final ToCsvConverter successor) {
        this.successor = successor;
    }

    private boolean canConvert(final StructuredFile structuredFile) {
        return "json".equals(structuredFile.getSuffix());
    }

    protected String convert(final String content) {
        return "json to csv";
    }
}
