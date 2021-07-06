/*
 * @(#)$Workfile: ValidationException.java$
 *
 * Copyright (c) 2000 TechRx. All Rights Reserved.
 *
 * @author: Dave Glasser
 *
 * Description:
 *
 *
 * $Date: 8/6/2009 2:36:58 PM$ $Archive:
 * //andromeda/TRexOneEnterprise/archives/Source/com/techrx/exceptions/ValidationException
 * .java-arc $ $History: 1 EPRN_SOURCE 1.0 2009-08-06 18:36:58Z Saurabh Dubey $
 * 
 * ***************** Version 1 ***************** User: Dglasser Date: 2/26/01 Time: 7:32p
 * Created in $/Darwin/src/com/techrx/exceptions Copied over from core; may have made some
 * changes.
 * 
 * ***************** Version 1 ***************** User: Dglasser Date: 2/05/01 Time: 4:35p
 * Created in $/core/com/techrx/exceptions Initial coding.
 * 
 * $NoKeywords$
 */

package edu.uth.exceptions;

public class ValidationException extends Exception {

	private static final long serialVersionUID = -1;

	/** Field value too long (101) */
	public static final int TOO_LONG = 101;

	/** Field value too short (102) */
	public static final int TOO_SHORT = 102;

	/** Attempt to assign an non-numeric value to a numeric field (103) */
	public static final int MUST_BE_NUMERIC = 103;

	/** Value is not in the valid set of values for this field (104) */
	public static final int NOT_IN_VALID_SET = 104;

	/** Value is lower than the start of the valid range (105) */
	public static final int OUT_OF_RANGE_LOW = 105;

	/** Value is higher than the end of the valid range (106) */
	public static final int OUT_OF_RANGE_HIGH = 106;

	/** Value is higher than the end of the valid range (107) */
	public static final int UNDEFINED_CODE = 107;

	/** Field is required; may not be null (201) */
	public static final int REQUIRED_FIELD = 201;

	/** Field missing data; process anyway (202) */
	public static final int MISSING_DATA_CONTINUE = 202;

	/** Field missing data; stop process (203) */
	public static final int MISSING_DATA_STOP = 203;

	/** Field not updateable once set (204) */
	public static final int NOT_UPDATEABLE = 204;

	/** Field was never set (205) */
	public static final int NEVER_SET = 205;

	/** Field was never loaded (206) */
	public static final int DATA_NOT_LOADED = 206;

	/** Missing or invalid data */
	public static final int INVALID_DATA = 207;

	/** Field must be null ( 208 ) */
	public static final int MUST_BE_NULL = 208;

	private int errorCode = 0;

	private String fieldName = null;

	public ValidationException() {
	}

	public ValidationException(String fieldName, int errorCode, String errorMsg) {
		super(errorMsg);
		this.fieldName = fieldName;
		this.errorCode = errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getFieldName() {
		return fieldName;
	}

}
