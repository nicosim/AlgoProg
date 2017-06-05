package algorithms;
import java.util.TreeMap;
import java.util.concurrent.SynchronousQueue;

import graph.*;
import main.Graph;

public class ClusterAlgorithm {
	private Graph graph;
	private int numEdgeRemoved=0;
	private TreeMap<Edge, Integer> edgeBetweeness;
	
	public  ClusterAlgorithm(Graph g) {
		graph = g;
		edgeBetweeness= new TreeMap<Edge, Integer>();
	}
	
	public void init() {
		DijsktraSP dijSP = new DijsktraSP(graph);
		int[] previous;
		int fromIndex;
		Node from, to;
		edgeBetweeness.clear();
		for (Node node : graph.getNodes()) {
			dijSP.init();
			dijSP.dfs(graph, node);
			previous = dijSP.getPrevious();
			for (int i=0; i < previous.length; ++i) {
				fromIndex = previous[i];
				if (fromIndex!=-1) {
					to = graph.getNodes().get(i);
					from = graph.getNodes().get(fromIndex);
					for (Edge edge: from.getEdges()) {
						if (edge.to().equals(to)) {
							if (!edgeBetweeness.containsKey(edge)) {
								edgeBetweeness.put(edge, 1);
							}
							else {
								edgeBetweeness.put(edge, edgeBetweeness.get(edge)+1);
							}
						}
					}
				}
			}
		}
	}
	
	public void run() {
		int value;
		Edge min=null, max= null;
		numEdgeRemoved=0;
		System.out.println(edgeBetweeness);
		System.out.println(edgeBetweeness.size());
		do {
			min = edgeBetweeness.firstKey();
			max = min;
			for (Edge edge = edgeBetweeness.higherKey(min); edge!=null; 
					edge = edgeBetweeness.higherKey(edge)){
				value = edgeBetweeness.get(edge);
				if (value<edgeBetweeness.get(min)) {
					min = edge;
				}
				else if (value>edgeBetweeness.get(max)) {
					max = edge;
				}
			}
			if (min.weight()!=max.weight()) {
				removeEdge(max);
				numEdgeRemoved++;
			}
		} while(min.weight()!=max.weight());
		System.out.println(edgeBetweeness);
		System.out.println(edgeBetweeness.size());
	}
	
	public void removeEdge(Edge edge) {
		edge.from().getEdges().remove(edge);
		edge.to().getEdges().remove(edge);
		edgeBetweeness.remove(edge);
		edge = null;
		
	}
	
	
}
