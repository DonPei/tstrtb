package edu.uth.exceptions;

import java.io.Serializable;

/**
 * A generic Exception class.
 *
 * This class accommodates two different Exception mechanisms. One is the more "pure" OO
 * concept of inheritance, and the other is the "less" pure concept of an exception whose
 * type can be determined by querying "getCode()".
 * <p>
 * The latter mechanism is useful in UI applications, where it is more practical to map
 * the code to potentially hundreds of human-readable error messages using resource
 * bundles rather than implementing hundreds of "catch" blocks, with a different user
 * message for each "catch". It is also easier to create help desk and user manuals with
 * error numbers than it is to list geeky sounding exceptions.
 * <p>
 * The "purer" implementation can still be accommodated by extending this class, and using
 * "catch" blocks as normal. If you extend this class, you must at least override
 * "getCode()" to return a unique code that can be used for resource lookups - preferrably
 * a code that has been defined as a constant in this class.
 * <p>
 * Updated 8/2/2004 by PJM to use Java 1.4 nested or 'cause' feature of Throwable
 * NestedException class will be maintained for backward compatibility, but will be
 * defined in terms of Throwable's 'cause'.
 *
 * @author Darwin Whaley
 */

/*
 * $Id$ $Pre-svn commit comments (no longer updated): 1 EPRN_SOURCE 1.0 2009-08-06
 * 18:36:58Z Saurabh Dubey $
 */

public class TRException extends Exception implements Serializable {

	private static final long serialVersionUID = -1;
	public final static int UNDEFINED_EXCEPTION = 0;

	// 1000 - 1999 Infrastructure exceptions
	public final static int IO_EXCEPTION = 1000;
	public final static int SQL_EXCEPTION = 1010;
	public final static int SQL_UNIQUE_CONSTRAINT_EXCEPTION = 1011;
	public final static int MESSAGING_EXCEPTION = 1020;
	public final static int XML_EXCEPTION = 1030;
	public final static int CONCURRENT_UPDATE_EXCEPTION = 1040;
	public final static int BUSINESS_EXCEPTION = 1050;
	public final static int DATA_FILTER_FACTORY_INIT_EXCEPTION = 1060;
	public final static int JSON_EXCEPTION = 1070;

	// 1900 Security exceptions
	public final static int AUTHENTICATION_EXCEPTION = 1900;
	public final static int ACCESS_NOT_ALLOWED_EXCEPTION = 1901;

	// 1300 Hardware exceptions
	public final static int DEVICE_IN_USE_EXCEPTION = 1300;
	public final static int NO_DOCUMENT_EXCEPTION = 1301;
	public final static int DOCUMENT_MISFEED_EXCEPTION = 1302;
	public final static int SCANNER_EXCEPTION = 1330;
	public final static int PRINTER_EXCEPTION = 1340;
	public final static int OUT_OF_PAPER_EXCEPTION = 1341;
	public final static int OUT_OF_TONER_EXCEPTION = 1342;
	public final static int COPIER_EXCEPTION = 1360;
	public final static int SIGNATURE_PAD_EXCEPTION = 1370;
	public final static int AUTOMATED_DISPENSING_EXCEPTION = 1380;

