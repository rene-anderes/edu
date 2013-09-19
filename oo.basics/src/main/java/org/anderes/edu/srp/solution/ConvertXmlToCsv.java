package org.anderes.edu.srp.solution;

/**
 * Diese Klasse konvertiert den Inhalt eins XML-Files in ein CSV-File.
 * </p>
 * Es wird ein neues CSV-File angelegt, welches den gleichen 
 * Pfad und Namen hat wie das ursprüngliche XML-File.
 * Die Fileendung wird jedoch angepasst: myFile.xml --> myFile.csv
 *
 * @author René Anderes
 */
public class ConvertXmlToCsv {

    public void convert(StructuredFile structuredFile) {
        if (fileIsXml (structuredFile)) {
            String content = structuredFile.getContent();
            String converted = convertXml(content);
            String newFileName = createCsvFileName(structuredFile);
            writeContent(newFileName, converted);
        } else {
            throw new IllegalArgumentException("Unbekannter Dateityp!");
        }
    }

    private String convertXml(String content) {
        return "xml to csv";
    }

    private boolean fileIsXml(StructuredFile structuredFile) {
        return "xml".equals(structuredFile.getSuffix());
    }

    private String createCsvFileName(StructuredFile structuredFile) {
        return structuredFile.getNameWithoutSuffix() + ".csv";
    }

    private void writeContent(String newFileName, String converted) {
	StructuredFile csvFile = new StructuredFile(newFileName + ".csv");
	csvFile.writeContent(converted);
    }
}