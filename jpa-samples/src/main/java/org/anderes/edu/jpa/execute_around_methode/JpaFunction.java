package org.anderes.edu.jpa.execute_around_methode;

@FunctionalInterface
public interface JpaFunction<T> {
    T execute();
}
