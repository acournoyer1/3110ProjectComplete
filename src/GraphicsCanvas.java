import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GraphicsCanvas extends JPanel implements SimulationListener{
	private ArrayList<NodeImage> nodes;
	private ArrayList<Connection> connections;
	private Simulation sim;
	
	public GraphicsCanvas(Simulation sim)
	{
		nodes = new ArrayList<NodeImage>();
		this.sim = sim;
		connections = sim.getConnections();
		sim.addListener(this);
	}
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		setBackground(Color.WHITE);
		Graphics2D g2 = (Graphics2D)g;
		for(Connection c: connections)
		{
			c.paint(g2);
		}
		for(NodeImage n: nodes)
		{
			n.paint(g2);
		}
	}
	
	public void update()
	{
		nodes.clear();
		ArrayList<Node> nodeList = sim.getNodes();
		connections = sim.getConnections();
		ArrayList<Point> points = getCircle(new Point(this.getWidth()/2, this.getHeight()/2), getRadius(), nodeList.size());
		for(int i = 0; i < nodeList.size(); i++)
		{
			NodeImage n = new NodeImage(points.get(i), 50, 50, nodeList.get(i));
			nodeList.get(i).setNodeImage(n);
			nodes.add(n);
		}
		repaint();
	}
	
	private int getRadius()
	{
		return this.getWidth() < this.getWidth() ? (int)(this.getWidth()*0.40):(int)(this.getHeight()*0.40); 
	}
	
	private ArrayList<Point> getCircle(Point center, int radius, int n)
	{
		ArrayList<Point> points = new ArrayList<Point>();
		if(n == 1)
		{
			points.add(center);
		}
		else
		{
			double angleJump = (2*Math.PI)/n;
			double currentAngle = 0;
			ArrayList<PolarPoint> polarPoints = new ArrayList<PolarPoint>();
			while(currentAngle < 2*Math.PI)
			{
				polarPoints.add(new PolarPoint(radius, currentAngle));
				currentAngle += angleJump;
			}
			for(PolarPoint p: polarPoints)
			{
				Point pCartesian = p.convertToCartesian();
			    pCartesian.translate(center.x, center.y);
				points.add(pCartesian);
			}
		}
		return points;
	}
}
