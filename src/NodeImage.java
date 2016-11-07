import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;

public class NodeImage {
	private Ellipse2D.Double circle;
	private Node node;
	
	public NodeImage(Point p, int width, int height, Node node)
	{
		circle = new Ellipse2D.Double(p.getX() - width/2, p.getY() - height/2, width, height);
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
