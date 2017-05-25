package graph;

import java.util.ArrayList;

public class Node {

	private ArrayList<Edge> edges;
	private int value;
	public static boolean directed= false;
	
	public Node(int v) {
		value = v;
		edges = new ArrayList<Edge>();
	}
	
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
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
		if (!directed && !n.hasPathTo(this)) {
			n.addEdge(this);
		}
	}

	public void addEdge(Node n, double weight) {
		edges.add(new Edge(this,n, weight));
		if (!directed && !n.hasPathTo(this)) {
			n.addEdge(this, weight);
		}
	}
	
	public String toString() {
		return String.valueOf(value);
	}
	
	public boolean equals(Object o) {
		return this.getClass().equals(o.getClass()) && ((Node) o).getValue()==this.getValue();
	}
}
