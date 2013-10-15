/**
 * COMP4500 Assignment 2 Question 2, implementation
 * Author: Sarp Kaya, s43324842
 * PS: Line 78 is changed as discussed on COMP4500 newsgroup
 */
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

	/*
	 * Takes a list of Integers and copies them into the wps array, adding an
	 * extra element at the end for the Destination (which must not be a way
	 * point, but helps to simplify the dynamic programming aspects)
	 */
	public void setWaypoints(ArrayList<Integer> twps) {
		wps = new int[twps.size() + 1];
		for (int i = 0; i < twps.size(); i++) {
			wps[i] = twps.get(i);
		}
		wps[wps.length - 1] = size() - 1; // place dest as the final "waypoint"
	}

	/*
	 * adds two integers, accounting for the possibility that one of them is
	 * "infinite". If so, the addition return infinity. This must replace the
	 * usual "+" operator when performing arithmetic on weights and path lengths
	 */
	private static int add(int x, int y) {
		if (x == inf || y == inf) {
			return inf;
		} else {
			return x + y;
		}
	}

	/* Auxiliary method for printing matrices */
	public void printMatrix(int[][] m) {
		int n = m[0].length;
		String s = "";
		for (int j = 0; j < n; j++) {
			for (int i = 0; i < n; i++) {
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
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				WPEdge e = getEdge(i, j);
				if (e == null) {
					d[i][j] = (i == j) ? 0 : inf; // d[i][j] = inf;
				} else {
					d[i][j] = e.getWeight();
				}
			}
		}
		// Calculate shortest path costs using the Floyd-Warshall algorithm
		for (int k = 0; k < n; k++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					int viaK = add(d[i][k], d[k][j]);
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

		//initialise 2d array for waypoint source to minimum amount of waypoint matrix
		int wAm = wps.length;
		int matrix[][] = new int[wAm][wAm];
		//initialise all values to infinity
		for (int i=0;i<wAm;i++) {
			for (int j=0;j<wAm;j++) {
				matrix[i][j] = inf;
			}
		}
		
		//get initial distances from source to wp_i
		for (int i = 0; i < wAm; i++) {
			matrix[i][0] = d[0][wps[i]];
		}
		
		//construct the matrix by starting from i 1(as in minimum amount of waypoints to be visited)
		for (int i=1;i<wAm;i++) {
			for (int j=i;j<wAm;j++) {
				for (int k = j-1; k >= i-1; k--) {
					int toBeAdded = add(matrix[k][i-1], d[wps[k]][wps[j]]);//the "recurrence"
					matrix[j][i] = Math.min(toBeAdded, matrix[j][i]) ;
				}
			}
		}
		
		//minCost(c) implementation
		double leastFinalCost = inf;
		int pCost = inf;
		int visitingWaypoint = 0;
		for (int i = 0; i < wAm; i++) {
			double finalCost = (matrix[wAm-1][i] * Math.pow(0.9, i));
			if (finalCost < leastFinalCost) {//equivalent to Math.min()
				leastFinalCost = finalCost;
				pCost = matrix[wAm-1][i];//get the pure cost
				visitingWaypoint = i;//get the min visiting waypoint
			}
		}

		 System.out.println("Least final cost is " + leastFinalCost + ", visiting " + visitingWaypoint
		 + " waypoint(s) on a path with pure cost " + pCost);
	}
}