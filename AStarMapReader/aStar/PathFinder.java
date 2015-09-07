package aStar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class PathFinder {
	/*
	 * Finds the best path using Astar algorithm
	 */
	public List<Node> findPath(int startX, int startY, int goalX, int goalY,int largeMap[][]){
		//Reading properties from constants file
		Constants con= new Constants();
		con.getConstants();
		int mapHeight=con.mapHt;
		int mapWidth = con.mapWd;

		List<Node> openList = new ArrayList<Node>();
		List<Node> closedList = new ArrayList<Node>();
		Node current = new Node(startX,startY,null,0,getDistance(startX, startY, goalX, goalY));

		openList.add(current);
		while(openList.size() > 0){
			//Sorting openList
			Collections.sort(openList,nodeSorter);
			current = openList.get(0); 
			//If we reach the goal reconstruct the path
			if(current.x == goalX && current.y == goalY){
				List<Node> bestPath = new ArrayList<Node>();
				while (current.parent != null){
					bestPath.add(current);
					current  = current.parent;
				}
				openList.clear();
				closedList.clear();
				return bestPath;
			}
			openList.remove(current);
			closedList.add(current);
			//Checking the possible adjacent nodes for next step
			for (int i=0; i<9; i++ ){
				if(i==4) continue;
				int x = current.x;
				int y = current.y;
				int xi = (i%3) - 1;
				int yi = (i/3) - 1;
				int adjX = x+xi, adjY = y+yi;
				//Checking for edges
				if(adjX < 0 || adjY < 0)
					continue;
				if(adjX == mapHeight || adjY == mapWidth)
					continue;
				int cost = largeMap[adjX][adjY]; 
				if (cost == 0) continue;//Checking for an obstacle
				int adjGCost = current.gCost + cost;
				int adjHCost = getDistance(adjX,adjY, goalX, goalY);
				Node node = new Node(adjX,adjY,current,adjGCost,adjHCost);
				if (inList(closedList,node) && adjGCost >= current.gCost) continue;
				if (!inList(openList,node) || adjGCost < current.gCost) openList.add(node);
			}
		}
		closedList.clear();
		return null;	
	}
	/*
	 * Checks whether Node belongs to a list
	 */
	private boolean inList(List<Node> list,Node adj){
		for(Node n : list){
			if(adj.x == n.x && adj.y == n.y) return true;
		}
		return false;
	}
	/*
	 * Compares two nodes for lowest cost node
	 */
	private Comparator<Node> nodeSorter = new Comparator<Node>(){
		public int compare(Node n0, Node n1) {
			if(n1.fCost < n0.fCost) return +1;
			if(n1.fCost > n0.fCost) return -1;
			return 0;
		}
	};
	/*
	 * Calculating Manhattan distance between current node and goal
	 */
	private int getDistance(int tileX, int tileY, int goalX, int goalY){
		int distance = Math.abs(tileX-goalX)+Math.abs(tileY-goalY);
		return distance;
	}
}
