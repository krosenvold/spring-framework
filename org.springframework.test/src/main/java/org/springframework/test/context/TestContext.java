/*
 * Copyright 2002-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.test.context;

import java.lang.reflect.Method;

import org.springframework.context.ApplicationContext;
import org.springframework.core.AttributeAccessorSupport;
import org.springframework.core.style.ToStringCreator;

/**
 * TestContext encapsulates the context in which a test is executed, agnostic of
 * the actual testing framework in use.
 *
 * Notes on thread safety:
 *
 * All the calls to updateState will come in on the same thread, since the test frameworks normally run the entire test cycle for
 * a given method on the same thread. This means the volatile state in this class is thread safe, but may need safe publication
 * for collection of results from another thread.
 *
 * @author Sam Brannen
 * @author Juergen Hoeller
 * @since 2.5
 */
public class TestContext extends AttributeAccessorSupport {

	private static final long serialVersionUID = -5827157174866681233L;

	private volatile Object testInstance;

	private volatile Method testMethod;

    private volatile Throwable testException;

	private final TestClassContext testClassContext;



	public TestContext(TestClassContext testClassContext, Object testInstance, Method testMethod) {
		this.testClassContext = testClassContext;
		this.testInstance = testInstance;
		this.testMethod = testMethod;
	}


    /**
	 * Get the {@link ApplicationContext application context} for this test
	 * context, possibly cached.
	 *
	 * @return the application context
	 * @throws IllegalStateException if an error occurs while retrieving the
	 * application context
	 */
	public ApplicationContext getApplicationContext() {
		return testClassContext.getApplicationContext();
	}

	/**
	 * Get the {@link Class test class} for this test context.
	 *
	 * @return the test class (never <code>null</code>)
	 */
	public final Class<?> getTestClass() {
		return this.testClassContext.getTestClass();
	}

	/**
	 * Get the current {@link Object test instance} for this test context.
	 * <p>
	 * Note: this is a mutable property.
	 *
	 * @return the current test instance (may be <code>null</code>)
	 */
	public final Object getTestInstance() {
		return this.testInstance;
	}

	/**
	 * Get the current {@link Method test method} for this test context.
	 * <p>
	 * Note: this is a mutable property.
	 *
	 * @return the current test method (may be <code>null</code>)
	 */
	public final Method getTestMethod() {
		return this.testMethod;
	}

	/**
	 * Get the {@link Throwable exception} that was thrown during execution of
	 * the {@link #getTestMethod() test method}.
	 * <p>
	 * Note: this is a mutable property.
	 *
	 * @return the exception that was thrown, or <code>null</code> if no
	 * exception was thrown
	 */
	@SuppressWarnings( { "UnusedDeclaration" } )
    public final Throwable getTestException() {
		return this.testException;
	}

	/**
	 * Call this method to signal that the {@link ApplicationContext application
	 * context} associated with this test context is <em>dirty</em> and should
	 * be reloaded. Do this if a test has modified the context (for example, by
	 * replacing a bean definition).
	 */
	public void markApplicationContextDirty() {
    	 testClassContext.markApplicationContextDirty();
	}

	/**
	 * Update this test context to reflect the state of the currently executing
	 * test.
	 *
	 * @param testInstance the current test instance (may be <code>null</code>)
	 * @param testMethod the current test method (may be <code>null</code>)
	 * @param testException the exception that was thrown in the test method, or
	 * <code>null</code> if no exception was thrown
	 */
	void updateState(Object testInstance, Method testMethod, Throwable testException) {
		this.testInstance = testInstance;
		this.testMethod = testMethod;
		this.testException = testException;
	}

	/**
	 * Provide a String representation of this test context's state.
	 */
	@Override
	public String toString() {
		return new ToStringCreator(this)//
		.append("testClass", testClassContext.toString())//
		.append("testInstance", this.testInstance)//
		.append("testMethod", this.testMethod)//
		.append("testException", this.testException)//
		.toString();
	}

}
