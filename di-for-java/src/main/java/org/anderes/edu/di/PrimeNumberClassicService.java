package org.anderes.edu.di;

import static java.lang.Boolean.*;

public class PrimeNumberClassicService implements Service {

    @Override
    public Boolean isPrimeNumber(final Integer number) {
        if (number == 1) {
            return TRUE;
        } else {
            for (int i = 2; i <= Math.sqrt(number); i++) {

                if (number % i == 0)
                    return FALSE;
            }
            return TRUE;
        }
    }

}
