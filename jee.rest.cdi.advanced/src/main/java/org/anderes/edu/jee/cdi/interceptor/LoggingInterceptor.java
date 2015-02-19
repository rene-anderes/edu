package org.anderes.edu.jee.cdi.interceptor;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.logging.log4j.Logger;

@Interceptor
@Logging
public class LoggingInterceptor {

    @Inject
    private Logger logger;
    
    @AroundInvoke
    public Object log(InvocationContext context) throws Exception {
        
        String parameters = "";
        if (logger.isTraceEnabled()) {
            final StringBuffer buffer = new StringBuffer();
            for(Object o : context.getParameters()) {
                buffer.append(o.toString()).append(" ");
            }
            parameters = buffer.toString();
        }
        logger.trace("Call %s:%s:[%s]", context.getTarget().getClass().getCanonicalName(), context.getMethod().getName(), parameters);
        try {
            return context.proceed();
        } finally {
            logger.trace("End %s:%s:[%s]", context.getTarget().getClass().getCanonicalName(), context.getMethod().getName(), parameters); 
        }
    }
}
