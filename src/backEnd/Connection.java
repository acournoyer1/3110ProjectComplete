package backEnd;
import java.awt.Graphics2D;

import userInterface.NodeImageGUI;

/**
 * Connection class defines the properties to connect two nodes
 * 
 * Created By: Alex Cournoyer
 * Last Edited By:
 */
public class Connection {
	private Node firstNode;
	private Node secondNode;
	
	public Connection(Node n1, Node n2)
	{
		firstNode = n1;
		secondNode = n2;
	}
	
	/*
	 * Get the first node that this connection is associated with
	 *
	 * @ret a node object with the associated node
	 */
	public Node getFirstNode()
	{
		return firstNode;
	}
	
	/*
	 * Get the second node that this connection is associated with
	 *
	 * @ret a node object with the associated node
	 */
	public Node getSecondNode()
	{
		return secondNode;
	}
	
	/*
	 * Get a string defining this connection
	 *
	 * @ret a String containing the connection information
	 */
	public String toString()
	{
		return firstNode.toString() + " < - > " + secondNode.toString();
	}
	
	/*
	 * Check if the node exists for this connection
	 *
	 * @param1 the node to be checked
	 * @ret a boolean whether the node is within this connection
	 */
	public boolean contains(Node n)
	{
		
		return firstNode == n || secondNode == n;
	}
	
	/*
	 * Check if this connection is the same connection between another connection
	 * 
	 * @ret a boolean object describing if the objects are equal
	 */
	public boolean equals(Object o)
	{
		if(this == o) return true;
		if(!(o instanceof Connection)) return false;
		Connection c = (Connection)o;
		return c.firstNode.equals(this.firstNode) && c.secondNode.equals(this.secondNode);
	}

}
