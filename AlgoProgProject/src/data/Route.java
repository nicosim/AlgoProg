package data;

public class Route {
	/*
	route_id
	agency_id
	route_short_name
	route_long_name
	route_desc
	route_type
	route_url
	route_color
	route_text_color
	 */
	private Long route_id;
	private String route_short_name;
	private String route_long_name;
	private Agency agency;
	private String route_desc;
	private int route_type;
	
	private String route_url;
	private String route_color;
	private String route_text_color;
	
	public Route(Long id, String sn, String ln, int type, Agency a) {
		route_id = id;
		if (sn.startsWith("\"") && sn.endsWith("\"") && sn.length()>=2) {
			sn = sn.substring(1, sn.length()-1);
		}
		if (ln.startsWith("\"") && ln.endsWith("\"") && ln.length()>=2) {
			ln = ln.substring(1, ln.length()-1);
		}
		route_short_name = sn;
		route_long_name = ln;
		route_type = type;
		agency = a;
	}

	public Long getRoute_id() {
		return route_id;
	}

	public void setRoute_id(Long route_id) {
		this.route_id = route_id;
	}

	public String getRoute_short_name() {
		return route_short_name;
	}

	public void setRoute_short_name(String route_short_name) {
		this.route_short_name = route_short_name;
	}

	public String getRoute_long_name() {
		return route_long_name;
	}

	public void setRoute_long_name(String route_long_name) {
		this.route_long_name = route_long_name;
	}

	public Agency getAgency() {
		return agency;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	public String getRoute_desc() {
		return route_desc;
	}

	public void setRoute_desc(String route_desc) {
		this.route_desc = route_desc;
	}

	public int getRoute_type() {
		return route_type;
	}

	public void setRoute_type(int route_type) {
		this.route_type = route_type;
	}

	public String getRoute_url() {
		return route_url;
	}

	public void setRoute_url(String route_url) {
		this.route_url = route_url;
	}

	public String getRoute_color() {
		return route_color;
	}

	public void setRoute_color(String route_color) {
		this.route_color = route_color;
	}

	public String getRoute_text_color() {
		return route_text_color;
	}

	public void setRoute_text_color(String route_text_color) {
		this.route_text_color = route_text_color;
	}
}
