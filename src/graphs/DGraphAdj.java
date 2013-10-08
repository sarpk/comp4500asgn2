package graphs;


/** Implementation of a directed graph using adjacency lists 
 * @author Ian Hayes
 */
public class DGraphAdj<V extends Vertex, E extends Edge<V>>
	extends GraphAdj<V, E>
	implements DGraph<V, E> 
{

    public DGraphAdj() {
    	super();
    }
    
    public void addEdge( E e ) {
    	graph.get(e.getSource().getIndex()).edges.add( e );
    }
}
