
import java.util.ArrayList;

public class WaterWay {

	private int V1, V2;
	private ArrayList<Double> theta, phi;
	private double length;
	private String id;

	public WaterWay(int V1, int V2, ArrayList<Double> theta, ArrayList<Double> phi, double length, String id) {
		this.V1 = V1;
		this.V2 = V2;
		this.theta = theta;
		this.phi = phi;
		this.length = length;
		this.id = id;
	}

	/** returns the index of the first vertice of the edge */
	public int getV1() {
		return V1;
	}

	/** returns index of the second vertice of the edge */
	public int getV2() {
		return V2;
	}

	/**
	 * returns theta coordinate of vertices and potential inflection points of
	 * the edge
	 */
	public ArrayList<Double> getTheta() {
		return theta;
	}

	/**
	 * returns phi coordinate of vertices and potential inflection points of the
	 * edge
	 */
	public ArrayList<Double> getPhi() {
		return phi;
	}

	/** returns length of edge */
	public double getLength() {
		return length;
	}

	/** returns id of the edge */
	public String getId() {
		return id;
	}

}

