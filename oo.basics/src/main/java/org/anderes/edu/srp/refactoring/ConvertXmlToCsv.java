package org.anderes.edu.srp.refactoring;

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

    public void convert(String fileName) {
       if (fileIsXml(fileName)) {
          String content = readContent(fileName);
          String converted = convertXml(content);
          String newFileName = createCsvFileName(fileName);
          writeContent(newFileName, converted);
      } else {
          throw new IllegalArgumentException("Unbekannter Dateityp!");
     }
   }

    private String convertXml(String content) {
        return "xml to csv";
    }

    private boolean fileIsXml(String fileName) {
        String suffix = getSuffix(fileName);
        return "xml".equals(suffix);
    }

    private String createCsvFileName(String fileName) {
        int endIndex = fileName.lastIndexOf(".");
        return fileName.substring(0, endIndex) + ".csv";
    }

    private String getSuffix(String fileName) {
        int beginIndex = fileName.lastIndexOf(".");
        int endIndex = fileName.length();
        return fileName.substring(beginIndex, endIndex);
    }

    private String readContent(String fileName) {
        return "content";
    }

    private void writeContent(String newFileName, String converted) {
        // write the oontent
    }
}