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
	
	public int getCenterX()
	{
		return (int)circle.getCenterX();
	}
	
	public boolean contains(Point p)
	{
		return circle.contains(p.x, p.y);
	}
	
	public int getCenterY()
	{
		return (int)circle.getCenterY();
	}
	
	public Point getCenter()
	{
		return new Point((int)circle.getCenterX(), (int)circle.getCenterY());
	}
	
	public Ellipse2D.Double getCircle()
	{
		return circle;
	}
	
	public void setCenter(Point p)
	{
		circle.setFrame(p.getX() - 25, p.getY() - 25, 50, 50);
	}
	
	public Node getNode()
	{
		return node;
	}
	
	public void paint(Graphics2D g)
	{
		g.fill(circle);
		g.setColor(Color.BLACK);
		g.draw(circle);
		g.drawString(node.getName(), getCenterX(), getCenterY());
	}
}