	// 2000 Domain exceptions
	public final static int ACCOUNT_EXCEPTION = 2010;
	public final static int ADJUDICATION_EXCEPTION = 2020;
	public final static int CLINICAL_EXCEPTION = 2030;
	public final static int CLAIM_EXCEPTION = 2040;
	public final static int DUR_EXCEPTION = 2050;
	public final static int INVENTORY_EXCEPTION = 2060;
	public final static int INVENTORY_DUPLICATE_POG_EXCEPTION = 2061;
	public final static int ORDER_EXCEPTION = 2070;
	public final static int PATIENT_EXCEPTION = 2080;
	public final static int PHARMACY_EXCEPTION = 2090;
	public final static int PRESCRIBER_EXCEPTION = 2100;
	public final static int PRICING_EXCEPTION = 2110;
	public final static int PRODUCT_EXCEPTION = 2120;
	public final static int REPORTING_EXCEPTION = 2130;
	public final static int RX_EXCEPTION = 2140;
	public final static int RX_FILL_EXCEPTION = 2150;
	public final static int THIRD_PARTY_EXCEPTION = 2160;
	public final static int WORKFLOW_EXCEPTION = 2170;
	public final static int WORKFLOW_ITEM_LOCKED_EXCEPTION = 2171;
	public final static int WORKFLOW_ITEM_REMOVED_EXCEPTION = 2172;
	public final static int ARCHIVE_EXCEPTION = 2180;
	public final static int AUDIT_EXCEPTION = 2190;
	public final static int BARCODE_EXCEPTION = 2200;
	public final static int BINMGT_EXCEPTION = 2210;
	public final static int CALLCTR_EXCEPTION = 2220;
	public final static int COMM_EXCEPTION = 2230;
	public final static int CONFIG_EXCEPTION = 2240;
	public final static int CREDITCARD_EXCEPTION = 2250;
	public final static int DISPENSE_EXCEPTION = 2260;
	public final static int DOCUMENT_EXCEPTION = 2270;
	public final static int EMAIL_EXCEPTION = 2280;
	public final static int FAX_EXCEPTION = 2290;
	public final static int FIELD_EXCEPTION = 2300;
	public final static int FORM_EXCEPTION = 2310;
	public final static int IMAGE_EXCEPTION = 2320;
	public final static int LABEL_EXCEPTION = 2330;
	public final static int ADMIN_EXCEPTION = 2340;
	public final static int SECURITY_EXCEPTION = 2350;
	public final static int PRINT_EXCEPTION = 2360;
	public final static int PURCHASE_EXCEPTION = 2370;
	public final static int DUR_HISTORY_EXCEPTION = 2380;
	public final static int HIERARCHY_EXCEPTION = 2390;
	public final static int SPONSOR_EXCEPTION = 2400;
	public final static int CCP_EXCEPTION = 2500;
	public final static int INTERVIEW_MODULE_EXCEPTION = 2510;
	public final static int LOGIN_EXCEPTION = 2520;
	public final static int QUICKCODE_EXCEPTION = 2530;
	public final static int PRE_EDIT_EXCEPTION = 2540;
	public final static int NDC_EXCEPTION = 2550;
	public final static int POS_EXCEPTION = 2560;
	public final static int POS_COMM_TIMEOUT_EXCEPTION = 2561;
	public final static int ONLINE_PAYMENT_EXCEPTION = 2562;
	public final static int ONLINE_PAYMENT_NO_CUSTOMER_ID_EXCEPTION = 2563;
	public final static int BILL_AFTER_RELEASE_EXCEPTION = 2570;
	public final static int DOWNTIME_EXCEPTION = 2580;
	public final static int SIG_ADMIN_EXCEPTION = 2590;
	public final static int TELEPHONY_API_EXCEPTION = 2600;
	public final static int RULE_ENGINE_EXCEPTION = 2610;
	public final static int SYNC_EXCEPTION = 2620;
	// For 2099 and 2123, If store is Closed all days
	public final static int STORE_IS_CLOSED_ALL_DAYS = 2630;
	public final static int POST_DISPENSING_AUDIT_EXCEPTION = 2640;

