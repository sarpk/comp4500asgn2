package graphs;

public class Edge<V> {
	
	protected V source;
	protected V destination;

	public Edge( V source, V destination ) {
		this.source = source;
		this.destination = destination;
	}
	
	public V getSource() {
		return source;
	}
	
	public V getDestination() {
		return destination;
	}
		
	public Edge<V> swap( Edge<V> e ) {
		return new Edge<V>( e.getDestination(), e.getSource() );
	}
	
	public String toString() {
		return "edge(" + source + "," + destination + ") ";
	}
	
}
