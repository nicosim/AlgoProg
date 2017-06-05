package graph;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import main.Graph;

public class JGraph extends JFrame {
	/** Pour eviter un warning venant du JFrame */
	private static final long serialVersionUID = -8123406571694511514L;
	public mxGraph mxgraph;
	public Graph graph;
	
	public JGraph() {
		super("JGrapghX");
		mxgraph = new mxGraph();
		mxGraphComponent graphComponent = new mxGraphComponent(mxgraph);
		getContentPane().add(graphComponent); 
	}
	
	public JGraph(Graph g) {
		this();
		graph = g;
		showGraph();
		
	}
	
	public void showGraph(){
		Object parent = mxgraph.getDefaultParent();
		Object[] listV;
		mxgraph.getModel().beginUpdate();
		mxgraph.setAutoSizeCells(true);
		mxgraph.setCellsResizable(true);
		Double lat, lon;
		String latS, lonS;
		double resize=Math.pow(10, 11);
		Edge edge;
		try
		{
			listV = new Object[graph.getNodes().size()];
			for (int i = 0; i < graph.getNodes().size(); i++){
				lat = graph.getNodes().get(i).getValue().getStop_lat();
				lon = graph.getNodes().get(i).getValue().getStop_lon();
				latS = lat.toString();
				lonS = lon.toString();
				latS = latS.substring(latS.indexOf(".")+1,latS.length());
				lonS = lonS.substring(lonS.indexOf(".")+1,lonS.length());
				lat = Double.parseDouble(latS)/resize;
				lon = Double.parseDouble(lonS)/resize;
				listV[i]= mxgraph.insertVertex(parent, null, graph.getNodes().get(i).toString(), 
						lon, lat, graph.getNodes().get(i).toString().length()*5.5,20);
			}
			
			for (int i = 0; i < graph.getNodes().size(); i++){
				for (int j=0;j<graph.getNodes().get(i).getEdges().size();j++){
					edge = graph.getNodes().get(i).getEdges().get(j);
					mxgraph.insertEdge(parent, null, "", listV[i],
							listV[graph.getNodes().indexOf(edge.to())]);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			mxgraph.getModel().endUpdate();
		}
	}
}