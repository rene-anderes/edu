package org.anderes.edu.dojo.converter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class ArgumentHandler {

    private final String[] args;

    public ArgumentHandler(String[] args) {
        this.args = args;
    }

    public Boolean isRecursive() {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-r")) {
                return true;
            }
        }
        return false;
    }

    public Optional<String> getConvertFrom() {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("–convertFrom") && args[i+1] != null && !args[i+1].startsWith("-")) {
                return Optional.of(args[i+1]);
            }
        }
        return Optional.empty();
    }

    public Optional<String> getConvertTo() {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("–convertTo") && args[i+1] != null && !args[i+1].startsWith("-")) {
                return Optional.of(args[i+1]);
            }
        }
        return Optional.empty();
    }

    public Optional<String> getFile() {
        if (args.length > 0 && !args[0].startsWith("-")) {
            return Optional.of(args[0]);
        }
        return Optional.empty();
    }

    public Optional<Path> getDirectory() {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-d") && args[i+1] != null && !args[i+1].startsWith("-")) {
                final String arg = args[i+1].replaceAll("[\"]", "");
                return Optional.of(Paths.get(arg));
            }
        }
        return Optional.empty();
    }

    public Boolean checkArguments() {
        return getFile().isPresent() && getConvertFrom().isPresent() && getConvertTo().isPresent();
    }

}
