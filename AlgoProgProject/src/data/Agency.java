package data;

public class Agency {
	/* 
	agency_id
	agency_name
	agency_url
	agency_timezone
	agency_lang
	agency_phone
	 */
	private Long agency_id;
	private String agency_name;
	private String agency_url;
	private String agency_timezone;
	
	private String agency_lang;
	private String agency_phone;
	
	public Agency(Long id, String name, String url, String timezone) {
		agency_id = id;
		agency_name = name;
		agency_url = url;
		agency_timezone = timezone;
	}
}
