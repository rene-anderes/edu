package org.anderes.edu.dojo.testdata;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestdataCreator {
    
    public interface Builder {
        List<List<?>> create(int rows);
        Builder add(Generator<?> generator);
    }

    private Builder builder;
    private static TestdataCreator instance;
    
    private TestdataCreator() {
        builder = new DataCreator();
    }
    
    private static TestdataCreator getInstance() {
        if (instance == null) {
            instance = new TestdataCreator();
        }
        return instance;
    }
    
    public static Builder add(final Generator<?> generator) {
        return getInstance().builder.add(generator);
    }

    private class DataCreator implements Builder {
        
        private final ArrayList<Generator<?>> generators = new ArrayList<>();

        @Override
        public List<List<?>> create(int rows) {
            final ArrayList<List<?>> list = new ArrayList<>(rows);
            for (int i = 0; i < rows; i++) {
                list.add(processGenerators());
            }
            return list;
        }

        @Override
        public Builder add(Generator<?> generator) {
            generators.add(generator);
            return this;
        }
        
        private List<?> processGenerators() {
            return generators.stream().map(g -> g.next()).collect(Collectors.toList());
        }
    }
}
