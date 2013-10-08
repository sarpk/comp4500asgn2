package waypoints;

import graphs.Edge;
import graphs.Vertex;

public class WPEdge extends Edge<Vertex> {

	private int weight = 5;

	public WPEdge(Vertex src, Vertex dest) {
		super(src, dest);
	}

	public int getWeight() { return weight; }
	public void setWeight(int i) { weight = i; }

}
