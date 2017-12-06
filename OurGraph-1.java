import java.util.ArrayList;
import java.util.Iterator;

import edu.princeton.cs.algs4.*;

public class OurGraph {

	private EdgeWeightedGraph graph;

	private ST<String, WaterWay> edges;

	private ArrayList<Integer> vertList;
	
	private ST<String, Integer> nameList;

	public OurGraph(EdgeWeightedGraph graph, ST<String, WaterWay> edges, ArrayList<Integer> vertList, ST<String, Integer> nameList) {
		this.graph = graph;
		this.edges = edges;
		this.vertList = vertList;
		this.nameList=nameList;
	}

	public ST<String, WaterWay> getEdges() {
		return edges;
	}

	public EdgeWeightedGraph getGraph() {
		return graph;
	}

	public ArrayList<Integer> getVertList() {
		return vertList;
	}
	
	public ST<String, Integer> getNameList(){
		return nameList;
	}
	
	public int findName(String name){
		if(nameList.contains(name)){
			return nameList.get(name);
		}
		else{
			StdOut.print("Incorrect Port Entry");
			return 0;
		}
	}
	
	public String getName(int index){
		Iterable<String> keys = nameList.keys();
		for (String key : keys) {
			if (nameList.get(key).equals(index)){
				return key;
			}
		}
		return null;
	}

}
