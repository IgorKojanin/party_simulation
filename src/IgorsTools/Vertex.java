/** @author Igor Kojanin
 * 
 */
package IgorsTools;

import java.util.ArrayList;
import java.util.Stack;
import com.simulation.enums.*;

public class Vertex {
	private static int currentKey = 0;
	private int key;
	private Places item;
	private ArrayList<Vertex> adjacencyList; // each vertex has its own adjacency list 4->5->6
																					// 5->7 ...
	private boolean marked;
	private boolean isAnEntryVertex;

	public Vertex(boolean isAnEntryVertex, Places item) {
		key = currentKey;
		currentKey++;

		generateEmptyAdjacancyList();

		marked = false;
		this.isAnEntryVertex = isAnEntryVertex;
		this.item = item;
	}

	public void setMarked() {
		marked = true;
	}

	// for traversing in the graph to know if we visited this vertix or not
	public void setUnmarked() {
		marked = false;
	}

	public boolean marked() {
		return marked;
	}

	public int getKey() {
		return key;
	}

	public Places getItem() {
		return item;
	}

	public ArrayList<Vertex> getAdjacencyList() {
		return adjacencyList;
	}

	public void addNeighbour(Vertex newNeighbour) {
		adjacencyList.add(newNeighbour);
		
		// lo yodea bishvil ma haxelek haba hakol oved gam biladav
		boolean edgeFound = false;
		int n = newNeighbour.getAdjacencyList().size();
		int i = 0;
		while (!edgeFound && i < n) {
			//System.out.println("Inside addNeighbour: newNeighbour.getAdjacencyList().get(i).getKey()= "+newNeighbour.getAdjacencyList().get(i).getKey()+" this.getKey()= "+this.getKey());
			edgeFound = newNeighbour.getAdjacencyList().get(i).getKey() == this.getKey();
			i++;
		}
		/*
		 * if ( !edgeFound ) { newNeighbour.addNeighbour(this); } // This is for
		 * undirected edge but here we want a directed edge
		 */
	}

	public void printAdjacencyList() {
		for (Vertex neighbour : adjacencyList) {
			System.out.print(" --> " + neighbour.getKey());
		}
		System.out.println();
	}

	public void clearNeihgbourhoodList() {
		generateEmptyAdjacancyList();
	}

	private void generateEmptyAdjacancyList() {
		adjacencyList = new ArrayList<Vertex>();
	}

	public void dfsVertex(Stack<Vertex> reverseOrder) {
		setMarked();
		for (Vertex vNext : adjacencyList) {
			if (!vNext.marked()) {
				vNext.dfsVertex(reverseOrder);
			}
		}
		reverseOrder.push(this);
	}

	public boolean entry() {
		return isAnEntryVertex;
	}
}