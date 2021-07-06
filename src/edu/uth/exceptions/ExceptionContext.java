package edu.uth.exceptions;

import java.io.Serializable;
import java.util.HashMap;


/**
 * This class holds information about the code context in which an Exception occurred. It
 * also allows arbitrary data to be associated with the exception occurrence for use by
 * associated ErrorHandlers
 *
 * @author Jim Harrington, Darwin Whaley
 **/
public final class ExceptionContext implements Serializable {

	private static final long serialVersionUID = -1;
	//
	// Instance Data
	//

	private int _severity;
	private Object _callingContext;
	private String _callingMethodName;
	private String _message;
	private Throwable _throwable;
	private HashMap _data;
	private boolean _runHandlers;

	/**
	 * Constructor
	 *
	 * @param severity One of the severity levels from com.techrx.util.Logger.
	 * @param callingContext The Object that caught the exception. If the Exception was
	 *            caught in a static class, the class must be passed (e.g.,
	 *            com.techrx.util.Logger.class).
	 * @param callingMethodName The name of the method where the Exception was caught. If
	 *            this method overrides or overloads other methods, some text indicating
	 *            which method was invoked is recommended.
	 * @param message A meaningful message that completely describes the Exception. This
	 *            could include values that were passed into the method, other information
	 *            about the state of the "callingContext" object at the time this
	 *            Exception occurred, etc. When deciding what to include here, pretend you
	 *            are the on-call developer who got called at 2:00 in the morning to fix
	 *            this Exception.
	 * @param throwable Typically, this will be an Exception. In rare instances, we may
	 *            want to capture Errors, such as an OutOfMemoryError, from which we may
	 *            be able to recover.
	 */
	public ExceptionContext(int severity,
			Object callingContext,
			String callingMethodName,
			String message,
			Throwable throwable,
			boolean runHandlers) {
		_severity = severity;
		_callingContext = callingContext;
		_callingMethodName = callingMethodName;
		_message = message;
		_throwable = throwable;
		_data = new HashMap();
		_runHandlers = runHandlers;
	}

	/**
	 * @return The severity of the Exception. These values must map to
	 *         com.techrx.util.Logger severity levels.
	 */
	public int getSeverity() {
		return _severity;
	}

	/**
	 * @return The Object in which the exception was caught
	 */
	public Object getCallingContext() {
		return _callingContext;
	}

	/**
	 * @return The name of the method in which the exception was caught
	 */
	public String getCallingMethodName() {
		return _callingMethodName;
	}

	/**
	 * @return The associated message
	 */
	public String getMessage() {
		return _message;
	}

	/**
	 * @return The exception associated with this context.
	 */
	public Throwable getException() {
		return _throwable;
	}

	/**
	 * @param severity One of the severity levels from com.techrx.util.Logger.
	 */
	public void setSeverity(int severity) {
		_severity = severity;
	}

	/**
	 * @param callingContext The Object that caught the exception. If the Exception was
	 *            caught in a static class, the class must be passed (e.g.,
	 *            com.techrx.util.Logger.class).
	 */
	public void setCallingContext(Object callingContext) {
		_callingContext = callingContext;
	}

	/**
	 * @param name The name of the method where the Exception was caught. If this method
	 *            overrides or overloads other methods, some text indicating which method
	 *            was invoked is recommended.
	 */
	public void setCallingMethodName(String name) {
		_callingMethodName = name;
	}

	/**
	 * @param message A meaningful message that completely describes the Exception. This
	 *            could include values that were passed into the method, other information
	 *            about the state of the "callingContext" object at the time this
	 *            Exception occurred, etc. When deciding what to include here, pretend you
	 *            are the on-call developer who got called at 2:00 in the morning to fix
	 *            this Exception.
	 *
	 */
	public void setMessage(String message) {
		_message = message;
	}

	/**
	 * @param throwable Typically, this will be an Exception. In rare instances, we may
	 *            want to capture Errors, such as an OutOfMemoryError, from which we may
	 *            be able to recover.
	 */
	public void setException(Throwable throwable) {
		_throwable = throwable;
	}

	/**
	 * Add a named data item to the context of the exception.
	 *
	 * @param name A unique name used to retrieve the data later
	 * @param data Data stored for later retrieval
	 * @see #getData
	 */
	public void addData(String name, Object data) {
		_data.put(name, data);
	}

	/**
	 * Get the value of a named data item.
	 *
	 * @param name The unique key under which this data was stored
	 * @see #addData
	 */
	public Object getData(String name) {
		return _data.get(name);
	}

	/**
	 * @return Returns the _runHandlers.
	 */
	public boolean isRunHandlers() {
		return _runHandlers;
	}
}
