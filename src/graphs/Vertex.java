package graphs;


public class Vertex implements Indexed {
	
	/** Used as a unique identifier for vertex to allow
	 * array-based implementation.
	 */
	private int index;
	
	public Vertex() {
	}
	public Vertex( int index ) {
		super();
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex( int i ) {
		this.index = i;
	}
	
	
	public String toString() {
		return "vertex(" + index + ")";
	}
	
}
