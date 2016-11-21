package userInterface;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;

import backEnd.Node;

public class NodeImageGUI {
	private Ellipse2D.Double circle;
	private Node node;
	
	public NodeImageGUI(Point p, Node node)
	{
		circle = new Ellipse2D.Double(p.getX() - 25, p.getY() - 25, 50, 50);
		this.node = node;
		node.setNodeImage(this);
	}
	
	/**
	 * getCenterX method:
	 * Gets the X coordinate from the center of the nodeImageGUI
	 * 
	 * @return The X coordinate
	 */
	public int getCenterX()
	{
		return (int)circle.getCenterX();
	}
	
	/**
	 * contains method:
	 * checks if a point is contained within the nodeImageGUI
	 * 
	 * @return True if the point is within the node
	 */
	public boolean contains(Point p)
	{
		return circle.contains(p.x, p.y);
	}
	
	/**
	 * getCenterY method:
	 * Gets the Y coordinate from the center of the nodeImageGUI
	 * 
	 * @return The Y coordinate
	 */
	public int getCenterY()
	{
		return (int)circle.getCenterY();
	}
	
	/**
	 * getCenter method:
	 * returns the point where the center of the node is
	 * 
	 * @return The coordinates of the center of the node
	 */
	public Point getCenter()
	{
		return new Point((int)circle.getCenterX(), (int)circle.getCenterY());
	}
	
	/**
	 * getCircle method:
	 * Gets the GUI representation of the node
	 * 
	 * @return the circle object that represents the node
	 */
	public Ellipse2D.Double getCircle()
	{
		return circle;
	}
	
	/**
	 * setCenter method:
	 * sets the center of the NodeImageGUI
	 * 
	 * @param The point where the center should be set
	 */
	public void setCenter(Point p)
	{
		circle.setFrame(p.getX() - 25, p.getY() - 25, 50, 50);
	}
	
	/**
	 * getNode method:
	 * Gets the node being represented by the NodeImageGUI
	 * 
	 * @return The node
	 */
	public Node getNode()
	{
		return node;
	}
	
	/**
	 * paint method:
	 * paints the canvas to represent the node and all changes made to it
	 * 
	 *  @param the graphics context
	 */
	public void paint(Graphics2D g)
	{
		g.fill(circle);
		g.setColor(Color.BLACK);
		g.draw(circle);
		g.drawString(node.getName(), getCenterX(), getCenterY());
	}
}
