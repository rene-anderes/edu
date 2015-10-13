package org.anderes.edu.dojo.java8.news.stars;

import java.io.IOException;
import java.io.UncheckedIOException;
import static java.nio.charset.StandardCharsets.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StarReader {

    private final Path csvFile;
    
    private StarReader(final Path csvFile) {
        super();
        this.csvFile = csvFile;
    }

    public static StarReader build(final Path csvFile) {
        return new StarReader(csvFile);
    }

    public Collection<Star> readStars() {
        try (Stream<String> lines = Files.lines(csvFile, UTF_8)) {
            final Collection<Star> stars = lines.skip(1)
                 .map(line -> line.split(";"))
                 .map(s -> Star.create(s[0], s[1], s[2]))
                 .collect(Collectors.toSet());
            return stars;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } 
    }

}
