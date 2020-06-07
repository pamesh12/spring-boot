package com.pamesh.rest.advice;

import java.lang.reflect.Method;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class AsyncExceptionHandler.
 *
 * @author Pamesh Bansal
 */
@Slf4j
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler#
	 * handleUncaughtException(java.lang.Throwable, java.lang.reflect.Method,
	 * java.lang.Object[])
	 */
	@Override
	public void handleUncaughtException(Throwable ex, Method method, Object... params) {
		if (log.isErrorEnabled()) {
			log.error("Unexpected exception occurred invoking async method: " + method, ex);
		}
	}

}
