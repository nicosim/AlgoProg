package graph;
import java.awt.Font;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

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
		
	}
	
	public void showGraph(){
		Object parent = mxgraph.getDefaultParent();
		Object[] listV;
		Double lat, lon, maxLat=99000d, minLon = 4000d;
		String latS, lonS;
		double resize=5;
		Edge edge;
		mxgraph.getModel().beginUpdate();
		mxgraph.setAutoSizeCells(true);
		mxgraph.setCellsResizable(true);
		setFontSize("10");
		System.out.println("show graph");
		try
		{
			listV = new Object[graph.getNodes().size()];
			for (int i = 0; i < graph.getNodes().size(); i++){
				lat = graph.getNodes().get(i).getValue().getStop_lat();
				lon = graph.getNodes().get(i).getValue().getStop_lon();
				latS = lat.toString();
				lonS = lon.toString();
				latS = latS.substring(0,latS.indexOf(".")-1) + latS.substring(latS.indexOf(".")+1,latS.indexOf(".")+6);
				lonS = lonS.substring(0,lonS.indexOf(".")-1) + lonS.substring(lonS.indexOf(".")+1,lonS.indexOf(".")+6);
				lat = maxLat - Double.parseDouble(latS)/resize;
				lon = Double.parseDouble(lonS)/resize - minLon;
				System.out.println(lat + " " + lon);
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

	public void showIconGraph(){
		Object parent = mxgraph.getDefaultParent();
		Object[] listV;
		Double lat, lon, maxLat=24800d, minLon = 1000d;
		String latS, lonS;
		double resize=20;
		Edge edge;
		mxgraph.getModel().beginUpdate();
		mxgraph.setAutoSizeCells(true);
		mxgraph.setCellsResizable(true);
		setFontSize("5");
		System.out.println("show icon graph");
		try
		{
			listV = new Object[graph.getNodes().size()];
			for (int i = 0; i < graph.getNodes().size(); i++){
				lat = graph.getNodes().get(i).getValue().getStop_lat();
				lon = graph.getNodes().get(i).getValue().getStop_lon();
				latS = lat.toString();
				lonS = lon.toString();
				latS = latS.substring(0,latS.indexOf(".")-1) + latS.substring(latS.indexOf(".")+1,latS.indexOf(".")+6);
				lonS = lonS.substring(0,lonS.indexOf(".")-1) + lonS.substring(lonS.indexOf(".")+1,lonS.indexOf(".")+6);
				lat = maxLat-Double.parseDouble(latS)/resize;
				lon = Double.parseDouble(lonS)/resize - minLon;
				System.out.println(lat + " " + lon);
				listV[i]= mxgraph.insertVertex(parent, null, graph.getNodes().get(i).toString(), 
						lon, lat, graph.getNodes().get(i).toString().length()*2.5,7);
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
	
	private void setFontSize(String size) {
		Map<String, Object> vstyle = mxgraph.getStylesheet().getDefaultVertexStyle();
		vstyle.put(mxConstants.STYLE_FONTSIZE, size);
		Map<String, Object> estyle = mxgraph.getStylesheet().getDefaultEdgeStyle();
		estyle.put(mxConstants.STYLE_FONTSIZE, size);

		
		mxStylesheet stylesheet = new mxStylesheet();
		stylesheet.setDefaultVertexStyle(vstyle);
		stylesheet.setDefaultEdgeStyle(estyle);
		mxgraph.setStylesheet(stylesheet);
	}
}