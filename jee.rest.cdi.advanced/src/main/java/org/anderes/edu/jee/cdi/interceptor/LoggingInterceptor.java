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
        
        final StringBuffer buffer = new StringBuffer();
        for(Object o : context.getParameters()) {
            buffer.append(o.toString()).append(" ");
        }
        
        logger.trace("{}:{}:[{}]", context.getTarget().getClass().getName(), context.getMethod().getName(), buffer.toString());
        
        return context.proceed();
    }
}
