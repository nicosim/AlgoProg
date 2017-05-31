package data;

public class Stop implements Comparable{
	/*
	stop_id
	stop_code
	stop_name
	stop_desc
	stop_lat
	stop_lon
	location_type
	parent_station
	 */
	private Long stop_id;
	private String stop_name;
	private String stop_desc;
	private Double stop_lat;
	private Double stop_lon;
	private int location_type;
	
	private String stop_code;
	private String parent_station;
	
	public Stop(Long id, String name, String desc, Double lat, Double lon, int loctype) {
		setStop_id(id);

		if (name.startsWith("\"") && name.endsWith("\"") && name.length()>=2) {
			name = name.substring(1, name.length()-1);
		}

		if (desc.startsWith("\"") && desc.endsWith("\"") && desc.length()>=2) {
			desc = desc.substring(1, desc.length()-1);
		}
		stop_name = name;
		stop_desc = desc;
		stop_lat = lat;
		stop_lon = lon;
		location_type = loctype;
	}

	public Long getStop_id() {
		return stop_id;
	}

	public void setStop_id(Long stop_id) {
		this.stop_id = stop_id;
	}

	public String getStop_name() {
		return stop_name;
	}

	public void setStop_name(String stop_name) {
		this.stop_name = stop_name;
	}

	public String getStop_desc() {
		return stop_desc;
	}

	public void setStop_desc(String stop_desc) {
		this.stop_desc = stop_desc;
	}

	public Double getStop_lat() {
		return stop_lat;
	}

	public void setStop_lat(Double stop_lat) {
		this.stop_lat = stop_lat;
	}

	public Double getStop_lon() {
		return stop_lon;
	}

	public void setStop_lon(Double stop_lon) {
		this.stop_lon = stop_lon;
	}

	public int getLocation_type() {
		return location_type;
	}

	public void setLocation_type(int location_type) {
		this.location_type = location_type;
	}

	public String getStop_code() {
		return stop_code;
	}

	public void setStop_code(String stop_code) {
		this.stop_code = stop_code;
	}

	public String getParent_station() {
		return parent_station;
	}

	public void setParent_station(String parent_station) {
		this.parent_station = parent_station;
	}
	
	public boolean equals(Object o) {
		boolean equal = true;
		
		if (!this.getClass().getName().equals(o.getClass().getName())) {
			equal = false;
		}
		else if (!getStop_id().equals(((Stop)o).getStop_id())) {
			equal = false;
		}
		return equal;
	}

	public int compareTo(Object o) throws ClassCastException {
	    if (!(o.getClass().getName().equals(this.getClass().getName()))) {
	      throw new ClassCastException("A Stop object expected.");
	    }
	    int res; 
	    if (this.getStop_id() - ((Stop)o).getStop_id()<0) {
	    	res =-1;
	    }
	    else if (this.getStop_id() - ((Stop)o).getStop_id()>0) {
	    	res = 1;
	    }
	    else {
	    	res = 0;
	    }
	    return res;    
	}
	
	public String toString() {
		String s="";
		s+="stop_name " + this.getStop_name();
		s+=" stop_desc " + this.getStop_desc();
		s+=" stop_lat " + this.getStop_lat();
		s+=" stop_lon " + this.getStop_lon();
		s+=" location_type " + this.getLocation_type();
		return s;
	}
}
