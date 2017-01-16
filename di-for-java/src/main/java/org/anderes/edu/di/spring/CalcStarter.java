package org.anderes.edu.di.spring;

import java.math.BigDecimal;
import java.util.Optional;

import org.anderes.edu.di.Calc;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CalcStarter {

    public static void main(String[] args) {
        
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring/spring-di.xml"});
        
//        ApplicationContext context = new AnnotationConfigApplicationContext(CalcConfigSpring.class);
        
        Calc calc = context.getBean(Calc.class);
        
        calc.addToStack(BigDecimal.valueOf(20D));
        calc.addToStack(BigDecimal.valueOf(22D));
        Optional<BigDecimal> value = calc.addition();
        BigDecimal result = value.orElseGet(() -> BigDecimal.ZERO);
        System.out.println(String.format("Die Antwort auf alle Fragen lautet : %d", result.intValue()));

        ((ConfigurableApplicationContext) context).close();
    }

}
