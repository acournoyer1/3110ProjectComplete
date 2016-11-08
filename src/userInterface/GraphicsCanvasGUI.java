package userInterface;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

import backEnd.Connection;
import backEnd.Message;
import backEnd.Node;
import backEnd.Simulation;
import backEnd.SimulationListener;

@SuppressWarnings("serial")
public class GraphicsCanvasGUI extends JPanel implements SimulationListener{
	private ArrayList<NodeImageGUI> nodes;
	private ArrayList<Connection> connections;
	private ArrayList<Message> messages;
	private Simulation sim;
	
	public GraphicsCanvasGUI(Simulation sim)
	{
		nodes = new ArrayList<NodeImageGUI>();
		this.sim = sim;
		connections = sim.getConnections();
		sim.addListener(this);
		connections = sim.getConnections();
		messages = sim.getMessageList();
	}
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		setBackground(Color.WHITE);
		Graphics2D g2 = (Graphics2D)g;
		for(Message m: messages)
		{
			m.paint(g2);
		}
		for(Connection c: connections)
		{
			c.paint(g2);
		}
		for(NodeImageGUI n: nodes)
		{
			n.paint(g2);
		}
	}
	
	public void update()
	{
		nodes.clear();
		ArrayList<Node> nodeList = sim.getNodes();
		connections = sim.getConnections();
		messages = sim.getMessageList();
		ArrayList<Point> points = getCircle(new Point(this.getWidth()/2, this.getHeight()/2), getRadius(), nodeList.size());
		for(int i = 0; i < nodeList.size(); i++)
		{
			NodeImageGUI n = new NodeImageGUI(points.get(i), nodeList.get(i));
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
			ArrayList<PolarPointGUI> polarPoints = new ArrayList<PolarPointGUI>();
			while(currentAngle < 2*Math.PI)
			{
				polarPoints.add(new PolarPointGUI(radius, currentAngle));
				currentAngle += angleJump;
			}
			for(PolarPointGUI p: polarPoints)
			{
				Point pCartesian = p.convertToCartesian();
			    pCartesian.translate(center.x, center.y);
				points.add(pCartesian);
			}
		}
		return points;
	}
}
