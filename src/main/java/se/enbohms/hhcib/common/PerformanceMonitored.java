package se.enbohms.hhcib.common;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

/**
 * Annotation which can be used by various method to determine the time it takes
 * to execute that particular method
 * 
 * 
 */
@InterceptorBinding
@Target({ METHOD, TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PerformanceMonitored {

}