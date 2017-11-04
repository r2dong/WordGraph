package standard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * WordGraph is an undirected graph with strings on vertices and edges between
 * strings that can be made the same with either a substitution or an insertion
 * to one of the strings. This class is case insensitive
 * 
 * Public Methods:
 * WordGraph(List) - Constructor that builds a new WordGraph from a list of strings
 * numberOfComponents() - return the number of connected components
 * shortestPath(String, String) - takes in two strings and returns the shortest
 * path between them as a list of strings.
 * 
 * @author Rentian Dong
 */
public class WordGraph {

	private HashSet<String> vertexNames; // set of all vertices on this graph
	private Hashtable<String, Vertex> vertices; // all vertices
	
	/**
	 * WordGraph Constructor
	 * 
	 * @param strings a list of strings to construct the WordGraph from
	 */
	public WordGraph(List<String> strings) {
		
		// create all vertices on to WordMap
		String next; // data on next vertex to create
		Iterator<String> iterator = strings.iterator();
		vertexNames = new HashSet<String>();
		vertices = new Hashtable<String, Vertex>();
		while (iterator.hasNext()) {
			next = iterator.next();
			vertexNames.add(next);
			vertices.put(next, new Vertex(next));
		}
		
		// create all connections
		Iterator<String> outerIterator = vertexNames.iterator();
		Iterator<String> innerIterator = vertexNames.iterator();
		String outer; // temp varaible to reference to data on vertex
		String inner;
		Vertex outerVertex; // temp variables to reference vetices
		Vertex innerVertex;
		
		while (outerIterator.hasNext()) {
			outer = outerIterator.next();
			outerVertex = vertices.get(outer);
			while (innerIterator.hasNext()) {
				inner = innerIterator.next();
				innerVertex = vertices.get(inner);
				if (outerVertex.hasEdge(innerVertex))
					outerVertex.addConnection(inner);
			}
			innerIterator = vertexNames.iterator();
		}
	}
	
	/**
	 * calculated the number of connected components in this WordGraph using
	 * Depth First Search. One connected component is formed by the complete
	 * collection of words that are able to reach each other.
	 * 
	 * @return an int representing number of connected components
	 */
	public int numberOfComponents() {
		
		// make a deep copy of vertex data representing untraversed vertices
		HashSet<String> unTraversed = new HashSet<String>();
		Iterator<String> iterator = vertexNames.iterator();
		while (iterator.hasNext())
			unTraversed.add(iterator.next());
		
		// use DFS to count all connected components
		undiscoverAll();
		int count = 0; // number of connected groups
		String start;
		while (unTraversed.size() > 0) {
			
			// iterator will always be able to return an element with set size > 0
			iterator = unTraversed.iterator();
			start = iterator.next();
			
			// exahust the current connected group using DFS
			depthFirstSearch(vertices.get(start), unTraversed);
			
			// increment count after current connected group exhausted
			count++;
		}
		
		return count;
	}
	
	/**
	 * Helper method for Depth Frist Search. Any discovered vertex is removed
	 * immediately from the vertices remain to be searched
	 * 
	 * @param working a Vertex that is being searched in the current recursion
	 * @param unTraversed a collection of data on vertices that have not been
	 * traversed yet
	 */
	private void depthFirstSearch(Vertex working, HashSet<String> unTraversed) {
		
		// update traverse condition of working vertex
		working.discovered = true;
		unTraversed.remove(working.data);
		
		// traverse on all edges of the working vertex
		for (String vertexDataNext : working.connections)
			if (unTraversed.contains(vertexDataNext)) {
				Vertex vertexNext = vertices.get(vertexDataNext);
				vertexNext.parent = working.data;
				depthFirstSearch(vertexNext, unTraversed);
			}
	}
	
