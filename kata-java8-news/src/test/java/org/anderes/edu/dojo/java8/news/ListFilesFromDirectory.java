package org.anderes.edu.dojo.java8.news;

import java.io.File;
import java.io.FileFilter;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Test;

public class ListFilesFromDirectory {

    private File tempDir = Paths.get("d:", "temp").toFile();

    @Test
    public void shouldBeListAllFiles() {

        File[] dateien = tempDir.listFiles(File::isFile);

        Arrays.stream(dateien).forEach(System.out::println);
    }

    @Test
    public void shouldBeListAllFilesOldStyle() {
        File[] dateien = tempDir.listFiles(
                        new FileFilter() {
                            @Override
                            public boolean accept(File f) {
                                return f.isFile();
                            }
                        });
        
        for(File file : dateien) {
            System.out.println(file);
        }
    }
}
