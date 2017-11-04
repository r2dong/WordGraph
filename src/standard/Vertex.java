package standard;

import java.util.HashSet;
import static java.lang.Math.abs;

/**
 * Vertex class represents vertices in a WordGraph
 * 
 * @author Rentian Dong
 */
public class Vertex {
	
	protected String data; // string on this vertex
	protected String parent; // data on Vertex from which this Vertex is discovered
	protected HashSet<String> connections; // vertices that share edges with
	protected boolean discovered; // if this Vertex is discovered in a traversal
	
	/**
	 * Vetex Constructor. Only data on current vertex is added during
	 * construction, connections are added later
	 * 
	 * @param data the data to be stored on this vertex
	 */
	public Vertex(String data) {
		this.data = data;
		connections = new HashSet<String>();
		discovered = false;
		//FIXME may need to initialize distance as well
	}
	
	/**
	 * add a new connection to this vertex
	 * 
	 * @param toAdd data on vertex to be added as a new connection
	 */
	public void addConnection(String toAdd) {
		connections.add(toAdd);
	}
	
	/**
	 * determines if the other vertex should have an edge with this vertex, ie,
	 * if they can be made equivalent by the insertion or substitution in either
	 * word
	 * 
	 * @param other the other vertex
	 * @return true if the two vertices should have an edge, false otherwise
	 */
	public boolean hasEdge(Vertex other) {
		
		// difference in length of data on both vertices
		int diffLength = abs(data.length() - other.data.length());
		
		// no edge if length differ by 2 or more
		if (diffLength >= 2)
			return false;
		// if length differ by 1, all letter in the short string should appear
		// exactly once in the longer string
		else if (diffLength == 1) {
			int indexShort = 0; // loop indexes, one each for longer and shorter
			int indexLong = 0;
			// determine longer and shorter strings of both vertices
			String longer = data.length() > other.data.length() ? data : other.data;
			String shorter = data.length() > other.data.length() ? other.data : data;
			// compare both strings letter by letter
			while (indexShort < shorter.length() && indexLong < longer.length()) {
				if (shorter.charAt(indexShort) != longer.charAt(indexLong)) {
					// comparing at missing letter
					if (indexShort == indexLong)
						indexLong++;
					// more letters than the missing one mismatch, no edge
					else
						return false;
				}
				indexShort++;
				indexLong++;
			}
			// if execution reaches this point, only the missing letter differ, have edge
			return true;
		}
		// if length are the same, has edge if differ by 1 letter only
		else {
			int diffLetter = 0; // # letters that are different
			int index = 0; // loop index
			while (diffLetter < 2 && index < data.length()) {
				if (data.charAt(index) != other.data.charAt(index))
					diffLetter++;
				index++;
			}
			// have edge if only different by 1 letter
			if (diffLetter == 1)
				return true;
			else
				return false;
		}
	}
}
