
/**
 * MESSAGE V2.0
 * @Author Daman Singh , Ryan Ha
 * Last Edited Nov 06th 2016
 * 
 */

import java.util.LinkedList;

public class Message {
	private Node dest;
	private int count;
	private LinkedList<Node> nodePath;
	private int id;
	
	private static int idGenerator = 1;
	
	public Message(Node src, Node dest){
		this.dest = dest;
		count = 0;
		this.nodePath = new LinkedList<Node>();
		nodePath.addFirst(src);
		this.id = idGenerator++;
	}

	public Message(int parentID, Node dest, Node src, int parentCount){
		this.id = parentID;
		this.nodePath = new LinkedList<Node>();
		this.dest = dest;
		this.count = parentCount;
	}
	
	public void appendPath(Node node){
		this.nodePath.add(node);
	}
	
	public int getId()
	{
		return id;
	}
	
	public static void reset(){
		idGenerator =1;
	}
	public LinkedList<Node> getPath(){
		return nodePath;
	}
	
	public Node getSrc() {
		return nodePath.getFirst();
	}
	
	
	public Node getDest() {
		return dest;
	}
	
	
	
	public int getCount() {
		return count;
	}
	
	public void incCount() {
		count++;
	}
	
}
