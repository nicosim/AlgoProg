package algorithms;
import java.util.ArrayList;

import graph.*;
import main.Graph;

public class DijsktraSP {
	private Graph g;
	private int start;	
	private boolean[] marked;
	private int[] previous;
	private double[] distance;
	private String[] value;
	
	public DijsktraSP(Graph g){
		this.g= g;
		marked = new boolean[g.getNodes().size()];
		previous = new int[g.getNodes().size()];
		distance = new double[g.getNodes().size()];
		value = new String[g.getNodes().size()];
		init();
	}
	
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}
	
	public void setGraph(Graph g) {
		this.g=g;
	}
	
	public void init() {
		start = 0;
		for (int i=0; i < g.getNodes().size(); ++i) {
			marked[i] = false;
			previous[i] = -1;
			distance[i] = Integer.MAX_VALUE;
			value[i] = g.getNodes().get(i).toString();
		}
	}

	public void dfs(Graph g, Node node) {
		setGraph(g);
		setStart(g.getNodes().indexOf(node));
		marked[getStart()] = true;
		distance[getStart()] = 0;
		previous[getStart()] = -1;
		dfs(node);
		//System.out.println(toString());
	}
		
	public void dfs(Node node){
		ArrayList<Edge> edges= node.getEdges();
		int pos, posTo;
		double distanceEdge;
		pos = g.getNodes().indexOf(node);
		for (int i = 0; i < edges.size(); ++i) {
			posTo =g.getNodes().indexOf(edges.get(i).to());
			if (distance[pos]==-1) {
				distanceEdge = edges.get(i).weight();
			}
			else {
				distanceEdge = distance[pos] + edges.get(i).weight();
			}
			if (distance[posTo]> distanceEdge) {
				previous[posTo] = pos;
				distance[posTo] = distanceEdge;
				//System.out.println("change for " + (posEdge+1) + " new distance " + distanceEdge);
			}
			if (!marked[posTo]) {
				marked [posTo] = true;
				dfs(edges.get(i).to());
			}
		}
	}

	public boolean hasPathTo(int v) {
		dfs(g, g.getNodes().get(start));
		return marked[v];
	}
	
	public double distTo(int v) {
		dfs(g, g.getNodes().get(start));
		return distance[v];
	}
	
	public String printSP(int v) {
		String s="";
		s+="[" + value[v] + "]";
		int pos = v, cpt=1, modulo=5;
		//System.out.println(start + " " + pos);
		if (pos !=start) {
			pos = previous[pos];
			s = "[" + value[pos] + "]\t=>\t" + s;
			cpt++;
			//System.out.println(start + " " + pos);
			while (pos !=start) {
				pos = previous[pos];
				s = "[" + value[pos] + "]\t" + s;
				cpt++;
				if (pos!=start) {
					if (cpt%modulo==0) {
						s="\n=>\t" + s;
					}
					else {
						s = "=>\t" + s;
					}
				}
				//System.out.println(s);
			}
		}
		s = "Shortest Path with a weight of " + distance[v] + " and a number of "+ cpt + " stations\n" + s;
		return s;
	}
	
	public boolean verifyNonNegative(Graph g) {
		for (int i = 0; i < g.getNodes().size(); ++i) {
			for (int j=0; j < g.getNodes().get(i).getEdges().size(); ++j) {
				if (g.getNodes().get(i).getEdges().get(j).weight()<0) {
					return false;
				}
			}
		}
		return true;
	}
	
	public int[] getPrevious() {
		return previous;
	}
	
	public String toString() {
		String s = "", sPos="", sValue="", sMarked="", sPrevious = "", sDistance = "";
		int modulo = 3, rightPad = 75;
		for (int i = 0; i< marked.length; ++i) {
			sPos += i;
			sValue+= value[i];
			sMarked+=marked[i];
			if (distance[i] ==Integer.MAX_VALUE) {
				sPrevious+="X";
				sDistance+="X";
			}
			else {
				sPrevious+=previous[i];
				sDistance+=distance[i];
				
			}
			if (i<marked.length) {
				sPos= String.format("%-" + (((i%modulo)+1)*rightPad) + "s ", sPos);
				sValue= String.format("%-" + (((i%modulo)+1)*rightPad) + "s ", sValue);
				sMarked= String.format("%-" + (((i%modulo)+1)*rightPad) + "s ", sMarked);
				sPrevious= String.format("%-" + (((i%modulo)+1)*rightPad) + "s ", sPrevious);
				sDistance= String.format("%-" + (((i%modulo)+1)*rightPad) + "s ", sDistance);
			}
			if (i%modulo==modulo-1) {
				s+= "POS      [" + sPos + "]\n";
				s+= "VALUES   [" + sValue + "]\n";
				s+= "MARKED   [" + sMarked + "]\n";
				s+= "PREVIOUS [" + sPrevious + "]\n";
				s+= "DISTANCE [" + sDistance + "]\n";
				s+="\n\n";
				
				sPos= "";
				sValue= "";
				sMarked="";
				sPrevious=" ";
				sDistance="";
			}
			else if (i<marked.length-1) {
				sPos+= ", ";
				sValue+= ", ";
				sMarked+=", ";
				sPrevious+=", ";
				sDistance+=", ";
			}
		}
		if ((marked.length-1)%modulo!=0) {
			s+= "POS      [" + sPos + "]\n";
			s+= "VALUES   [" + sValue + "]\n";
			s+= "MARKED   [" + sMarked + "]\n";
			s+= "PREVIOUS [" + sPrevious + "]\n";
			s+= "DISTANCE [" + sDistance + "]\n";
		}
		
		return s;
	}
}
