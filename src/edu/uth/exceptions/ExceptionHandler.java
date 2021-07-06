package edu.uth.exceptions;

import java.util.HashMap;

/**
 * This class encapsulates the concept of handling of an exceptional condition.
 *
 * ExceptionHandler provides a means to delegate exception handling to something other
 * than the class that caught the Exception. With this mechanism, we can isolate common
 * exception handling tasks to a limited number of classes, and make global changes to
 * exception handling without going into every class that has a try/catch block.
 *
 * At this time, the only common requirement for all Exceptions is to log the Exception.
 * The static "handleException()" method on this class does this. The implementation of
 * this abstract class may change in the future to handle certain Exceptions differently.
 * For instance, certain critical exceptions might trigger alerts and pagers. Again, we
 * can accomplish this without modifying every class where these Exceptions can be thrown.
 *
 * This class may be subclassed. More likely, developers will simply use the static
 * "handleException()" method. If this class is subclassed, the subclasses must be passed
 * in as parameters to the static "handleException" method so that common tasks
 * implemented in the static method can be invoked.
 *
 * @author Jim Harrington, Darwin Whaley
 *
 **/
public abstract class ExceptionHandler {

	private static final long serialVersionUID = -1;
	private static HashMap _exceptionHandlers = new HashMap();

	private static String _lastMessage = null;

	/**
	 * Protected constructor so that only subclasses can be instantiated.
	 **/
	protected ExceptionHandler() {
	}

	/**
	 * Handle the passed Exception. This abstract method must be overridden to add
	 * specialized behavior. The method is intentionally protected because classes that
	 * have a reference to this instance must invoke the <b>static</b> handleException
	 * method, not this instance method. This design allows us to put required behavior in
	 * the static method with no chance of it being overridden. If you extend this class,
	 * pass an instance of it to the static handleException method.
	 *
	 * @param context This class provides the context of the exception, such as the object
	 *            or static class which caught the exception, the method name, the
	 *            severity of the exception, and a message.
	 **/
	protected abstract void handleException(ExceptionContext context);

	/**
	 * Handle the passed Exception. This abstract method must be overridden to add
	 * specialized behavior. The method is intentionally protected because classes that
	 * have a reference to this instance must invoke the <b>static</b> handleException
	 * method, not this instance method. This design allows us to put required behavior in
	 * the static method with no chance of it being overridden. If you extend this class,
	 * pass an instance of it to the static handleException method.
	 *
	 * @param context This class provides the context of the exception, such as the object
	 *            or static class which caught the exception, the method name, the
	 *            severity of the exception, and a message.
	 **/
	protected void handleMinorException(ExceptionContext context) {
		handleException(context);
	}

	//
	// Static Usage Interfaces
	//

	/**
	 * Handle the passed Exception. This is a convenience method which matches
	 * com.techrx.util.Logger's "log()" method, allowing easy transition for catch blocks
	 * that are currently logging, but not calling any exception handling routines. This
	 * is the generic exception handling mechanism. This should be called in most cases
	 * except when you want to handle an exception in a specific way - such as displaying
	 * a specific user-friendly error message. On the client, this method will run a
	 * registered handler that displays a generic error message.
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
	 * @return This method returns the entire log statement that was logged in the process
	 *         of handling this request. Typically, calling methods will not need this
	 *         information, and will invoke this method as if it were a "void".
	 */
	public static String handleException(int severity,
			Object callingContext,
			String callingMethodName,
			String message,
			Throwable throwable) {
		// Future enhancement - reuse ExceptionContexts from a pool
		String retVal = handleException(new ExceptionContext(severity,
				callingContext,
				callingMethodName,
				message,
				throwable,
				true), null);
		if (throwable instanceof TRException) {
			TRException tre = (TRException) throwable;
			tre.setLogged();
		}
		return retVal;
	}

	/**
	 * Handle the passed Exception. This is a convenience method which matches
	 * com.techrx.util.Logger's "log()" method, allowing easy transition for catch blocks
	 * that are currently logging, but not calling any exception handling routines. This
	 * method will not run the configured handlers and hence it allows specific handling
	 * of exceptions. This method should be called when you need to display a
	 * user-friendly error message or carry out alternative control paths in case of
	 * exceptions.
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
	 * @return This method returns the entire log statement that was logged in the process
	 *         of handling this request. Typically, calling methods will not need this
	 *         information, and will invoke this method as if it were a "void".
	 */
	public static String handleNonGenericException(int severity,
			Object callingContext,
			String callingMethodName,
			String message,
			Throwable throwable) {
		// Future enhancement - reuse ExceptionContexts from a pool
		String retVal = handleException(new ExceptionContext(severity,
				callingContext,
				callingMethodName,
				message,
				throwable,
				false), null);
		if (throwable instanceof TRException) {
			TRException tre = (TRException) throwable;
			tre.setLogged();
		}
		return retVal;
	}

	/**
	 * Handle the passed Exception. This is a convenience method which matches
	 * com.techrx.util.Logger's "log()" method, allowing easy transition for catch blocks
	 * that are currently logging, but not calling any exception handling routines. This
	 * method will not run the configured handlers and hence it allows specific handling
	 * of exceptions. This method should be called when you need to display a
	 * user-friendly error message or carry out alternative control paths in case of
	 * exceptions.
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
	 * @return This method returns the entire log statement that was logged in the process
	 *         of handling this request. Typically, calling methods will not need this
	 *         information, and will invoke this method as if it were a "void".
	 */
	public static String handleMinorException(int severity,
			Object callingContext,
			String callingMethodName,
			String message,
			Throwable throwable) {
		// Future enhancement - reuse ExceptionContexts from a pool
		String retVal = handleMinorException(new ExceptionContext(severity,
				callingContext,
				callingMethodName,
				message,
				throwable,
				true), null);
		if (throwable instanceof TRException) {
			TRException tre = (TRException) throwable;
			tre.setLogged();
		}
		return retVal;
	}

