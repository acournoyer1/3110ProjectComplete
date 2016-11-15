package userInterface;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
	private NodeImageGUI selectedNode = null;
	private CanvasState state = CanvasState.NEUTRAL;
	
	public GraphicsCanvasGUI(Simulation sim)
	{
		nodes = new ArrayList<NodeImageGUI>();
		this.sim = sim;
		connections = sim.getConnections();
		sim.addListener(this);
		connections = sim.getConnections();
		messages = sim.getMessageList();
		this.setUpListeners();
		this.setEnabled(true);
	}
	
	private void setUpListeners()
	{
		this.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				boolean found = false;
				for(NodeImageGUI n: nodes)
				{
					if(n.contains(e.getPoint()))
					{
						selectedNode = n;
						found = true;
					}
				}
				if(!found) selectedNode = null;
				repaint();
			}
		});
		this.addMouseMotionListener(new MouseAdapter()
		{
			@Override
			public void mouseDragged(MouseEvent e)
			{
				if(selectedNode != null)
				{
					selectedNode.setCenter(e.getPoint());
					repaint();
				}
			}
		});
	}
	
	public void setState(CanvasState s)
	{
		state = s;
	}
	
	public void clearSelection()
	{
		selectedNode = null;
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
			if(n != selectedNode)g.setColor(Color.CYAN);
			else g.setColor(Color.ORANGE);
			n.paint(g2);
		}
	}
	
	public void update()
	{
		nodes.clear();
		ArrayList<Node> nodeList = sim.getNodes();
		connections = sim.getConnections();
		messages = sim.getMessageList();
		for(Node n: nodeList)
		{
			nodes.add(n.getNodeImage());
		}
		repaint();
	}
}
