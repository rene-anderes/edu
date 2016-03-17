package org.anderes.edu.dojo.converter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class JConverter {

    private final OutputStream output;
    private final ArgumentHandler argument;

    /*package*/ JConverter(String[] args, OutputStream output) {
        this.output = output;
        this.argument = new ArgumentHandler(args);
    }

    public static void main(String[] args) {
        final JConverter converter = new JConverter(args, System.out);
        if (!converter.checkArguments()) {
            converter.dumpHelp();
            System.exit(1);
        }
        final List<Path> files = converter.getFileList();
        files.stream().forEach(f -> converter.convert(f));
    }
    
    /*package*/ void dumpHelp() {
        try {
            output.write("Fehlerhafter Aufruf.".getBytes());
        } catch (IOException e) {
            // nothing to do ...
        }
    }

    /*package*/ Boolean checkArguments() {
        return argument.checkArguments();
    }

    /*package*/ void convert(final Path file) {
        try {
            final Path tempFile = Files.createTempFile("Converter", ".tmp");
            final OutputStream outputStream = new FileOutputStream(tempFile.toFile());
            final InputStream inputStream = new FileInputStream(file.toFile());
            final EncodingConverter converter = new EncodingConverter(argument.getConvertFrom().get(), argument.getConvertTo().get());

            converter.convert(inputStream, outputStream);

            inputStream.close();
            outputStream.close();
            
            Files.copy(tempFile, new FileOutputStream(file.toFile()));
            Files.delete(tempFile);
            output.write(("File '" + file.toString() + "' konvertiert.").getBytes());
        } catch (IOException e) {
            try {
                output.write(e.getMessage().getBytes());
            } catch (IOException e1) {
                // nothing to do ...
            }
            System.exit(2);
        } 
    }

    /*package*/ List<Path> getFileList() {
        final FileFilter fileFilter = new FileFilter(argument.getDirectory().get(), argument.getFile().get(), argument.isRecursive());
        return fileFilter.getFileList();
    }

    
}
