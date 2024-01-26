/** @author Igor Kojanin
 * 
 */
package com.simulation.partypeople.igor;

import java.util.ArrayList;
import java.util.Iterator;
import com.simulation.enums.*;

public class Vertex implements Iterable<Vertex>{
	private static int currentKey = 0;
	private int key;
	private Places place;
	private ArrayList<Vertex> adjacencyList; // each vertex has its own adjacency list 4->5->6
																					// 5->7 ...
	private boolean visited;

	public Vertex(Places place) {
		key = currentKey;
		currentKey++;

		generateEmptyAdjacancyList();

		visited = false;
		this.place = place;
	}

	public void setVisited() {
		visited = true;
	}

	// for traversing in the graph
	public void setNotVisited() {
		visited = false;
	}

	public boolean visited() {
		return visited;
	}

	public int getKey() {
		return key;
	}

	public Places getPlace() {
		return place;
	}

	public ArrayList<Vertex> getAdjacencyList() {
		return adjacencyList;
	}

	public void addNeighbor(Vertex newNeighbour) {
		if (!adjacencyList.contains(newNeighbour)) {
			adjacencyList.add(newNeighbour);
			newNeighbour.adjacencyList.add(this);
		}
	}

	public void printAdjacencyList() {
		for (Vertex neighbour : adjacencyList) {
			System.out.print(" --> " + neighbour.getKey());
		}
		System.out.println();
	}

	public void clearNeihgborhoodList() {
		generateEmptyAdjacancyList();
	}

	private void generateEmptyAdjacancyList() {
		adjacencyList = new ArrayList<Vertex>();
	}
	
    public int getNumberOfNeighbors() {
        return adjacencyList.size();
    }

	// Helper function to decide which vertex to visit next -> Get the vertex with least Neighbors
    public int getAdjacentVertexWithLeastNeighbors() {
        if (adjacencyList.isEmpty()) {
            // No neighbors
            return -1;
        }

        int minNeighbors = Integer.MAX_VALUE;
        int minNeighborsVertexId = -1;

        for (Vertex neighbor : adjacencyList) {
            int neighborId = neighbor.getKey();
            int neighborNeighborCount = neighbor.getAdjacencyList().size();

            if (neighborNeighborCount < minNeighbors && !neighbor.visited()) {
                minNeighbors = neighborNeighborCount;
                minNeighborsVertexId = neighborId;
            }
        }
        if (minNeighborsVertexId == -1) // visited all neighbors
            return this.getKey();
        else
        	return minNeighborsVertexId;
    }

	@Override
	public Iterator<Vertex> iterator() {
		return new Iterator<Vertex>() {
			private Vertex tmp = adjacencyList.get(0); // temporary variable(starts with the first element) that runs through the list- changed when next is called
			private int size = adjacencyList.size();
			private int i = 0;

			@Override
			public boolean hasNext() {
				return i < size;
			}

			@Override
			public Vertex next() {
				Vertex nextElement = tmp;
				if (++i < size) // this is for the last place in the array
					tmp = adjacencyList.get(i);
				return nextElement;
			}
		};
	}
}