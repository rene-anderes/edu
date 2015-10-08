package org.anderes.edu.dojo.java8.news.time;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Stream;

public class StarReader {

    private final Path csvFile;
    
    private StarReader(Path csvFile) {
        super();
        this.csvFile = csvFile;
    }

    public static StarReader build(final Path csvFile) {
        return new StarReader(csvFile);
    }

    public Collection<Star> readStars() {
        final Collection<Star> stars = new HashSet<>();
        try (Stream<String> lines = Files.lines(csvFile, StandardCharsets.UTF_8)) {
            lines.skip(1)
                 .map(line -> Arrays.asList(line.split(";")))
                 .forEach(s -> stars.add(Star.create(s.get(0), s.get(1), s.get(2))));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } 
        return stars;
    }

}