	/**
	 * find a shortest path between two words
	 * 
	 * @param word1 the starting word
	 * @param word2 the destination word
	 * @return an ArrayList<String> containing the path
	 */ 
	public ArrayList<String> shortestPath(String word1, String word2) {
		
		ArrayList<String> path = new ArrayList<String>(); // path between words
		
		// edge case where word1 is not in graph
		if (!vertexNames.contains(word1))
			return path;
		
		// edge case where starting and ending point are same
		if (word1.equals(word2)) {
			path.add(word1);
			path.add(word2);
			return path;
		}
		
		Integer numLevel = 0; // the number of current level
		Hashtable<Integer, HashSet<String>> levels; // table for all BFS levels
		HashSet<String> currentLevel; // current level of BFS search
		HashSet<String> nextLevel; // next level of BFS search
		Vertex vertexNext; // next vertex searched
		Vertex vertexTemp; // used in tracing shortest path
		String dataTemp; 
		
		// initialize levels of BFS
		levels = new Hashtable<Integer, HashSet<String>>();

		// initialize discovered states
		undiscoverAll();
		vertices.get(word1).discovered = true;

		// initialize first level of search to starting point
		currentLevel = new HashSet<String>();
		currentLevel.add(word1);
		levels.put(0, currentLevel);

		// loop through all nodes
		while (!currentLevel.isEmpty()) {
			// initialize next level to empty set
			nextLevel = new HashSet<String>();
			
			// search through all vertices on the current level
			for (String vertexData : currentLevel)
				
				// search through all nodes upstream of current node
				for (String vertexDataNext : vertices.get(vertexData).connections) {
					
					vertexNext = vertices.get(vertexDataNext);
					
					// update traversal status of the vertex
					if (vertexNext.discovered == false) {
						vertexNext.discovered = true;
						vertexNext.parent = vertexData;
						nextLevel.add(vertexDataNext);
					}
					
					// check if found word2 
					if (vertexDataNext.equals(word2)) {
						vertexTemp = vertexNext;
						
						// we can use a do while since word2 should have
						// at least one parent here
						do {
							dataTemp = vertexTemp.parent;
							path.add(dataTemp);
							vertexTemp = vertices.get(dataTemp);
						} while (vertexTemp.parent != null);
						
						// put path into correct order and add word2
						reverse(path);
						path.add(word2);
					}
				}

			// increment level count then store completed current level
			levels.put(++numLevel, nextLevel);

			// read next iteration
			currentLevel = nextLevel;
		}
		
		// should return a empty path if word2 is not in graph
		return path;
	}
	
	/**
	 * toString returns a string representation of this WordGraph for testing
	 * 
	 * @return a string representation of this WordGraph
	 */
	public String toString() {
		
		String strRep = ""; // string representation to be returned
		
		// copy all verticies' data into an arrayList and sort them
		ArrayList<String> vertexNamesOrdered = new ArrayList<String>();
		Iterator<String> iterator = vertexNames.iterator();
		while (iterator.hasNext())
			vertexNamesOrdered.add(iterator.next());
		QuickSort.quickSort(vertexNamesOrdered, 0, vertexNamesOrdered.size() - 1);
		
		// print ordered names of all vertices
		strRep += "All Vertices:\n";
		for (int index = 0; index < vertexNamesOrdered.size(); index++) {
			strRep += vertexNamesOrdered.get(index);
			strRep += " ";
			// print 10 perline
			if (index % 10 == 0 && index != vertexNamesOrdered.size() - 1 && index != 0)
				strRep += "\n";
		}
	
		// print all vertices and their connections
		strRep += "\nAll Vertices and their connected vertices:\n";
		for (int index = 0; index < vertexNamesOrdered.size(); index++) {
			strRep += vertexNamesOrdered.get(index);
			strRep += ": ";
			
			// order connections of the current vertex
			ArrayList<String> connectionsTemp = new ArrayList<String>();
			for (String data : vertices.get(vertexNamesOrdered.get(index)).connections)
				connectionsTemp.add(data);
			QuickSort.quickSort(connectionsTemp, 0, connectionsTemp.size() - 1);
			
			// add the ordered connections into string representation
			for (int index2 = 0; index2 < connectionsTemp.size(); index2++) {
				strRep += connectionsTemp.get(index2);
				strRep += " ";
			}
			strRep += "\n";
		}
		
		return strRep;
	}
	
	/**
	 * return all Vertcies in this WordGraph to the state before traversal
	 */
	private void undiscoverAll() {
		Vertex tempVertex;
		for (String tempName : vertexNames) {
			tempVertex = vertices.get(tempName);
			tempVertex.discovered = false;
			tempVertex.parent = null;	
		}
	}
	
	/**
	 * reverse an ArrayList
	 * 
	 * @param toReverse the arrayList to be reversed
	 */
	private void reverse(ArrayList<String> toReverse) {
		String temp; // to store a string during swapping
		int size = toReverse.size();
		for (int index = 0; index < size / 2; index++) {
			temp = toReverse.get(index);
			toReverse.set(index, toReverse.get(size - 1 - index));
			toReverse.set(size - 1 - index, temp);
		}
	}
}
