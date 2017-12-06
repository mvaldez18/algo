import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.princeton.cs.algs4.DijkstraUndirectedSP;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.ST;

class Read2Test {

	OurGraph graph;

	@BeforeEach
	void setUp() throws Exception {
		Read2 read = new Read2();
		graph = read.run("text.csv", "");
	}

	@Test
	void dataTest() {
		assertTrue(graph.getNameList().contains("OAKLAND"));
		assertTrue(graph.getNameList().contains("PUERTO R"));
		assertTrue(graph.getNameList().contains("OKINAWA"));
		
		DijkstraUndirectedSP shortestPath = new DijkstraUndirectedSP(graph.getGraph(), graph.findName("PUGET SO"));
		assertTrue(shortestPath.hasPathTo(graph.getNameList().get("PUERTO R")));

		// work in progress on verifying number of edges and vertices from data
		// assertEquals(graph.getVertList().size(),5631);
		// assertEquals(graph.getEdges().size(),8377);

		// correct number of names from data!
		assertEquals(727, graph.getNameList().size());
	}

	@Test
	void graphTest() {
		//tests functionality of OurGraph data type
		EdgeWeightedGraph uGraph = new EdgeWeightedGraph(4);
		Edge[] edge = new Edge[4];
		ST<String, WaterWay> edges = new ST<String, WaterWay>();
		ArrayList<Integer> vertList = new ArrayList<Integer>();
		ST<String, Integer> nameTable = new ST<String, Integer>();
		ArrayList<Double> coorx = new ArrayList<Double>();
		ArrayList<Double> coory = new ArrayList<Double>();
		OurGraph graph = new OurGraph(uGraph, edges, vertList, nameTable);

		edge[0] = new Edge(0, 1, 1);
		graph.getNameList().put("SOUTHWEST", 0);
		coorx.add(0.0);
		coory.add(0.0);
		graph.getEdges().put("0+1", new WaterWay(0, 1, coorx, coory, 1.0, "edge1"));
		vertList.add(0);

		edge[1] = new Edge(1, 2, 1);
		graph.getNameList().put("SOUTHEAST", 1);
		coorx.clear();
		coory.clear();
		coorx.add(1.0);
		coory.add(0.0);
		graph.getEdges().put("1+2", new WaterWay(1, 2, coorx, coory, 1.0, "edge2"));
		vertList.add(1);

		edge[2] = new Edge(2, 3, 1);
		graph.getNameList().put("NORTHEAST", 2);
		coorx.clear();
		coory.clear();
		coorx.add(1.0);
		coory.add(1.0);
		graph.getEdges().put("2+3", new WaterWay(2, 3, coorx, coory, 1.0, "edge3"));
		vertList.add(2);

		edge[3] = new Edge(0, 3, 10);
		graph.getNameList().put("NORTHWEST", 3);
		coorx.clear();
		coory.clear();
		coorx.add(0.0);
		coory.add(1.0);
		graph.getEdges().put("0+3", new WaterWay(3, 0, coorx, coory, 10.0, "edge4"));
		vertList.add(3);
		
		for (int i = 0; i < edge.length; i++) {
			graph.getGraph().addEdge(edge[i]);
		}
		
		assertEquals(graph.getGraph().V(), 4);
		assertEquals(graph.findName("SOUTHWEST"),0);
		assertEquals(graph.findName("NORTHEAST"),2);
		assertEquals(graph.getEdges().get("0+3").getLength(),10);
		assertEquals((double) graph.getEdges().get(vertList.get(0)+"+"+vertList.get(1)).getTheta().get(0),.0);
	}

}
