package org.anderes.edu.di;

import java.math.BigDecimal;
import java.util.Optional;

public class CalcStarter {

    public static void main(String[] args) {
        final Calc calc = new Calc();
        calc.addToStack(BigDecimal.valueOf(20D));
        calc.addToStack(BigDecimal.valueOf(22D));
        Optional<BigDecimal> value = calc.addition();
        BigDecimal result = value.orElseGet(() -> BigDecimal.ZERO);
        System.out.println(String.format("Die Antwort auf alle Fragen lautet : %d", result.intValue()));
    }

}
