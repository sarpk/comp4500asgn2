package graphs;


import java.util.Iterator;

/** Directed or undirected graph
 * The main difference is in the treatment of edges.
 */
public interface Graph<V extends Vertex, E extends Edge<V>> 
		extends Iterable<V>{

	/** @return the size of the graph */
	public int size();
	
	/** Constructs a new vertex and adds to graph */
	public void addVertex( V v );
	
	/** Returns vertex numbered i */
	public V getVertex( int i );
	    
	/** @return true if and only if graph has vertex with index i. */
    public boolean hasVertex( int i );

    /** Add edge e to the graph. */
    public void addEdge( E e );

    /** @return true if and only if graph has edge from u to v */
    public boolean hasEdge( V u, V v );

    /** @return edge from u to v if it exists, else null */
    public E getEdge( V u, V v );

    /** @return an iterator over the vertices of the graph */
    public Iterator<V> iterator();

    /** @return iterator over the list of vertices adjacent to vertex v */
    public Iterable<E> adjacent( V v );

}
