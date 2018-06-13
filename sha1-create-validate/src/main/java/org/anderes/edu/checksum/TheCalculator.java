package org.anderes.edu.checksum;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

public interface TheCalculator {

    ResultData eval(final Path theFile) throws FileNotFoundException, IOException;
}
