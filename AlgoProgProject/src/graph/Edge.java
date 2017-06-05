package graph;

public class Edge implements Comparable<Edge>{

	private Node from;
	private Node to;
	private double weight;
	
	public Edge(Node from, Node to) {
		this(from,to,1);
	}
	
	public Edge(Node from, Node to, double weight) {
		this.from = from;
		this.to= to;
		this.weight = weight;
	}
	
	public Node from() {
		return from;
	}
	
	public Node to() {
		return to;
	}
	
	public double weight() {
		return weight;
	}
	
	public String toString() {
		return from.toString() + " - " + to.toString() + " : " + weight; 
	}
	
	public boolean equals(Object o) {
		if (!getClass().equals(o.getClass())) {
			return false;
		}
		Edge e = (Edge) o;
		if (!from.equals(e.from())) {
			return false;
		}
		if (!to.equals(e.to())) {
			return false;
		}
		if (weight!= e.weight()) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(Edge edge) {
		int comp=0; 
		if (weight()-edge.weight()<0) {
			comp=-1;
		}
		else if (weight()-edge.weight()>0) {
			comp=1;
		}
		return comp;
	}
}

