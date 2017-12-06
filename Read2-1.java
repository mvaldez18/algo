
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.princeton.cs.algs4.*;

/**
 * Simple Java program to read CSV file in Java. 
 * We changed it so that it reads data in our format.
 * 
 * @author WINDOWS 8
 *
 */
public class Read2 {

	public Read2() {
	}

	public OurGraph run(String filename, String closed) {

		// name of the port to be closed -currently does not work
		OurGraph graph = readRowsFromCSV(filename, closed);
		return graph;

		/**
		 * Code that prints the fastest route found
		 * 
		 * 
		 * for (Edge edge : path) { StdOut.println(edge.toString());
		 * StdOut.println(graph.getEdges().get("" + edge.either() + "+" +
		 * edge.other(edge.either())).getPhi()); StdOut.println(graph.getEdges().get(""
		 * + edge.either() + "+" + edge.other(edge.either())).getTheta());
		 * StdOut.println(graph.getName(edge.either())); }
		 */

	}
	

	private static OurGraph readRowsFromCSV(String fileName, String closed) {
		Path pathToFile = Paths.get(fileName);
		EdgeWeightedGraph graph = new EdgeWeightedGraph(4470);
		ST<String, WaterWay> edges = new ST<String, WaterWay>();
		ArrayList<Integer> vertList = new ArrayList<Integer>();
		ST<String, Integer> nameTable = new ST<String, Integer>();

		// create an instance of BufferedReader
		// using try with resource, Java 7 feature to close resources
		try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {

			// read the first line from the text file
			String line = br.readLine();
			line = br.readLine();
			// loop until all lines are read
			while (line != null) {

				ArrayList<Double> coordx = new ArrayList<Double>();
				ArrayList<Double> coordy = new ArrayList<Double>();

				//splits up row by commas into String array 
				String[] metadata = line.split(",");
				
				//V1 is the FROM vertex, V2 is the TO vertex
				int V1 = Integer.parseInt(metadata[0]);
				int V2 = Integer.parseInt(metadata[1]);
				
				//currently unused data
				int freq = Integer.parseInt(metadata[2]);
				
				//name of FROM vertex
				String name = metadata[3];
				
				//length AKA weight
				double length = Double.parseDouble(metadata[4]);
				
				//Currently unused data
				String geometry_type = metadata[5];
				
				//ID of the edge
				String id = metadata[6];
				
				//Coordinates of FROM vertex
				coordx.add(Double.parseDouble(metadata[7]));
				coordy.add(Double.parseDouble(metadata[8]));

				//stores FROM vertex in an ArrayList (re-indexing)
				if (!vertList.contains(V1)) {
					vertList.add(V1);
				}
				
				//puts name of FROM vertex into symbol table at new index
				nameTable.put(name, vertList.indexOf(V1));
				
				//stores TO vertex in ArrayList (re-indexing)
				if (!vertList.contains(V2)) {
					vertList.add(V2);
				}
				
				//supposed to render edge of closed node too long for use (AKA closed)
				if (name.equals(closed)) {
					length = 2000000;
				}
				
				Edge edge = new Edge(vertList.indexOf(V1), vertList.indexOf(V2), length);
				graph.addEdge(edge);

				line = br.readLine();
				while (line != null && line.split(",")[0].equals("NA")) {
					metadata = line.split(",");
					coordx.add(Double.parseDouble(metadata[7]));
					coordy.add(Double.parseDouble(metadata[8]));
					line = br.readLine();
				}

				edges.put(
						(Math.min(vertList.indexOf(V1), vertList.indexOf(V2)) + "+"
								+ (Math.max(vertList.indexOf(V1), vertList.indexOf(V2)))),
						new WaterWay(vertList.indexOf(V1), vertList.indexOf(V2), coordx, coordy, length, id));
			}

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return new OurGraph(graph, edges, vertList, nameTable);
	}

}
