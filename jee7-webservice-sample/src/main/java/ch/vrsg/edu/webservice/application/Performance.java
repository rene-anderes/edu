package ch.vrsg.edu.webservice.application;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

/**
 * Dieser Interceptor misst die laufzeit einer Methode.
 * 
 * @author René Anderes
 *
 */
@InterceptorBinding
@Retention(RUNTIME)
@Target({METHOD, TYPE})
public @interface Performance {

}
