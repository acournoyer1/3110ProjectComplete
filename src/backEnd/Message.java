package backEnd;

/**
 * MESSAGE V2.0
 * @Author Daman Singh , Ryan Ha
 * Last Edited Nov 06th 2016
 * 
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.LinkedList;

import userInterface.NodeImageGUI;

public class Message {
	private Node dest;
	private int count;
	private LinkedList<Node> nodePath;
	private int id;
	private boolean stopped = false;
	private int unvisitedCount = 0;	
	private static int idGenerator = 1;
	
	public Message(Node src, Node dest){
		this.dest = dest;
		count = 0;
		this.nodePath = new LinkedList<Node>();
		nodePath.addFirst(src);
		this.id = idGenerator++;
		src.addMessagesVisited(this.id);
	}

	public Message(int parentID, Node dest, Node src, int parentCount){
		this.id = parentID;
		this.nodePath = new LinkedList<Node>();
		this.dest = dest;
		this.count = parentCount;
	}
	
	/**
	 * appendPath method:
	 * Puts a node on the message's list of path
	 * 
	 * @param node to add to the path
	 */
	public void appendPath(Node node){
		this.nodePath.add(node);
	}
	
	/**
	 * getId method:
	 * Get the id of the message
	 * 
	 * @return the integer value of the node's ID
	 */
	public int getId()
	{
		return id;
	}
	
	/**
	 * reset method:
	 * reset the id generator value
	 */
	public static void reset(){
		idGenerator =1;
	}
	
	/**
	 * getPath method:
	 * returns the path of the message
	 * 
	 * @return the LinkedList of the node
	 */
	public LinkedList<Node> getPath(){
		return nodePath;
	}
	
	/**
	 * getSrc method:
	 * Get the first node in the path
	 * 
	 * @return the node in the path
	 */
	public Node getSrc() {
		return nodePath.getFirst();
	}
	
	/**
	 * getDest method:
	 * Get the destination node of the message
	 * 
	 * @return node object of the destination
	 */
	public Node getDest() {
		return dest;
	}
	
	/**
	 * reachedDestination method:
	 * check if the message has reached the destination
	 * 
	 * @return a boolean value checking if the destination and value on nodepath is the same
	 */
	public boolean reachedDestination()
	{
		return nodePath.getLast().equals(dest);
	}
	
	/**
	 * getCount method:
	 * return the count of the current message
	 * 
	 * @return the integer value of count
	 */
	public int getCount() {
		return count;
	}
	
	/**
	 * incCount method:
	 * increment the count of the current message
	 */
	public void incCount() {
		count++;
	}
	
	/**
	 * stop method:
	 * Change the variable of the message to stop 
	 */
	public void stop()
	{
		stopped = true;
	}
	
	/**
	 * isStopped method:
	 * check if the message has stopped
	 * 
	 * @return a boolean representing the stopped state
	 */
	public boolean isStopped()
	{
		return stopped;
	}
	
	/**
	 * getUnvisitedCount method:
	 * Return the message's unvisited count number
	 * 
	 * @return an integer representing this count
	 */
	public int getUnvisitedCount(){
		return unvisitedCount;
	}
	
	/**
	 * incUnvisitedCount method:
	 * Increment the unvisited count value
	 */
	public void incUnvisitedCount(){
		unvisitedCount++;
	}
}