	/**
	 * Handle the passed Exception. Look up the appropriate handlers, based on context,
	 * and allow them to handle the passed exception. ALL Exceptions passed to this method
	 * will be logged. Do not invoke Logger.log() directly while handling Exceptions -
	 * allow this method to do the logging for you.
	 *
	 * @param context This class provides the context of the exception, such as the object
	 *            or static class which caught the exception, the method name, the
	 *            severity of the exception, and a message. If "null" is passed, this
	 *            method will simply return.
	 * @param handlers null, or 1 to many subclasses of this class. Handlers will be
	 *            invoked in the order in which they are passed, starting at the zeroeth
	 *            array entry, and AFTER any default handling by this static method has
	 *            occurred. "null" is allowed.
	 * @return This method returns the entire log statement that was logged in the process
	 *         of handling this Exception. Typically, calling methods will not need this
	 *         information, and will invoke this method as if it were a "void".
	 */
	public static String handleException(ExceptionContext context,
			ExceptionHandler[] handlers) {
		if (context == null)
			return null;

		_lastMessage = context.getMessage();

		//
		// First, we'll log the exception
		//

		String returnString = context.getSeverity()+" "+
				context.getCallingContext()+" "+
				context.getCallingMethodName()+" "+
				context.getMessage()+" "+
				context.getException();

		//
		// Now, lets handle configured handlers
		//

		if (context.isRunHandlers()) {
			ExceptionHandler[] confighandlers = getConfiguredHandlers(context);

			if ((confighandlers != null) &&
					(confighandlers.length > 0)) {
				runErrorHandlers(context, confighandlers);
			}

			//
			// Finally, lets handle passed handlers
			//

			if ((handlers != null) &&
					(handlers.length > 0)) {
				runErrorHandlers(context, handlers);
			}
		}
		return returnString;
	}

	/**
	 * Handle the passed Exception. Look up the appropriate handlers, based on context,
	 * and allow them to handle the passed exception. ALL Exceptions passed to this method
	 * will be logged. Do not invoke Logger.log() directly while handling Exceptions -
	 * allow this method to do the logging for you.
	 *
	 * @param context This class provides the context of the exception, such as the object
	 *            or static class which caught the exception, the method name, the
	 *            severity of the exception, and a message. If "null" is passed, this
	 *            method will simply return.
	 * @param handlers null, or 1 to many subclasses of this class. Handlers will be
	 *            invoked in the order in which they are passed, starting at the zeroeth
	 *            array entry, and AFTER any default handling by this static method has
	 *            occurred. "null" is allowed.
	 * @return This method returns the entire log statement that was logged in the process
	 *         of handling this Exception. Typically, calling methods will not need this
	 *         information, and will invoke this method as if it were a "void".
	 */
	public static String handleMinorException(ExceptionContext context,
			ExceptionHandler[] handlers) {
		if (context == null)
			return null;

		_lastMessage = context.getMessage();

		//
		// First, we'll log the exception
		//

		String returnString = context.getSeverity()+" "+
				context.getCallingContext()+" "+
				context.getCallingMethodName()+" "+
				context.getMessage()+" "+
				context.getException();

		//
		// Now, lets handle configured handlers
		//

		if (context.isRunHandlers()) {
			ExceptionHandler[] confighandlers = getConfiguredHandlers(context);

			if ((confighandlers != null) &&
					(confighandlers.length > 0)) {
				runMinorErrorHandlers(context, confighandlers);
			}

			//
			// Finally, lets handle passed handlers
			//

			if ((handlers != null) &&
					(handlers.length > 0)) {
				runMinorErrorHandlers(context, handlers);
			}
		}
		return returnString;
	}

	public static void clearLastMessage() {
		_lastMessage = null;
	}

	public static String getLastMessage() {
		return _lastMessage;
	}

	public static void registerSecondaryHandler(ExceptionHandler handler) {
		_exceptionHandlers.put(handler, handler);
	}

	public static void unregisterSecondaryHandler(ExceptionHandler handler) {
		_exceptionHandlers.remove(handler);
	}

	//
	// Private Helpers
	//

	/**
	 * This method returns null for now. Future enhancements include configurable handlers
	 * that can be assembled at run time.
	 */
	private static ExceptionHandler[] getConfiguredHandlers(ExceptionContext context) {
		if (_exceptionHandlers.isEmpty())
			return null;

		return (ExceptionHandler[]) _exceptionHandlers.values().toArray(new ExceptionHandler[0]);
	}

	private static void runErrorHandlers(ExceptionContext context,
			ExceptionHandler[] handlers) {
		if ((context == null) ||
				(handlers == null))
			return;

		final int handlerCount = handlers.length;

		for (int i = 0; i < handlerCount; ++i) {
			handlers[i].handleException(context);
		}
	}

	private static void runMinorErrorHandlers(ExceptionContext context,
			ExceptionHandler[] handlers) {
		if ((context == null) ||
				(handlers == null))
			return;

		final int handlerCount = handlers.length;

		for (int i = 0; i < handlerCount; ++i) {
			handlers[i].handleMinorException(context);
		}
	}

}
