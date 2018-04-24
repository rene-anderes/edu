package org.anderes.edu.sha1;

import java.nio.file.Path;

public class ResultData {
    
    private final String value;
    private Path path;
    
    public ResultData(final Path filePath, String value) {
        super();
        this.value = value;
        this.path = filePath;
    }

    public String getValue() {
        return value;
    }

    public Path getPath() {
        return path;
    }

}
