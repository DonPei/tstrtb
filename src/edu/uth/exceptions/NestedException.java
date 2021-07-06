package edu.uth.exceptions;

/** @author Tom Stachura */

/*
 * Updated 8/2/2004 by PJM to use Java 1.4 nested or 'cause' feature of Throwable
 * Maintained for backward compatibility.
 */

public class NestedException
		extends TRException {

	private static final long serialVersionUID = -1;

	public NestedException(int errorcode) {
		super(errorcode);
	}

	public NestedException(int errorcode, String message) {
		super(errorcode, message);
	}

	public NestedException(int errorcode, String message, Throwable nestedThrowable) {
		super(errorcode, message == null ? "" : message, nestedThrowable);
		updateErrorCode();
	}

	public NestedException(int errorcode, Throwable nestedThrowable) {
		super(errorcode, "", nestedThrowable);
		updateErrorCode();
	}

	public Throwable getNestedThrowable() {
		return getCause();
	}

	public Throwable getRootThrowable() {
		Throwable nestedThrowable = getCause();
		if ((nestedThrowable != null) && (nestedThrowable instanceof NestedException))
			return ((NestedException) nestedThrowable).getRootThrowable();
		else if (nestedThrowable != null)
			return nestedThrowable;
		else
			return this;
	}

	public String getNestedMessage() {
		StringBuffer message = new StringBuffer();

		message.append(this.getMessage());

		Throwable nestedThrowable = getCause();
		if (nestedThrowable != null) {
			message.append("[ ");
			if (nestedThrowable instanceof NestedException) {
				message.append(((NestedException) nestedThrowable).getNestedMessage());
			} else if (nestedThrowable.getMessage() != null) {
				message.append(nestedThrowable.getMessage());
			}
			message.append(" ]");
		}

		return message.toString();
	}

	/**
	 * Return the first Throwable object within the "nested" Throwable chain that is an
	 * instance of the passed Object. A non-null result is returned if a "nested"
	 * Throwable's Class is "castable" to the Class for the passed Object. If no such
	 * Throwable is found, or if the objectToFind parameter is null, null is returned.
	 *
	 * @param objectToFind An Object whose Class is to be used in the search
	 *
	 * @return The first instance of a Throwable within the "nested" chain that is an
	 *         instance of the Object parameter
	 */
	public Throwable firstInstanceOf(Object objectToFind) {
		Throwable foundInstance = null;

		if (objectToFind != null) {
			final Class objectToFindsClass = objectToFind.getClass();
			// First check to see if "this" is of the desired class
			if (objectToFindsClass.isInstance(this)) {
				foundInstance = this;
			} else {
				// Now, if there is a nested Throwable, check it first
				Throwable nestedThrowable = getCause();
				if (nestedThrowable != null) {
					// If the nested Throwable is a NestedException, let it check as well
					if (nestedThrowable instanceof NestedException) {
						foundInstance = ((NestedException) nestedThrowable).firstInstanceOf(objectToFind);
					} else {
						// Not a NestedException, so check it directly!
						if (objectToFindsClass.isInstance(nestedThrowable)) {
							foundInstance = nestedThrowable;
						}
					}
				}
			}
		}

		return foundInstance;
	}

	//
	// Private Helper Methods
	//

	private void updateErrorCode() {
		Throwable nestedThrowable = getCause();
		if (nestedThrowable != null) {
			setErrorCode(determineErrorCode(nestedThrowable));
		}
	}

	private int determineErrorCode(Throwable throwable) {
		int rtn = getErrorCode();
		Throwable anotherThrowable = null;

		if (throwable != null) {
			// if ( throwable instanceof java.sql.SQLException )
			// rtn = TRException.SQL_EXCEPTION;
			// else if ( throwable instanceof java.io.IOException )
			// rtn = TRException.IO_EXCEPTION;
			// else if ( throwable instanceof javax.jms.JMSException )
			// rtn = TRException.MESSAGING_EXCEPTION;
			if ((throwable instanceof NestedException) &&
					((anotherThrowable = ((NestedException) throwable).getRootThrowable()) != null) &&
					(!(anotherThrowable == throwable)) &&
					(!(anotherThrowable == this)))
				rtn = determineErrorCode(anotherThrowable);
			else if (throwable instanceof TRException)
				rtn = ((TRException) throwable).getErrorCode();
		}

		return rtn;
	}
}
