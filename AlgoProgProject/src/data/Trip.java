package data;

public class Trip {
	/*
	route_id
	service_id
	trip_id
	trip_headsign
	trip_short_name
	direction_id
	shape_id
	 */
	private Long trip_id;
	private Long trip_headsign;
	private Long trip_short_name;
	private Long direction_id;
	private Long service_id;
	private Route route;
	
	private String shape_id;
	
	public Trip(Long id, Long heads, Long  shortName, Long dir, long serv_id, Route r) {
		trip_id = id;
		trip_headsign = heads;
		trip_short_name = shortName;
		direction_id = dir;
		service_id = serv_id;
		route = r;
		
	}

	public Long getTrip_id() {
		return trip_id;
	}

	public void setTrip_id(Long trip_id) {
		this.trip_id = trip_id;
	}

	public Long getTrip_headsign() {
		return trip_headsign;
	}

	public void setTrip_headsign(Long trip_headsign) {
		this.trip_headsign = trip_headsign;
	}

	public Long getTrip_short_name() {
		return trip_short_name;
	}

	public void setTrip_short_name(Long trip_short_name) {
		this.trip_short_name = trip_short_name;
	}

	public Long getDirection_id() {
		return direction_id;
	}

	public void setDirection_id(Long direction_id) {
		this.direction_id = direction_id;
	}

	public Long getService_id() {
		return service_id;
	}

	public void setService_id(Long service_id) {
		this.service_id = service_id;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public String getShape_id() {
		return shape_id;
	}

	public void setShape_id(String shape_id) {
		this.shape_id = shape_id;
	}
}
