package ch.vrsg.edu.webservice.application;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.log4j.Logger;

@Interceptor
@Audit
public class AuditInterceptor {

    @Inject
    private Logger logger;
    
    @AroundInvoke
    public Object log(InvocationContext context) throws Exception {

        String beginMsg = "";
        String endMsg = "";
        if (logger.isTraceEnabled()) {
            final StringBuffer buffer = new StringBuffer();
            for(Object o : context.getParameters()) {
                buffer.append("'").append(o.toString()).append("'").append(" ");
            }
            final String parameters = buffer.toString().trim();
            beginMsg = String.format("Call %s:%s:[%s]", context.getTarget().getClass().getName(), context.getMethod().getName(), parameters);
            endMsg = String.format("End %s:%s:[%s]", context.getTarget().getClass().getCanonicalName(), context.getMethod().getName(), parameters);
        }
        logger.trace(beginMsg);
        try {
            return context.proceed();
        } finally {
            logger.trace(endMsg); 
        }
        
    }
    
}
