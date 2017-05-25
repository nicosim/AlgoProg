package data;

public class Transfer {
	/*
	from_stop_id
	to_stop_id
	transfer_type
	min_transfer_time
	 */
	private Long from_id;
	private Long to_id;
	private int transfer_type;
	private Long min_transfer_time;
	
	public Transfer (Long f, Long t, int trans, Long min) {
		from_id = f;
		to_id = t;
		transfer_type = trans;
		min_transfer_time = min;
	}
	
	public Long getFromId() {
		return from_id;
	}
	
	public Long getToId() {
		return to_id;
	}
	
	public int getTransferType() {
		return transfer_type;
	}
	
	
	public Long getMinTime() {
		return min_transfer_time;
	}
	
	public boolean equals(Object o) {
		boolean equal = true;
		Transfer trans = null;
		
		if (!this.getClass().getName().equals(o.getClass().getName())) {
			equal = false;
		}
		else {
			trans = ((Transfer)o);
			equal = getFromId().equals(trans.getFromId()) 
					&& getToId().equals(trans.getToId())
					&& getTransferType()==trans.getTransferType()
					&& getMinTime().equals(trans.getMinTime());
		}
		return equal;
	}
	
	public String toString() {
		String s="";
		s+="from : " + from_id;
		s+=" to : "+ to_id;
		s+=" transfer_type : "+ transfer_type;
		s+=" min_transfer_time : "+ min_transfer_time;
		return s;
	}
}