	public final static int CALCULATE_REFILL_REMAINING_EXCEPTION = 2650;
	public final static int USER_SEARCH_EXCEPTION = 2660;
	public final static int CERTIFICATE_SEARCH_EXCEPTION = 2690;
	public final static int HUB_ADMIN_EXCEPTION = 2700;
	// External Interface Exceptions
	public final static int EXTERNAL_INTERFACE_EXCEPTION = 3000;
	public static final int EXT_DUPLICATE_ORDER_EXCEPTION = 3001;
	public final static int EXT_WEB_INTERFACE_EXCEPTION = 3010;
	public final static int EXT_IVR_INTERFACE_EXCEPTION = 3020;
	public final static int EXT_FAX_INTERFACE_EXCEPTION = 3030;
	public final static int EXT_PPI_INTERFACE_EXCEPTION = 3040;
	public final static int EXT_PPI_NOT_PPI_FACILITY_EXCEPTION = 3041;
	public final static int EXT_PPI_FACILITY_NOTONFILE_EXCEPTION = 3042;
	public final static int EXT_AIM_INTERFACE_EXCEPTION = 3050;
	public final static int EXT_SUPPLIER_INTERFACE_EXCEPTION = 3060;
	// PI Webservice exceptions
	public final static int REFILL_SERVICE_INVALID_PROMISETIME = 3061;
	public final static int REFILL_SERVICE_JMS_MESSAGE_POSTEXCEPTION = 3062;
	public final static int REFILL_SERVICE_PROCESSING_EXCEPTION = 3063;

	public final static int RX_FILL_PRICING_DATA_EXCEPTION = 3064;

	// 9000 Miscellaneous exceptions
	public final static int RECORD_IN_USE_EXCEPTION = 9000;

	// 9500 Framework exceptions
	public final static int SCHEDULE_DEFINITION_EXCEPTION = 9500;

	// 9501 JWT exceptions
	public final static int JWT_EXCEPTION = 9501;

	// 2 COMMUNICATION EXCEPTION
	// Added by Sumit Gupta
	public final static int COMMUNICATION_EXCEPTION = 2;

	// Application Defined Oracle Exception Error Codes (ORA-#####)
	// Note: Application defined error codes in Oracle are always
	// negative numbers between -20000 and -29999 inclusive,
	// but they are returned to Java as positive numbers.
	public final static int ORA_DISTR_LOCK_FAILED = 20100;
	public final static int FACILITY_LOCK_NOT_PHARMACIST = 88888;

	private int _code;

	// flag to indicate if this exception has been logged
	private transient boolean _isLogged = false;

	public final static int EPCS_AUTHORIZATION_EXCEPTION = 900;

	public final static int PROGRAMS_EXCEPTION = 88889;

	public final static int PRECHECK_EXCEPTION = 55555;

	public final static int VERIFYRX_EXCEPTION = 55556;
	public final static int SUPPLIER_ADMIN_EXCEPTION = 55557;
	public final static int CALCULATE_PROMISE_TIME_EXCEPTION = 55558;

	/**
	 * Constructor which accepts an error code
	 */
	public TRException(int errorcode) {
		super();

		_code = errorcode;
	}

	/**
	 * Constructor which accepts an error code and a cause or a nested exception
	 */
	public TRException(int errorcode, Throwable throwable) {
		super(throwable);

		_code = errorcode;
	}

	/**
	 * Constructor which accepts a message. Typically, this message is not intended for
	 * the end user, but rather, for support personnel and developers to assist in
	 * debugging. End user messages are typically accomplished by translating the code
	 * returned by "getCode()" to a human-readable message, in any spoken language.
	 */
	public TRException(int errorcode, String message) {
		super(message);

		_code = errorcode;
	}

	/**
	 * Constructor which accepts an error code, a message for support personnel and a
	 * cause or a nested exception
	 */
	public TRException(int errorcode, String message, Throwable throwable) {
		super(message, throwable);

		_code = errorcode;
	}

	/**
	 * Determine whether an exception code has been assigned to this exception.
	 */
	public boolean isCodeAssigned() {
		return _code != UNDEFINED_EXCEPTION;
	}

	/**
	 * Set the exception code, typically from the list of constants associated with this
	 * class.
	 */
	public void setErrorCode(int code) {
		_code = code;
	}

	/**
	 * Return the exception code associated with this exception.
	 */
	public int getErrorCode() {
		return _code;
	}

	/*
	 * Package level method to indicate that exception has been logged
	 */
	void setLogged() {
		_isLogged = true;
	}

	/**
	 * Indicates if this exception has been logged
	 *
	 * @return true/false
	 */
	public boolean isLogged() {
		return _isLogged;
	}
}
