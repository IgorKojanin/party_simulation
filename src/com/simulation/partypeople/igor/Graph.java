/** @author Igor Kojanin
 * 
 */
package com.simulation.partypeople.igor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Graph {
	private ArrayList<Vertex> vertices; // its basically an array of vertices which every vertices includes an array so
										// an array of arrays
	public Graph() {
		vertices = new ArrayList<Vertex>();
	}
	// A function to get the size of the graph
	public int numberOfVertices() {
		return vertices.size();
	}
	// Add a vertex to the graph
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
	
	public Vertex getVertex(int key) {
		return vertices.get(key);
	}

	// Find shortest path between Vertex v to Vertex t in the graph using BFS traversal
	// Return the ID of the next Vertex to go on the way to Vertex t
	public int nextVertexIdInShortestPath(Vertex v, Vertex t) {
        int[] markedVer = new int[numberOfVertices()];
        return bfsShortestPath(v.getKey(),t.getKey(), markedVer);
    }
	
	// Perform BFS traversal starting from vertex v to find the shortest path
    public int bfsShortestPath(int v, int d, int[] markedVer) {
        Queue<Integer> queue = new LinkedList<>();
        int[] predecessors = new int[numberOfVertices()];
        Arrays.fill(predecessors, 0);

        markedVer[v] = 1;
        queue.add(v);

        while (!queue.isEmpty() && queue.peek() != d) {
            int currentVertex = queue.poll();
            Iterator<Vertex> neighbors = getVertex(currentVertex).iterator();

            while (neighbors.hasNext()) {
                int neighbor = neighbors.next().getKey();
                if (markedVer[neighbor] == 0) {
                    markedVer[neighbor] = 1;
                    predecessors[neighbor] = currentVertex;
                    queue.add(neighbor);
                }
            }
        }
        return reconstructAndPrintPath(predecessors, v, d);
    }
    
    
    // Helper function to reconstruct the path
    private int reconstructAndPrintPath(int[] predecessors, int source, int destination) {
        List<Integer> path = new ArrayList<>();
        int current = destination;

        while (current != source) {
            path.add(current);
            current = predecessors[current];
        }

        path.add(source);
        Collections.reverse(path);

        return path.get(1);
    }
	
    public int findVertexWithFewerThanFourNeighbors(Vertex v) {
        int[] markedVer = new int[numberOfVertices()];
    	return bfsVertexWithFewerThanFourNeighbors(v.getKey(), markedVer);
    }
    
    public int bfsVertexWithFewerThanFourNeighbors(int v, int[] markedVer) {
        Queue<Integer> queue = new LinkedList<>();
        int[] predecessors = new int[numberOfVertices()];
        Arrays.fill(predecessors, -1);

        markedVer[v] = 1;
        queue.add(v);

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();
            Iterator<Vertex> neighbors = getVertex(currentVertex).iterator();

            while (neighbors.hasNext()) {
                int neighbor = neighbors.next().getKey();
                if (markedVer[neighbor] == 0) {
                    markedVer[neighbor] = 1;
                    predecessors[neighbor] = currentVertex;
                    queue.add(neighbor);

                    getVertex(neighbor).printAdjacencyList();
                    
                    if (!getVertex(neighbor).visited() && getVertex(neighbor).getAdjacencyList().size() < 4 && getVertex(neighbor).getAdjacencyList().size() > 1) {
                        return neighbor;
                    }
                }
            }
        }

        // No vertex with fewer than 4 neighbors found
        return -10;
    }

    // Print the graph -> print all vertices and their neighbors
	public void print() {
		for (Vertex v : vertices) {
			System.out.print("Vertex " + v.getKey());
			v.printAdjacencyList();
		}
	}
}
