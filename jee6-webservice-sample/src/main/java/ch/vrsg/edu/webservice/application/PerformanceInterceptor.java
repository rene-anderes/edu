package ch.vrsg.edu.webservice.application;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.log4j.Logger;

@Interceptor
@Performance
public class PerformanceInterceptor {

    @Inject
    private Logger logger;
    
    @AroundInvoke
    public Object stopWatch(final InvocationContext context) throws Exception {
        if (!logger.isTraceEnabled()) {
            return context.proceed();
        }
        final StopWatch watch = new StopWatch();
        try {
            watch.start();
            return context.proceed();
        } finally {
            watch.stop();
            final String msg = String.format("%s:%s - runtime: %s ms]", 
                            context.getTarget().getClass().getCanonicalName(), context.getMethod().getName(), watch.getTime());
            logger.trace(msg); 
        }
    }
}
