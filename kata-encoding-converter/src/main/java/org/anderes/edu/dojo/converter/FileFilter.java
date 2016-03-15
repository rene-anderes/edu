package org.anderes.edu.dojo.converter;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

public class FileFilter {

    private final Path dir;
    private final int maxDepth;
    private final BiPredicate<Path, BasicFileAttributes> matcher;

    public FileFilter(final Path dir, final String file, final Boolean recursive) {
        this.dir = dir;
        maxDepth = recursive ? Integer.MAX_VALUE : 1;
        matcher = (p, a) -> { 
            return a.isRegularFile() && FileSystems.getDefault().getPathMatcher("glob:" + file).matches(p.getFileName());
        }; 
    }

    public List<Path> getFileList() {
        try {
            return Files.find(dir, maxDepth, matcher).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<Path>();
        }
    }
}
