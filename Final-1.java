import edu.princeton.cs.algs4.DijkstraUndirectedSP;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.ST;

public class Final {

	//Use WASD to move camera and up and down for zoom
	//use left and right for spin
	public static void main(String[] args) {
		Read2 read = new Read2();
		OurGraph graph = read.run("text.csv", "+");
		String closed = "";

		// choose port as start and finish
		DijkstraUndirectedSP shortestPath = new DijkstraUndirectedSP(graph.getGraph(), graph.findName("OAKLAND"));
		Iterable<Edge> path = shortestPath.pathTo(graph.findName("Suez Cnl"));

		// array that keeps track of camera position, zoom, and world angle
		double[] controls = new double[4];
		// array that holds mouse x and y location
		double[] mouseXY = new double[2];
		StdDraw.enableDoubleBuffering();
		StdDraw.setCanvasSize(1200, 600);
		StdDraw.setXscale(-2, 2);
		StdDraw.setYscale(-1, 1);
		while (true) {
			// stores key stroke input into array
			controls = handleKeyPresses(controls);
			// stores mouse data
			mouseXY = handleMouse();
			// code that allows for moving around the screen
			StdDraw.setXscale(-2 + controls[1] * 2 + controls[2], 2 - controls[1] * 2 + controls[2]);
			StdDraw.setYscale(-1 + controls[1] + controls[3], 1 - controls[1] + controls[3]);
			// draws shortest path
			drawPath(path, graph, controls[0], controls[1]);
			// draws all vertices across the globe
			drawWorld(graph, controls[0], controls[1], mouseXY);
		}

	}

	// returns mouse coordinates
	private static double[] handleMouse() {
		double[] mouseXY = { StdDraw.mouseX(), StdDraw.mouseY() };
		return mouseXY;
	}

	// returns key data
	private static double[] handleKeyPresses(double[] controls) {
		if (StdDraw.isKeyPressed(java.awt.event.KeyEvent.VK_LEFT)) {
			controls[0] += -0.9;
		}
		if (StdDraw.isKeyPressed(java.awt.event.KeyEvent.VK_RIGHT)) {
			controls[0] += 0.9;
		}
		if (StdDraw.isKeyPressed(java.awt.event.KeyEvent.VK_UP)) {
			controls[1] += 0.01;
		}
		if (StdDraw.isKeyPressed(java.awt.event.KeyEvent.VK_DOWN)) {
			controls[1] += -0.01;
		}
		if (StdDraw.isKeyPressed(java.awt.event.KeyEvent.VK_W)) {
			controls[3] += 0.01;
		}
		if (StdDraw.isKeyPressed(java.awt.event.KeyEvent.VK_A)) {
			controls[2] += -0.01;
		}
		if (StdDraw.isKeyPressed(java.awt.event.KeyEvent.VK_S)) {
			controls[3] += -0.01;
		}
		if (StdDraw.isKeyPressed(java.awt.event.KeyEvent.VK_D)) {
			controls[2] += 0.01;
		}

		return controls;
	}

	private static void drawPath(Iterable<Edge> path, OurGraph graph, double offset, double zoom) {
		StdDraw.setPenColor(StdDraw.RED);
		ST<String, WaterWay> edges = graph.getEdges();
		for (Edge edge : path) {
			// find the smaller vertex as the key for the table uses the smaller vertex first
			int V1 = Math.min(edge.either(), edge.other(edge.either()));
			int V2 = Math.max(edge.either(), edge.other(edge.either()));
			int numPoints = edges.get(V1 + "+" + V2).getTheta().size();
			double phi, theta;

			// arrays for cartesian coordinates of vertices
			// some edges contain inflection points hence the arrays
			double[] coorx = new double[numPoints];
			double[] coory = new double[numPoints];
			double[] coorz = new double[numPoints];
			for (int i = 0; i < numPoints; i++) {
				// polar coordinate phi
				phi = edges.get("" + V1 + "+" + V2).getPhi().get(i) + 90;
				// polar coordinate theta
				theta = edges.get("" + V1 + "+" + V2).getTheta().get(i) + 180 + offset;
				// translation to the cartesian plane
				coory[i] = Math.sin((phi / 180) * Math.PI) * Math.sin((theta / 180) * Math.PI);
				coorx[i] = -Math.sin((phi / 180) * Math.PI) * Math.cos((theta / 180) * Math.PI);
				coorz[i] = -Math.cos((phi / 180) * Math.PI);
				
				//if statement prevents objects behind the globe from being displayed
				if (coory[i] > 0) {
					StdDraw.filledSquare(coorx[i], coorz[i], .005 * Math.sqrt(coory[i]));
					if (i > 0) {
						StdDraw.line(coorx[i], coorz[i], coorx[i - 1], coorz[i - 1]);
					}
					if (graph.getName(edge.either()) != null) {
						StdDraw.setPenColor(StdDraw.WHITE);
						StdDraw.text(coorx[i], coorz[i], graph.getName(edge.either()));
						StdDraw.setPenColor(StdDraw.RED);
					}
				}
			}
		}
		StdDraw.show();
		StdDraw.clear(StdDraw.BLACK);
	}

	
	//draws all points. see drawPath for logic
	private static void drawWorld(OurGraph graph, double offset, double zoom, double[] mouseXY) {
		StdDraw.setPenColor(150,150,70);
		StdDraw.filledCircle(0, 0, 1);
		ST<String, WaterWay> edges = graph.getEdges();
		double phi, theta;
		for (String key : edges.keys()) {
			int numPoints = edges.get(key).getTheta().size();
			double[] coorx = new double[numPoints];
			double[] coory = new double[numPoints];
			double[] coorz = new double[numPoints];
			for (int i = 0; i < numPoints; i++) {
				theta = edges.get(key).getTheta().get(i) + offset + 180;
				phi = edges.get(key).getPhi().get(i) + 90;
				coory[i] = Math.sin((phi / 180) * Math.PI) * Math.sin((theta / 180) * Math.PI);
				coorx[i] = -Math.sin((phi / 180) * Math.PI) * Math.cos((theta / 180) * Math.PI);
				coorz[i] = -Math.cos((phi / 180) * Math.PI);
				StdDraw.setPenColor(StdDraw.BLUE);
				if (coory[i] > 0) {
					if (i > 0)
						StdDraw.line(coorx[i], coorz[i], coorx[i - 1], coorz[i - 1]);
					if (i == 0 || i == numPoints - 1) {
						StdDraw.filledCircle(coorx[i], coorz[i], .005 * Math.sqrt(coory[i]));
						if (Math.sqrt(Math.pow(mouseXY[0] - coorx[i], 2) + Math.pow(mouseXY[1] - coorz[i], 2)) <= .008
								* Math.sqrt(coory[i])) {
							StdDraw.setPenColor(StdDraw.WHITE);
							StdDraw.filledCircle(coorx[i], coorz[i], .005 * Math.sqrt(coory[i]));
							StdDraw.text(mouseXY[0], mouseXY[1] - .05,
									edges.get(key).getTheta().get(i) + "," + edges.get(key).getPhi().get(i));
							if (graph.getName(edges.get(key).getV1()) != null) {
								StdDraw.text(mouseXY[0], mouseXY[1] - .1, graph.getName(edges.get(key).getV1()));
							}
						}
					}
				}
			}
		}
	}
}
