package se.enbohms.hhcib.common;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * Represents an implementation of the {@link PerformanceMonitored} aspect.
 * 
 * 
 */
@PerformanceMonitored
@Interceptor
public class PerformanceMonitoredInterceptor {

	@AroundInvoke
	public Object logExecutionTime(InvocationContext invocationContext)
			throws Exception {
		long startTime = System.nanoTime();
		try {
			return invocationContext.proceed();
		} finally {
			long processingTime = System.nanoTime() - startTime;
			System.out.println("Method: "
					+ invocationContext.getMethod().getName()
					+ " invocation took: " + processingTime / 1000000
					+ " milli seconds");
		}
	}
}