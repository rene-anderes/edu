package org.anderes.edu.dojo.testdata;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;


public class RandomAlphabeticGenerator implements Generator<String> {

    public interface Configure {
        Configure setMaxLenght(int max);
        Configure setMinLenght(int min);
        Generator<String> build();
    }

    private static RandomAlphabeticGenerator instance;
    private final ConfigureGenerator configure;
    
    private RandomAlphabeticGenerator() {
        this.configure = new ConfigureGenerator();
    }
    
    private static RandomAlphabeticGenerator getInstance() {
        if (instance == null) {
            instance = new RandomAlphabeticGenerator();
        }
        return instance;
    }
    
    public static Configure setMaxLenght(int max) {
        return  getInstance().configure.setMaxLenght(max);
    }
    
    public static Configure setMinLenght(int min) {
        return getInstance().configure.setMinLenght(min);
    }
    
    public static Generator<String> build() {
        return getInstance();
    }

    @Override
    public String next() {
        return RandomStringUtils.randomAlphabetic(configure.nextRandomInt());
    }

    private class ConfigureGenerator implements Configure {

        private int min = 1;
        private int max = 1000;
        private final Random random = new Random();
        
        @Override
        public Configure setMaxLenght(int max) {
            this.max = max;
            return this;
        }

        private int nextRandomInt() {
            return random.ints(min, max + 1).findFirst().getAsInt();
        }

        @Override
        public Configure setMinLenght(int min) {
            this.min = min;
            return this;
        }

        @Override
        public Generator<String> build() {
            return getInstance();
        }
    }
}
