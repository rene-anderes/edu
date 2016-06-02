package ch.vrsg.edu.webservice.application;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Singleton;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Singleton
public class LoggerProducer {

    @Produces  
    public Logger produceLogger(InjectionPoint injectionPoint) {  
        return LogManager.getLogger(injectionPoint.getMember().getDeclaringClass().getName());  
    }  
}
