package graph;

import java.util.ArrayList;

import data.Stop;

public class Node {

	private ArrayList<Edge> edges;
	private Stop value;
	private String stringValue;
	
	public Node(Stop stop) {
		value = stop;
		stringValue = stop.getStop_id() + " " + stop.getStop_name();
		edges = new ArrayList<Edge>();
	}
	
	public Stop getValue() {
		return value;
	}
	public void setValue(Stop stop) {
		this.value = stop;
	}
	
	public ArrayList<Edge> getEdges() {
		return edges;
	}
	
	public boolean hasPathTo(Node n) {
		boolean hasPath = false;
		for (int i = 0 ; i< edges.size(); ++i) {
			if (edges.get(i).to().getValue() == n.getValue()) {
				hasPath = true;
				break;
			}
		}
		return hasPath;
	}
	
	public void addEdge(Node n) {
		edges.add(new Edge(this,n));
	}

	public void addEdge(Node n, double weight) {
		edges.add(new Edge(this,n, weight));
	}
	
	public void setStringValue(String val) {
		stringValue = val;
	}
	
	public String toString() {
		return stringValue;
	}
	
	public boolean equals(Object o) {
		return this.getClass().equals(o.getClass()) 
				&& ((Node) o).getValue().getStop_lat().equals(this.getValue().getStop_lat())
				&& ((Node) o).getValue().getStop_lon().equals(this.getValue().getStop_lon());
	}
}
