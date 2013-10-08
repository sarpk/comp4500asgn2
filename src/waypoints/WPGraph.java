package waypoints;

import java.util.ArrayList;
import graphs.*;

public class WPGraph extends DGraphAdj<Vertex, WPEdge> {	

	private static final int inf = Integer.MAX_VALUE; // "infinity"
	private int[][] d; // all-pairs shortest paths
	private int[] wps; // way points

	public void addEdge(int src, int dest) {
		super.addEdge(new WPEdge(getVertex(src), getVertex(dest)));
	}

	public WPEdge getEdge(int src, int dest) {
		return super.getEdge(getVertex(src), getVertex(dest));
	}

	/* Takes a list of Integers and copies them into the wps array,
	 * adding an extra element at the end for the Destination (which
	 * must not be a way point, but helps to simplify the dynamic
	 * programming aspects)
	*/
	public void setWaypoints(ArrayList<Integer> twps) {
		wps = new int[twps.size()+1];
		for (int i=0;i<twps.size();i++) {
			wps[i] = twps.get(i);
		}
		wps[wps.length-1] = size()-1; // place dest as the final "waypoint"
	}

	/* adds two integers, accounting for the possibility that one
	 * of them is "infinite".  If so, the addition return infinity.
	 * This must replace the usual "+" operator when performing
	 * arithmetic on weights and path lengths
	 */
    private static int add( int x, int y ) {
        if( x == inf || y == inf ) {
            return inf;
        } else {
            return x + y;
        }
    }

	/* Auxiliary method for printing matrices */
	public void printMatrix(int[][] m) {
		int n = m[0].length;
		String s = "";
		for( int j = 0; j < n; j++ ) {
			for( int i = 0; i < n; i++ ) {
				int x = m[i][j];
				if (x == inf) {
					s += String.format("%4s", "-");
				} else {
					s += String.format("%4d", x);
				}
			}
			s += "\n";
		}
		System.out.println(s);
	}

	/* Stores the lengths of all-pairs shotest paths in array d */
	public void allPairsShortestPaths() {
		int n = size();
		d = new int[n][n];
		// Set up d with the initial weights
		for (int i=0;i<n;i++) {
			for (int j=0;j<n;j++) {
				WPEdge e = getEdge(i,j);
				if (e == null) {
					d[i][j] = inf;
				} else {
					d[i][j] = e.getWeight();
				}
			}
		}
		// Calculate shortest path costs using the Floyd-Warshall algorithm
		for( int k = 0; k < n; k++ ) {
			for( int i = 0; i < n; i++ ) {
				for( int j = 0; j < n; j++ ) {
					int viaK = add(d[i][k] , d[k][j]);
					if (viaK < d[i][j]) {
						d[i][j] = viaK;
					}
				}
			}
		}
		//printMatrix(d);
	}

	/* Your dynamic programming solution to Question 2 */
	public void SPWP() {
		// Your final output should follow this format:
		// System.out.println("Least final cost is " + ??? + ", visiting " + ??? + " waypoint(s) on a path with pure cost " + ???);
	}

}
