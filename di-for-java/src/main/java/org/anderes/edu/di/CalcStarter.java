package org.anderes.edu.di;

import java.math.BigDecimal;
import java.util.Optional;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class CalcStarter {

    public static void main(String[] args) {
        /*
         * Guice.createInjector() takes your Modules, and returns a new Injector
         * instance. Most applications will call this method exactly once, in their
         * main() method.
         */
        Injector injector = Guice.createInjector(new CalcGuiceModule());
        
        /*
         * Now that we've got the injector, we can build objects.
         */
        Calc calc = injector.getInstance(Calc.class);
        
        calc.addToStack(BigDecimal.valueOf(20D));
        calc.addToStack(BigDecimal.valueOf(22D));
        Optional<BigDecimal> value = calc.addition();
        BigDecimal result = value.orElseGet(() -> BigDecimal.ZERO);
        System.out.println(String.format("Die Antwort auf alle Fragen lautet : %d", result.intValue()));
    }

}
