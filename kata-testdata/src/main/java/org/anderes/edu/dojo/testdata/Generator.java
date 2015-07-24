package org.anderes.edu.dojo.testdata;

public interface Generator<T extends Object> {

    T next();
}
