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
		printMatrix(d);
	}

	private int leastCost2(Vertex sourceV, int minAmount) {
		int cost = inf;

		Vertex destinationV = getVertex(d[0].length - 1);
		if (destinationV == sourceV && minAmount < 1) {
			return 0;
		} else if (destinationV == sourceV && minAmount > 1) {
			return cost;
		}
		for (WPEdge e : this.adjacent(sourceV)) {
			int countMinAm = minAmount;
			for (int w : wps) {
				if (w == sourceV.getIndex()) {
					countMinAm--;
				}
			}
			int pathCost = add(leastCost2(e.getDestination(), countMinAm),
					e.getWeight());
			cost = Math.min(cost, pathCost);
		}
		return cost;
	}

	private int leastCost(Vertex destinationV, int minAmount) {
		Vertex sourceV = getVertex(0);
		if (destinationV == sourceV && minAmount < 1) {
			return 0;
		} else if (destinationV == sourceV && minAmount > 1) {
			return inf;
		}

		int destNo = destinationV.getIndex();
		int cost = inf;

		for (int i = 0; i < destNo; i++) {
			if (d[i][destNo] != inf) {
				int countMinAm = minAmount;
				for (int w : wps) {
					if (w == i) {
						countMinAm--;
					}
				}
				int pathCost = add(leastCost(this.getVertex(i), countMinAm),
						d[i][destNo]);
				cost = Math.min(cost, pathCost);
			}
		}
		return cost;
	}

	private int leastCost3(Vertex destinationV, int minAmount) {
		Vertex sourceV = getVertex(0);
		Vertex firstWp = getVertex(wps[0]);
		if (destinationV == sourceV && minAmount < 1) {
			return 0;
		} else if ((destinationV == sourceV || destinationV == firstWp)
				&& minAmount > 1) {
			return inf;
		} else if (destinationV == firstWp && minAmount < 1) {
			return d[0][wps[0]];
		}

		int destNo = destinationV.getIndex();
		int cost = inf;

		for (int w : wps) {
			int countMinAm = minAmount;

			if (w < destNo) {
				countMinAm--;
				//System.out.println("w is " + w + " to dest " + destNo);
				int rCost = leastCost3(this.getVertex(w), countMinAm);
				int pathCost = add(rCost, d[w][destNo]);
				// System.out.println("w is " + w + " adding " + pathCost +
				// " to dest " + destNo
				// + " d w destN is " + d[w][destNo] + " rCost is "
				// + rCost);
				cost = Math.min(cost, pathCost);
			}
		}

		return cost;
	}

	/* Your dynamic programming solution to Question 2 */
	public void SPWP() {

		//initialise 2d array for waypoint source to minimum amount of waypoint matrix
		int wAm = wps.length;
		int destIndex = size()-1;
		int matrix[][] = new int[wAm][wAm];
		for (int i=0;i<wAm;i++) {
			for (int j=0;j<wAm;j++) {
				matrix[i][j] = inf;
			}
		}
		
		//get initial distances from source to wp_i
		for (int i = 0; i < wAm; i++) {
			matrix[i][0] = d[0][wps[i]];
		}
		printMatrix(matrix);
		
		for (int i=1;i<wAm;i++) {
			for (int j=i;j<wAm;j++) {
				System.out.println("for i " + i + " and j " + j);
				//int toBeAdded = 0;
				for (int k = j-1; k >= i-1; k--) {
					System.out.println("k " +k + " to i-1 " + (i-1) + " is " + matrix[k][i-1] + " and i to end " + d[wps[k]][wps[i]] + " where wps[i] is " + wps[i] + " and [wps[k] is " + wps[k]);
					int toBeAdded = 0;
					toBeAdded = add(matrix[k][i-1], toBeAdded);
					toBeAdded = add(d[wps[k]][wps[j]], toBeAdded);
					matrix[j][i] = Math.min(toBeAdded, matrix[j][i]) ;
				}
				
				printMatrix(matrix);
			}
		}
		
		
		double leastFinalCost = inf;
		int pCost = inf;
		for (int i = 0; i < wAm; i++) {
			System.out.println(matrix[wAm-1][i]);
			double finalCost = (matrix[wAm-1][i] * Math.pow(0.9, i));
			if (finalCost < leastFinalCost) {//equivalent to Math.min()
				leastFinalCost = finalCost;
				pCost = matrix[wAm-1][i];
			}
			//leastFinalCost = Math.min(leastFinalCost, );
		}
		System.out.println(leastFinalCost + " with pure cost " + pCost);
		int maxDInd = size() - 1;
		int cost = inf;
		int visited = 2;
		for (int wayIndex = wps.length - 1; wayIndex >= 0; wayIndex--) {
			int w1 = wps[wayIndex];
			// for (int w1 : wps) {

			if (w1 < maxDInd) {
				System.out.println("w1 is " + w1);
				int tempVisited = visited;
				tempVisited--;
				int minFoundCost = inf;

				for (int wayIndex2 = wayIndex; wayIndex2 >= 0; wayIndex2--) {
					int w2 = wps[wayIndex2];
					// for (int w2 : wps) {

					if (w2 < w1) {
						tempVisited--;
					}
					if (tempVisited < 1) {
						int dist = add(d[0][w2], add(d[w2][w1], d[w1][maxDInd]));
						//System.out.println("w2 is " + w2 + " to dest "
							//	+ maxDInd + " with dist of " + dist);
						minFoundCost = Math.min(minFoundCost, dist);

					}

				}

				if (tempVisited < 1) {
					cost = Math.min(cost, minFoundCost);
				}

			}
			// maxDInd = w1;
		}
		System.out.println("The rec cost is " + cost);

		cost = leastCost(getVertex(d[0].length - 1), visited);
		System.out.println("The cost is " + cost);

		cost = leastCost2(getVertex(0), visited);
		System.out.println("The cost2 is " + cost);

		cost = leastCost3(getVertex(d[0].length - 1), visited);
		System.out.println("The cost3 is " + cost);

		for (int i : wps) {
			Vertex waypoint = this.getVertex(i);
			// if(waypoint.getIndex() == d.length-1) { continue;}
			System.out.println(waypoint + " begin with " + d[0][i] + " to end "
					+ d[i][d[0].length - 1]);
		}
		// Your final output should follow this format:
		int pureCost = d[0][d[0].length - 1];
		// determine the path
		int prevCost = inf;
		int edgeSrc = inf;
		Vertex v = getVertex(0);
		for (WPEdge ver : this.adjacent(v)) {
			System.out.println(ver + " " + wps[0]);
		}

		for (int i = 0; i < d[0].length; i++) {
			WPEdge e = getEdge(i, 5);
			if (e != null) {
				System.out.println(e + " " + e.getWeight() + " " + d[0][i]);
				if (prevCost > d[0][i]) {
					prevCost = d[0][i];
					edgeSrc = i;
				}
			}
		}
		System.out.println(prevCost + " " + edgeSrc);
		// System.out.println("Least final cost is " + ??? + ", visiting " + ???
		// + " waypoint(s) on a path with pure cost " + ???);
	}
}