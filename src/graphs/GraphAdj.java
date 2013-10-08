package graphs;



import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;

/** This class implements the common parts of directed and undirected 
 * graphs using an adjacency list representation. 
 * Only adding edges is different between the two.
 * @author Ian Hayes
 *
 * @param <V extends Vertex> type of information stored with each vertex
 * @param <E extends Edge<V>> type of information stored with each edge
 */
abstract public class GraphAdj<V extends Vertex,E extends Edge<V>>
				implements Graph<V,E> {

	protected List<VEntry> graph;
	
    public GraphAdj() {
    	super();
    	graph = new ArrayList<VEntry>();
    }
    
	protected class VEntry {
		V vert;
		List<E> edges;
		
		VEntry( V v ) {
			super();
			this.vert = v;
			this.edges = new LinkedList<E>();
		}
	}

	private class Vertices implements Iterator<V> {
		int currentVertex;
		
		public Vertices() {
			super();
			currentVertex = -1;
		}
		
		public boolean hasNext() {
			return currentVertex < graph.size()-1;
		}
		
		public V next() {
			assert currentVertex < graph.size()-1;
			currentVertex++;
			return graph.get(currentVertex).vert;
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	private class Edges implements Iterator<E>, Iterable<E> {
		Iterator<E> edgesToGo;
		
		public Edges( V u ) {
			edgesToGo = graph.get(u.getIndex()).edges.iterator();
		}
		
		public Iterator<E> iterator() {
			return this;
		}
		
		public boolean hasNext() {
			return edgesToGo.hasNext();
		}
		
		public E next() {
			return edgesToGo.next();
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	public int size() {
		return graph.size();
	}

    public void addVertex( V v ) {
    	v.setIndex( graph.size() );
    	graph.add( new VEntry( v ) );
    }
    
    public V getVertex( int i ) {
    	return graph.get(i).vert;
    }
    
    public boolean hasVertex( int i ) {
    	return 0 <= i && i < graph.size();
    }
    
    public abstract void addEdge( E e );

    public boolean hasEdge( V u, V v ) {
    	assert hasVertex(u.getIndex()) && hasVertex(u.getIndex());
    	// Search the edges adjacent to u for vertex v
    	for( E e : adjacent( u )  ) {
    		if( e.destination == v ) {
    			return true;
    		}
    	}
    	return false;
    }

    public E getEdge( V u, V v ) {
    	assert hasVertex(u.getIndex()) && hasVertex(u.getIndex());
    	// Search the edges adjacent to u for vertex v
    	for( E e : adjacent( u )  ) {
    		if( e.destination == v ) {
    			return e;
    		}
    	}
    	return null;
    }

    public Iterator<V> iterator() {
    	return new Vertices();
    }

    public Iterable<E> adjacent( V u ) {
    	return new Edges( u );
    }

}
