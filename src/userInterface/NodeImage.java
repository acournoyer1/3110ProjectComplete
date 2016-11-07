package userInterface;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;

import backEnd.Node;

public class NodeImage {
	private Ellipse2D.Double circle;
	private Node node;
	
	public NodeImage(Point p, Node node)
	{
		circle = new Ellipse2D.Double(p.getX() - 25, p.getY() - 25, 50, 50);
		this.node = node;
	}
	
	public int getCenterX()
	{
		return (int)circle.getCenterX();
	}
	
	public int getCenterY()
	{
		return (int)circle.getCenterY();
	}
	
	public Ellipse2D.Double getCircle()
	{
		return circle;
	}
	
	public void paint(Graphics2D g)
	{
		g.setColor(Color.CYAN);
		g.fill(circle);
		g.setColor(Color.BLACK);
		g.draw(circle);
		g.drawString(node.getName(), getCenterX(), getCenterY());
	}
}
