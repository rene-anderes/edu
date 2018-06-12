package org.anderes.edu.sha1;

import java.nio.file.Path;
import static com.google.common.base.Preconditions.*;

public class ResultData {
    
    private final String value;
    private Path path;
    
    public ResultData(final Path filePath, final String value) {
        super();
        checkNotNull(value);
        checkNotNull(filePath);
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
