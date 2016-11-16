package userInterface;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import backEnd.Connection;
import backEnd.Message;
import backEnd.Node;
import backEnd.Simulation;
import backEnd.SimulationListener;

@SuppressWarnings("serial")
public class GraphicsCanvasGUI extends JPanel implements SimulationListener{
	private ArrayList<NodeImageGUI> nodes;
	private ArrayList<Connection> connections;
	private Shape tempShape;
	private ArrayList<Message> messages;
	private Simulation sim;
	private JTextArea statusWindow;
	private NodeImageGUI selectedNode = null;
	private CanvasState state = CanvasState.NEUTRAL;
	
	private Node tempNode;
	
	public GraphicsCanvasGUI(Simulation sim, JTextArea statusWindow)
	{
		nodes = new ArrayList<NodeImageGUI>();
		this.sim = sim;
		this.statusWindow = statusWindow;
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
				boolean found;
				switch(state)
				{
				case NEUTRAL:
					found = false;
					for(NodeImageGUI n: nodes)
					{
						if(n.contains(e.getPoint()))
						{
							selectedNode = n;
							found = true;
						}
					}
					if(!found) selectedNode = null;
					break;
					
				case ADDNODE:
					setState(CanvasState.NEUTRAL);
					sim.addNode(tempNode);
					selectedNode = null;
					break;
				case ADDCONNECTION:
					if(selectedNode == null)
					{
						found = false;
						for(NodeImageGUI n: nodes)
						{
							if(n.contains(e.getPoint()))
							{
								selectedNode = n;
								statusWindow.append("Select the second node.\n");
								found = true;
							}
						}
						if(!found)
						{
							statusWindow.append("Invalid selection, terminating the creation of the connection.\n");
							setState(CanvasState.NEUTRAL);
							selectedNode = null;
						}
					}
					else
					{
						found = false;
						NodeImageGUI temp = null;
						for(NodeImageGUI n: nodes)
						{
							if(n.contains(e.getPoint()))
							{
								temp = n;
								found = true;
							}
						}
						if(!found)
						{
							statusWindow.append("Invalid selection, terminating the creation of the connection.\n");
							setState(CanvasState.NEUTRAL);
							selectedNode = null;
						}
						else
						{
							sim.addConnection(temp.getNode(), selectedNode.getNode());
							statusWindow.append("Connection " + selectedNode.getNode().getName() + " < - > " + temp.getNode().getName() + " has been added.\n");
							tempShape = null;
							selectedNode = null;
							setState(CanvasState.NEUTRAL);
							
						}
					}
					break;
				default:
					break;
					
				}
				
				repaint();
			}
		});
		this.addMouseMotionListener(new MouseAdapter()
		{
			@Override
			public void mouseDragged(MouseEvent e)
			{
				switch(state)
				{
				case ADDCONNECTION:
					break;
				case ADDNODE:
					break;
				case NEUTRAL:
					if(selectedNode != null)
					{
						selectedNode.setCenter(e.getPoint());
						repaint();
					}
					break;
				default:
					break;
				
				}
			}
			
			@Override
			public void mouseMoved(MouseEvent e)
			{
				switch(state)
				{
				case ADDCONNECTION:
					if(selectedNode != null)
					{
						tempShape = new Line2D.Double(selectedNode.getCenter(), e.getPoint());
						repaint();
					}
					break;
				case ADDNODE:
					if(selectedNode != null)
					{
						selectedNode.setCenter(e.getPoint());
						repaint();
					}
					else
					{
						selectedNode = new NodeImageGUI(e.getPoint(), tempNode);
						nodes.add(selectedNode);
					}
					repaint();
					break;
				case NEUTRAL:
					break;
				default:
					break;
				
				}
			}
		});
	}
	
	public void setState(CanvasState s)
	{
		state = s;
	}
	
	public CanvasState getState()
	{
		return state;
	}
	
	public void setTempNode(Node n)
	{
		tempNode = n;
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
		if(tempShape != null)
		{
			g2.draw(tempShape);
		}
		for(NodeImageGUI n: nodes)
		{
			switch(state)
			{
			case NEUTRAL:
				if(n != selectedNode)g2.setColor(Color.CYAN);
				else g2.setColor(Color.ORANGE);
				break;
				
			case ADDNODE:
				if(n != selectedNode)g2.setColor(Color.CYAN);
				else g2.setColor(new Color(0,255,255,75));
				break;
				
			case ADDCONNECTION:
				g2.setColor(Color.CYAN);
				break;
			}
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
