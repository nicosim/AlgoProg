package data;

import java.util.Date;

public class StopTime {
	/*
	trip_id
	arrival_time
	departure_time
	stop_id
	stop_sequence
	stop_headsign
	shape_dist_traveled
	 */
	private Trip trip;
	private String arrival_time;
	private String departure_time;
	private Long stop_sequence;
	private Long stop_id;
	
	private String stop_headsign;
	private String shape_dist_traveled;
	
	public StopTime(Trip t, String arr, String dep, long seq, Long s) {
		trip = t;
		arrival_time = arr;
		departure_time = dep;
		stop_sequence = seq;
		stop_id = s;
	}
	
	public Trip getTrip() {
		return trip;
	}
	public void setTrip(Trip trip) {
		this.trip = trip;
	}
	public String getArrival_time() {
		return arrival_time;
	}
	public void setArrival_time(String arrival_time) {
		this.arrival_time = arrival_time;
	}
	public String getDeparture_time() {
		return departure_time;
	}
	public void setDeparture_time(String departure_time) {
		this.departure_time = departure_time;
	}
	public Long getStop_sequence() {
		return stop_sequence;
	}
	public void setStop_sequence(Long stop_sequence) {
		this.stop_sequence = stop_sequence;
	}
	public Long getStop_id() {
		return stop_id;
	}
	public void setStop_id(Long stop) {
		this.stop_id = stop;
	}
	public String getStop_headsign() {
		return stop_headsign;
	}
	public void setStop_headsign(String stop_headsign) {
		this.stop_headsign = stop_headsign;
	}
	public String getShape_dist_traveled() {
		return shape_dist_traveled;
	}
	public void setShape_dist_traveled(String shape_dist_traveled) {
		this.shape_dist_traveled = shape_dist_traveled;
	}
	
	public String toString() {
		String s="";
		s+= "trip_id : " + this.getTrip().getTrip_id();
		s+= " stop_id : " + this.getStop_id();
		s+= " arrival_time : " + this.getArrival_time();
		s+= " departure_time : " + this.getDeparture_time();
		s+= " stop_sequence : " + this.getStop_sequence();
		return s;
	}
}
