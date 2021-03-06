package my.com.etiqa.swtc.enums;

public enum ErrorCodeEnum {

	UNKNOWN_ERROR("A999"),
	JSON_MODEL_EMPTY("A100"),
	PROPERTY_NOT_FOUND("A101"),
	PRODUCT_CODE_IS_MANDATORY("A102"),
	INVALID_PRODUCT_CODE("A103"),
	LANGUAGE_IS_MANDATORY("A104"),
	AGENT_CODE_IS_MANDATORY("A105"),
	START_DATE_IS_MANDATORY("A106"),
	END_DATE_IS_MANDATORY("A107"),
	INVALID_START_DATE_FORMAT("A108"),
	INVALID_END_DATE_FORMAT("A109"),
	TRIP_TYPE_IS_MANDATORY("A110"),
	INVALID_TRIP_TYPE("A111"),
	TRAVEL_AREA_IS_MANDATORY("A112"),
	INVALID_TRAVEL_AREA("A113"),
	TRAVEL_WITH_CODE_IS_MANDATORY("A114"),
	INVALID_TRAVEL_WITH_CODE("A115"),
	OPERATOR_CODE_IS_MANDATORY("A116"),
	QQ_ID_IS_MANDATORY("A117"),
	PAYMENT_METHOD_IS_MANDATORY("A118"),
	INVALID_PAYMENT_METHOD("A119"),
	REJECTED_DECLARATION_IS_MANDATORY("A120"),
	DISCOUNT_PERCENTAGE_IS_MANDATORY("A121"),
	DISCOUNT_PERCENTAGE_MUST_BE_NUMERIC("A122"),
	GROSS_PREMIUM_IS_MANDATORY("A123"),
	GROSS_PREMIUM_MUST_BE_NUMERIC("A124"),
	DISCOUNT_IS_MANDATORY("A125"),
	DISCOUNT_MUST_BE_NUMERIC("A126"),
	COMMISSION_IS_MANDATORY("A127"),
	COMMISSION_MUST_BE_NUMERIC("A128"),
	GST_IN_MANDATORY("A129"),
	GST_MUST_BE_NUMERIC("A130"),
	TOTAL_PREMIUM_IS_MANDATORY("A131"),
	TOTAL_PREMIUM_MUST_BE_NUMERIC("A132"),
	ID_TYPE_IS_MANDATORY("A133"),
	INVALID_ID_TYPE("A134"),
	ID_NO_IS_MANDATORY("A135"),
	NAME_IS_MANDATORY("A136"),
	EMAIL_IS_MANDATORY("A137"),
	INVALID_EMAIL("A138"),
	RELIGION_IS_MANDATORY("A139"),
	INVALID_RELIGION("A140"),
	NO_OF_CHILD_IS_MANDATORY("A141"),
	ADDRESS1_IS_MANDATORY("A142"),
	ADDRESS2_IS_MANDATORY("A143"),
	POSTCODE_IS_MANDATORY("A144"),
	STATE_IS_MANDATORY("A145"),
	MOBILE_NO_IS_MANDATORY("A146"),
	GENDER_IS_MANDATORY("A147"),
	INVALID_GENDER("A148"),
	DATE_OF_BIRTH_IS_MANDATORY("A149"),
	INVALID_DATE_OF_BIRTH_FORMAT("A150"),
	COUNTRY_IS_MANDATORY("A151"),
	SPOUSE_ID_TYPE_IS_MANDATORY("A152"),
	SPOUSE_ID_NO_IS_MANDATORY("A153"),
	SPOUSE_DATE_OF_BIRTH_IS_MANDATORY("A154"),
	INVALID_SPOUSE_DATE_OF_BIRTH_FORMAT("A155"),
	SPOUSE_GENDER_IS_MANDATORY("A156"),
	INVALID_SPOUSE_GENDER("A157"),
	CHILD_NAME_IS_MANDATORY("A158"),
	CHILD_ID_TYPE_IS_MANDATORY("A159"),
	CHILD_ID_NO_IS_MANDATORY("A160"),
	CHILD_DATE_OF_BIRTH_IS_MANDATORY("A161"),
	INVALID_CHILD_DATE_OF_BIRTH_FORMAT("A162"),
	PDPA_IS_MANDATORY("A163"),
	TRANSACTION_ID_IS_MANDATORY("A164"),
	CORPORATE_NAME_IS_MANDATORY("A165"),
	PAYEE_CODE_IS_MANDATORY("A166"),
	REFERENCE_NO_IS_MANDATORY("A167"),
	ACCOUNT_NO_IS_MANDATORY("A168"),
	TRANSACTION_AMOUNT_IS_MANDATORY("A169"),
	TRANSACTION_AMOUNT_MUST_BE_NUMERIC("A170"),
	APPROVAL_CODE_IS_MANDATORY("A171"),
	BANK_REFERENCE_NO_IS_MANDATORY("A172"),
	TRANSACTION_STATUS_IS_MANDATORY("A173"),
	TRANSACTION_DATE_IS_MANDATORY("A174"),
	INVOICE_NO_IS_MANDATORY("A175"),
	DSP_TRANSACTION_ID_IS_MANDATORY("A176"),
	TRANSACTION_SIGNATURE_IS_MANDATORY("A177"),
	AUTHORIZED_ID_MANDATORY("A178"),
	AUTHORIZED_DATE_IS_MANDATORY("A179"),
	STATUS_CODE_IS_MANDATORY("A180"),
	STATUS_DESCRIPTION_IS_MANDATORY("A181"),
	MERCHANT_TRANSACTION_ID_IS_MANDATORY("A182"),
	CUSTOMER_ID_IS_MANDATORY("A183"),
	BUYER_BANK_BRANCH_IS_MANDATORY("A184"),
	BUYER_BANK_ID_IS_MANDATORY("A185"),
	BUYER_ID_IS_MANDATORY("A186"),
	BUYER_NAME_IS_MANDATORY("A187"),
	CREDIT_AUTHORIZED_CODE_IS_MANDATORY("A188"),
	CREDIT_AUTHORIZED_NO_IS_MANDATORY("A189"),
	DEBIT_AUTHORIZED_CODE_IS_MANDATORY("A190"),
	DEBIT_AUTHORIZED_NO_IS_MANDATORY("A191"),
	FPX_TRANSACTION_ID_IS_MANDATORY("A192"),
	FPX_TRANSACTION_TIME_IS_MANDATORY("A193"),
	MAKER_NAME_IS_MANDATORY("A194"),
	MESSAGE_TYPE_IS_MANDATORY("A195"),
	SELLER_EXCHANGE_ID_IS_MANDATORY("A196"),
	SELLER_EXCHANGE_ORDER_NO_IS_MANDATORY("A197"),
	SELLER_ORDER_NO_IS_MANDATORY("A198"),
	SELLER_ID_IS_MANDATORY("A199"),
	SELLER_TRANSACTION_TIME_IS_MANDATORY("A200"),
	TRANSACTION_CURRENCY_IS_MANDATORY("A201"),
	CHECK_SUM_IS_MANDATORY("A202"),
	PKI_VERIFICATION_IS_MANDATORY("A203"),
	PAYMENT_GATEWAY_RESPONSE_CODE_IS_MANDATORY("A204"),
	SOAP_URL_ERROR("A205"),
	GENERAL_SOAP_ERROR("A206"),
	NULL_POINTER_ERROR("A207"),
	INDEX_OUT_OF_BOUND_ERROR("A208"),
	CPF_REQUEST_TIMEOUT_ERROR("A209"),
	CPF_SERVICE_DOWN("A210"),
	INVALID_DATE_CONVERSION_FORMAT("A211"),
	INVALID_OPERATOR_CODE("A212"),
	INVALID_SPOUSE_ID_TYPE("A213"),
	STRING_OUT_OF_BOUND_ERROR("A214"),
	INVALID_NAME("A215"),
	PLAN_NAME_IS_MANDATORY("A216"),
	INVALID_PLAN_NAME("A217"),
	INVALID_NO_OF_CHILD("A218"),
	INVALID_PDPA_TERM("A219"),
	INVALID_START_DATE("A220"),
	INVALID_END_DATE("A221"),
	DATE_RANGE_CODE_IS_MANDATORY("A222"),
	INVALID_DATE_RANGE_CODE("A223"),
	SPOUSE_NAME_IS_MANDATORY("A224"),
	INVALID_SPOUSE_NAME("A225"),
	INVALID_CHILD_ID_TYPE("A226"),
	INVALID_CHILD_NAME("A227"),
	INVALID_ID_NO("A228"),
	INVALID_MOBILE_NO("A229"),
	INVALID_SPOUSE_ID_NO("A230"),
	INVALID_CHILD_ID_NO("A231"),
	INVALID_QQ_ID("A232"),
	INITIALIZATION_ERROR("A233"),
	SERVICE_LAYER_ERROR("A234"),
	AFTER_DISCOUNT_IS_MANDATORY("A235"),
	AFTER_DISCOUNT_MUST_BE_NUMERIC("A236"),
	COMMISSION_PERCENTAGE_IS_MANDATORY("A237"),
	COMMISSION_PERCENTAGE_MUST_BE_NUMERIC("A238"),
	STAMP_DUTY_IN_MANDATORY("A239"),
	STAMP_DUTY_MUST_BE_NUMERIC("A240");
	
	private String code;
	
	private ErrorCodeEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Override
	public String toString() {
		return code;
	}
	
}
