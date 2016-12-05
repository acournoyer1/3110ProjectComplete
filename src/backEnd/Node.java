package backEnd;
/**
 * Node class that holds the properties of every node that will be manipulated.
 *
 * Created by: Daman Singh
 * Last Edited by: Ryan Ha
 */

import java.util.*;

import userInterface.NodeImageGUI;

public class Node {
	private String name;
	private HashSet<Node> connections;
	private ArrayList<Integer> MessagesVisited;
	private NodeImageGUI image;
	
	/**
	 * Node constructor: 
	 * 
	 * Create a node that has connections to other nodes 
	 * held in a linkedList
	 */
	public Node(String name){
		this.name = name;
		connections = new HashSet<Node>();
		this.MessagesVisited = new ArrayList<Integer>();
	}
	
	public NodeImageGUI getNodeImage()
	{
		return image;
	}
	
	public void setNodeImage(NodeImageGUI image)
	{
		this.image = image;
	}
	
	/**
	 * Get the name of the current node object.
	 * 
	 * @ret a string containing the name of the node.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set the name of the node
	 *
	 * @param1 the string that the node will be set to
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the hashset of nodes that the current node is connected to
	 *
	 * @ret a hashset of connections
	 */
	public HashSet<Node> getConnections() {
		return connections;
	}
	
	/**
	 * Connect this node to another node
	 *
	 * @param1 the node object that this node will be connected to
	 */
	public void connect(Node n)
	{
		connections.add(n);
		n.connections.add(this);
	}
	
	/**
	 * Disconnect this node from another node
	 *
	 * @param2 the node to disconnect from
	 */
	public void disconnect(Node n)
	{
		connections.remove(n);
		n.connections.remove(this);
	}

	/**
	 * Returns a list of all nodes connected to this node
	 * 
	 * @ret a string of the node properties
	 */
	public String getDetails()
	{
		String s = "Name: " + name + "\n"
				+ "    Connections: ";
		if(connections.size() == 0) return s + "no connections.";
		else
		{
			for(Node n: connections)
			{
				s += n.name + " ";
			}
		    return s;
		}
	}

	/**
	 * Add a message ID to the list
	 */
	public void addMessagesVisited(int msgID){
		MessagesVisited.add(msgID);
	}
	
	public ArrayList<Integer> getMessagesVisited(){
		return MessagesVisited;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
