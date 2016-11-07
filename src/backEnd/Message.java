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

import userInterface.NodeImage;

public class Message {
	private Node dest;
	private int count;
	private LinkedList<Node> nodePath;
	private int id;
	private boolean stopped = false;
	
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
	
	public boolean reachedDestination()
	{
		return nodePath.getLast().equals(dest);
	}
	
	
	
	public int getCount() {
		return count;
	}
	
	public void incCount() {
		count++;
	}
	
	public void stop()
	{
		stopped = true;
	}
	
	public boolean isStopped()
	{
		return stopped;
	}
	
	public void paint(Graphics2D g)
	{
		NodeImage n = nodePath.getLast().getNodeImage();
		Ellipse2D.Double circle = new Ellipse2D.Double(n.getCenterX()-30, n.getCenterY()-30, 60, 60);
		if(!reachedDestination())
		{
			if(stopped) g.setColor(Color.YELLOW);
			else g.setColor(Color.GREEN);
		}
		else g.setColor(Color.RED);
		g.fill(circle);
		g.setColor(Color.BLACK);
		g.draw(circle);
		g.drawString(""+this.getId(), n.getCenterX()+30, n.getCenterY()-30);
	}
	
}
