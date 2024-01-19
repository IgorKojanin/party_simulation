/** @author Igor Kojanin
 * 
 */
package IgorsTools;

import java.util.ArrayList;
import java.util.Stack;

public class Graph {
	private ArrayList<Vertex> vertices; // its basically an array of vertices which every vertices includes an array so
										// an array of arrays
	public Graph() {
		vertices = new ArrayList<Vertex>();
	}

	public int numberOfVertices() {
		return vertices.size();
	}

	public void add(Vertex v) {
		int n = vertices.size();
		int i = 0;
		boolean keyNotFound = true;

		while (i < n && keyNotFound) {
			keyNotFound = vertices.get(i).getKey() != v.getKey();
			i++;
		}
		if (keyNotFound == false) {
			System.out.println("Key already present in the graph!");
		} else {
			vertices.add(v);
		}
	}

	public void print() {
		for (Vertex v : vertices) {
			System.out.print("Vertex " + v.getKey());
			v.printAdjacencyList();
		}
	}
	
	

	public void topoSort() {
		Stack<Vertex> reverseOrder = new Stack<Vertex>();
		for (Vertex v : vertices) {
			v.setUnmarked();
		}
		for (Vertex v : vertices) {
			if (v.entry()) {
				v.dfsVertex(reverseOrder);
			}
		}
		while (!reverseOrder.empty()) {
			System.out.println(reverseOrder.pop().getItem());
		}
	}
}
