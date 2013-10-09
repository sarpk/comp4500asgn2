package waypoints;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.MatchResult;
import graphs.Vertex;

public class WaypointsTest {

	/* Main program reads in a description of a 
	 * graph from the input file (args[0]), calculates all-pairs
	 * shortest paths, then determines the shortest paths taking into
	 * account way points. 
	 */
	public static void main(String[] args) {
		//String testFile = args[0];
		String testFile = "src/waypoints/sample.txt";
		WPGraph G = readWPG(testFile);
		// modifyGraph(G);
		G.allPairsShortestPaths();
		G.SPWP();
	}

	/** Read input from the named file and construct the corresponding
	 * graph. The format of the input is described in the assignment handout.
	 */
	public static WPGraph readWPG(String fileName) { 
		WPGraph G = new WPGraph();
        Scanner s = null;
        try {
            s = new Scanner(new BufferedReader(new FileReader(fileName)));
            int n = s.nextInt();
			// add n vertices 
			for( int i = 0; i < n; i++ ) { G.addVertex(new Vertex()); }
			s.nextLine(); // read newline character

			// Read in way points 
			if (s.findInLine("Waypoints (.*)") != null) {
				Scanner sc = new Scanner(s.match().group(1));
				ArrayList<Integer> twps = new ArrayList<Integer>();
				while (sc.hasNextInt()) {
					Integer wp = new Integer(sc.nextInt());
					twps.add(wp);
				}
				G.setWaypoints(twps);
				s.nextLine();
			}

			// Read in edges
			while (s.findInLine("Path (.*)") != null) {
				Scanner sc = new Scanner(s.match().group(1));
				int src = sc.nextInt();
				while (sc.hasNextInt()) {
					int dest = sc.nextInt();
					if (src >= dest) {
						throw new Exception("Increasing vertex numbers only");
					}
                	G.addEdge(src, dest);
					src = dest;
				}
				s.nextLine(); 
			}

			while (s.findInLine("Weight (\\d+) (\\d+) (\\d+)") != null) {
				Scanner sc = new Scanner(s.match().group(0));
				sc.next();
				int src = sc.nextInt();
				int dest = sc.nextInt();
				int weight = sc.nextInt();
				G.getEdge(src, dest).setWeight(weight);
				s.nextLine(); 
			}
				
        } catch (Exception e) {
			System.err.println("Error parsing input");
			e.printStackTrace();
		} finally {
            if (s != null) {
                s.close();
            }
        }
		return G;
    }

	/**
	 * Modify the graph by adding more edges, etc.  Does not necessarily
	 * need to be changed
	 */
	public static void modifyGraph(WPGraph G) { }

}
