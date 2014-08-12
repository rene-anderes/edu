package org.anderes.edu.srp.initial;

/**
 * Diese Klasse konvertiert den Inhalt eins XML-Files in ein CSV-File. </p>
 * Es wird ein neues CSV-File angelegt, welches den gleichen Pfad und Namen hat wie das ursprüngliche XML-File.<br>
 * Die Fileendung wird jedoch angepasst: myFile.xml --> myFile.csv
 *
 * @author René Anderes
 */
public class ConvertXmlToCsv {

    public void convert(String fileName) {
        int beginIndex = fileName.lastIndexOf(".");
        int endIndex = fileName.length();
        String suffix = fileName.substring(beginIndex, endIndex);
        if ("xml".equals(suffix)) {
            String content = readContent(fileName);
            String converted = convertXml(content);
            String newFileName = fileName.substring(0, beginIndex) + ".csv";
            writeContent(newFileName, converted);
        } else {
            throw new IllegalArgumentException("Unbekannter Dateityp");
        }
    }

    private String convertXml(String content) {
        return "xml to csv";
    }

    private String readContent(String fileName) {
         return  "content";
   }

    private void writeContent(String newFileName, String converted) {
        // write the oontent
    }
}
