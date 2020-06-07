package com.pamesh.rest.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * The Class Async.
 *
 * @author pamesh1.bansal
 */
@ConfigurationProperties(prefix="application.async")
public class Async {

	/** The core pool size. */
	private int corePoolSize = 2;

	/** The max pool size. */
	private int maxPoolSize = 20;

	/** The queue capacity. */
	private int queueCapacity = 500;

	/**
	 * Gets the core pool size.
	 *
	 * @return the core pool size
	 */
	public int getCorePoolSize() {
		return corePoolSize;
	}

	/**
	 * Sets the core pool size.
	 *
	 * @param corePoolSize the new core pool size
	 */
	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	/**
	 * Gets the max pool size.
	 *
	 * @return the max pool size
	 */
	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	/**
	 * Sets the max pool size.
	 *
	 * @param maxPoolSize the new max pool size
	 */
	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	/**
	 * Gets the queue capacity.
	 *
	 * @return the queue capacity
	 */
	public int getQueueCapacity() {
		return queueCapacity;
	}

	/**
	 * Sets the queue capacity.
	 *
	 * @param queueCapacity the new queue capacity
	 */
	public void setQueueCapacity(int queueCapacity) {
		this.queueCapacity = queueCapacity;
	}
}
