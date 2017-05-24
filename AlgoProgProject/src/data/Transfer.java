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
}
