package se.enbohms.hhcib.common;

import java.util.logging.Logger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * Represents an implementation of the {@link PerformanceMonitored} aspect.
 * 
 */
@PerformanceMonitored
@Interceptor
public class PerformanceMonitoredInterceptor {

	private final static Logger LOG = Logger
			.getLogger(PerformanceMonitoredInterceptor.class.getName());

	@AroundInvoke
	public Object logExecutionTime(InvocationContext invocationContext)
			throws Exception {
		long startTime = System.nanoTime();
		try {
			return invocationContext.proceed();
		} finally {
			long processingTime = System.nanoTime() - startTime;
			LOG.info(invocationContext.getTarget().getClass() + "#" + invocationContext.getMethod().getName()
					+ " invocation took: " + processingTime / 1000000
					+ " milli seconds");
		}
	}
}