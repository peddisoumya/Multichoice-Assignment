package aStar;
/*
 * Defines Node properties
 */
public class Node {
	public int x,y,gCost,hCost,fCost;
	public Node parent;

	public Node(int x,int y,Node parent,int gCost,int hCost){
		this.x = x;
		this.y = y;
		this.gCost = gCost;
		this.hCost = hCost;
		this.fCost = gCost + hCost;
		this.parent= parent;
	}
}
