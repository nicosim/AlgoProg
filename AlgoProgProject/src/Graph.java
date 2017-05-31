import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import data.*;
import graph.*;

public class Graph {
	private Node start;
	private ArrayList<Node> nodes;

	public static void main(String[] args) {
		try {
			//Node.directed = true;
			GTFSData gtfsData = new GTFSData("./ressources");
			Graph g  = new Graph(gtfsData);
			DijsktraSP dfsSp = new DijsktraSP(g);
			//System.out.println("NbStops : " + gtfsData.getStops().size() + " NbNodes : " + g.getNodes().size());
			dfsSp.dfs(g,g.getNodes().get(20));
			System.out.println("dfs : \n" + dfsSp.printSP(106));
			//System.out.println("bfs : \n" + bfs(g).toString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Graph() {
        nodes = new ArrayList<Node>();
        start = null;
	}
	
	public Graph(GTFSData datas) {
		this();
		TreeMap<Long,Long> routeTrip;
		Stop stop = null;
		Node node = null, nodeTo = null;
		Double weight = null;
		
		routeTrip = getBestTripsByRoute(datas);

		for (Long it = routeTrip.firstKey(); it!=null; 
				it = routeTrip.higherKey(it)) 
		{
			stop = null;
			node = null;
			//System.out.println(it + " " + datas.getStoptimesByTrip().get(routeTrip.get(it)));
			for (StopTime stopTime:datas.getStoptimesByTrip().get(routeTrip.get(it))) 
			{
				stop = datas.getStops().get(stopTime.getStop_id());
				if (node!=null) 
				{
					nodeTo = getNodeFromList(new Node(stop), datas);
					weight = getWeight(node, nodeTo);
					addEdge(node, nodeTo, weight);
					node = nodeTo;
				}
				else 
				{
					node = getNodeFromList(new Node(stop), datas);
				}
				nodeTo = null;
				weight = null;
			}
		}
		
		addTransfers(datas);
	}
	
	public TreeMap<Long,Long> getBestTripsByRoute(GTFSData datas){
		TreeMap<Long,Integer> routeSizes = new TreeMap<Long, Integer>();
		TreeMap<Long,Long> routeTrip = new TreeMap<Long, Long>();
		Long routeId;
		int tripSize;
		StopTime stoptime;
		
		for (Long it = datas.getStoptimesByTrip().firstKey(); it!=null; 
				it = datas.getStoptimesByTrip().higherKey(it)){
			if (!datas.getStoptimesByTrip().get(it).isEmpty()) 
			{
				tripSize = datas.getStoptimesByTrip().get(it).size();
				stoptime = datas.getStoptimesByTrip().get(it).get(0);
				routeId = stoptime.getTrip().getRoute().getRoute_id();
				if (routeSizes.containsKey(routeId)) 
				{
					if (routeSizes.get(routeId).compareTo(tripSize)<0) 
					{
						routeSizes.put(routeId,tripSize);
						routeTrip.put(routeId,it);
					}
				}
				else 
				{
					routeSizes.put(routeId,tripSize);
					routeTrip.put(routeId,it);
				}
			}
		}
		/*
		for (Long it = routeSizes.firstKey(); it!=null; 
				it = routeSizes.higherKey(it)) 
		{
			System.out.println("route " + datas.getRoutes().get(it).getRoute_short_name()
					+  " - " + datas.getRoutes().get(it).getRoute_long_name()
					+ " size " + routeSizes.get(it));
		}
		System.out.println(routeTrip);
		System.out.println(routeSizes);
		 */
		return routeTrip;
	}
	
	public void addTransfers(GTFSData datas) {
		Node temp, to;
		for (Node node : nodes) 
		{
			if (datas.getTransfers().containsKey(node.getValue().getStop_id())) 
			{
				temp = null;
				to = null;
				for (Transfer transfer : datas.getTransfers().get(node.getValue().getStop_id()))
				{
					if (datas.getStops().get(transfer.getToId())!= null) 
					{
						temp = new Node(datas.getStops().get(transfer.getToId()));
						if (nodes.contains(temp)) 
						{
							to = nodes.get(nodes.indexOf(temp));
							addEdge(node, to, getWeight(node, to));
							/*
							System.out.println("from " + node.getValue().getStop_name()
									+ " to " + to.getValue().getStop_name());
							 */
						}
					}
				}
			}
		}
	}
	
	/**
	 * Si node présent dans la liste de nodes renvoie le node
	 * Sinon ajoute le node a la liste
	 * @param node
	 * @param datas 
	 * @return node contenu dans la liste
	 */
	public Node getNodeFromList(Node node, GTFSData datas) {
		String val="";
		List<StopTime> stopTimes;
		if (!nodes.contains(node)) 
		{
			val = node.getValue().getStop_name();
			stopTimes = datas.getStoptimesByStop().get(node.getValue().getStop_id());
			if (stopTimes!=null && !stopTimes.isEmpty()) {
				val += " ligne " + stopTimes.get(0).getTrip().getRoute().getRoute_short_name();
			}
			node.setStringValue(val);
			nodes.add(node);
		}
		else {
			node = nodes.get(nodes.indexOf(node));
		}
		return node;
	}
	
	public void addEdge(Node from, Node to) {
		double weight=1;
		addEdge(from,to,weight);
	}
	
	public void addEdge(Node from, Node to, double weight) {
		from.addEdge(to, weight);
	}
	
	public double getWeight(Node from, Node to) {
		double weight=1;
		Double fromLat,fromLon, toLat, toLon;
		fromLat = from.getValue().getStop_lat();
		fromLon = from.getValue().getStop_lon();
		toLat = to.getValue().getStop_lat();
		toLon = to.getValue().getStop_lon();
		if (fromLat !=null && fromLon !=null && toLat !=null && toLon !=null) {
			weight = Math.sqrt(Math.pow(toLat-fromLat, 2)+Math.pow(toLon-fromLon, 2));
		}
		return weight;
	}

	public ArrayList<Node> getNodes() {
		return nodes;
	}
}