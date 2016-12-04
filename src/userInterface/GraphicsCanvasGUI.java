package userInterface;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
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
	
	/**
	 *  Add all global listeners in the method below 
	 *  i.e anything that is in the main GUI window
	 *  
	 */
	private void setUpListeners()
	{
		this.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				switch(state)
				{
				case NEUTRAL:
					selectedNode = findNode(e.getPoint());
					if(selectedNode != null && (int)e.getButton() == (int)MouseEvent.BUTTON3)
					{
						createPopup(selectedNode.getNode()).show(e.getComponent(), e.getPoint().x, e.getPoint().y);
					}
					break;
					
				case ADDNODE:
					setState(CanvasState.NEUTRAL);
					sim.addNode(tempNode);
					selectedNode = null;
					break;
				case ADDCONNECTION:
					if(selectedNode == null)
					{
						selectedNode = findNode(e.getPoint());
						if(selectedNode != null) statusWindow.append("Select the second node.\n");
						else
						{
							statusWindow.append("Invalid selection, terminating the creation of the connection.\n");
							setState(CanvasState.NEUTRAL);
						}
					}
					else
					{
						NodeImageGUI temp = findNode(e.getPoint());
						if(temp == null)
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
					if(selectedNode != null && ((int)e.getButton() == (int)MouseEvent.BUTTON1 || (int)e.getButton() == (int)MouseEvent.NOBUTTON))
					{
						int x = e.getPoint().x;
						int y = e.getPoint().y;
						if(x < 0) x = 0;
						else if(x > getWidth()) x = getWidth();
						if(y < 0) y = 0;
						else if(y > getHeight()) x = getHeight();
						selectedNode.setCenter(new Point(x,y));
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
	
	/**
	 * createPopup method:
	 * Creates a popup menu to remove a node and its connections
	 * 
	 * @param node to be removed
	 * @return a menu with the remaining nodes
	 */
	public JPopupMenu createPopup(Node node)
	{
		JPopupMenu menu = new JPopupMenu();
		JMenuItem removeNode = new JMenuItem("Remove Node");
		removeNode.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				sim.removeNode(node);
				statusWindow.append("Node " + node.getName() + " has been removed.\n");
			}
		});
		menu.add(removeNode);
		for(Node n: node.getConnections())
		{
			JMenuItem temp = new JMenuItem("Remove Connection to " + n.getName());
			temp.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					sim.removeConnection(node, n);
					statusWindow.append("Connection " + node.getName() + " < - > " + n.getName() + " has been removed.\n");
				}
			});
			menu.add(temp);
		}
		return menu;
	}
	
	/**
	 * findNode method:
	 * finds if a node is contained in the GUI of the node tree
	 * 
	 * @param The X,Y coordinates of the node
	 * @return The nodeImageGUI if found
	 */
	public NodeImageGUI findNode(Point p)
	{
		for(NodeImageGUI n: nodes)
		{
			if(n.contains(p))
			{
				return n;
			}
		}
		return null;
	}
	
	/**
	 * setState method:
	 * Sets the state of the canvas
	 * 
	 * @param the canvaseState to be set
	 */
	public void setState(CanvasState s)
	{
		state = s;
	}
	
	/**
	 * getState method:
	 * Gets the current state of the canvas
	 * 
	 * @return the current canvasState
	 */
	public CanvasState getState()
	{
		return state;
	}
	
	/**
	 * setTempNode method:
	 * Creates a temp node
	 * 
	 * @param the node to be set
	 */
	public void setTempNode(Node n)
	{
		tempNode = n;
	}
	
	/**
	 * getTempNode method:
	 * Gets the temp node that was created
	 * 
	 * @return the tempNode 
	 */
	public Node getTempNode()
	{
		return tempNode;
	}
	
	/**
	 * setTempShape method:
	 * creates a temp shape
	 * 
	 * @param the shape to be set
	 */
	public void setTempShape(Shape s)
	{
		tempShape = s;
	}
	
	/**
	 * setSelectedNode method:
	 * sets the node to be selected 
	 * 
	 * @param the NodeImageGUI to be selected
	 */
	public void setSelectedNode(NodeImageGUI n)
	{
		selectedNode = n;
	}
	
	/**
	 * getSelectedNode method:
	 * gets the node that was selected
	 * 
	 * @return the selectedNode
	 */
	public NodeImageGUI getSelectedNode()
	{
		return selectedNode;
	}
	
	/**
	 * clearSelection method:
	 * clears the selectedNode 
	 */
	public void clearSelection()
	{
		selectedNode = null;
	}
	
	/**
	 * paint method:
	 * paints the canvas to represent the node tree and all changes made to it
	 * 
	 *  @param the graphics context
	 */
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		setBackground(Color.WHITE);
		Graphics2D g2 = (Graphics2D)g;
		for(Message m: messages)
		{
			paintMessage(m, g2);
		}
		for(Connection c: connections)
		{
			paintConnection(c, g2);
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
	
	private void paintMessage(Message m, Graphics2D g)
	{
		NodeImageGUI n = getImage(m.getPath().getLast());
		if(n == null) return;
		Ellipse2D.Double circle = new Ellipse2D.Double(n.getCenterX()-30, n.getCenterY()-30, 60, 60);
		if(!m.reachedDestination())
		{
			if(m.isStopped()) g.setColor(Color.YELLOW);
			else g.setColor(Color.GREEN);
		}
		else g.setColor(Color.RED);
		g.fill(circle);
		g.setColor(Color.BLACK);
		g.draw(circle);
		g.drawString(""+m.getId(), n.getCenterX()+30, n.getCenterY()-30);
	}
	
	private void paintConnection(Connection c, Graphics2D g)
	{
		NodeImageGUI n1 = getImage(c.getFirstNode());
		NodeImageGUI n2 = getImage(c.getSecondNode());
		if(n1 == null || n2 == null)
		{
			return;
		}
		g.drawLine(n1.getCenterX(), n1.getCenterY(), n2.getCenterX(), n2.getCenterY());
	}
	
	private NodeImageGUI getImage(Node node)
	{
		for(NodeImageGUI n: nodes)
		{
			if(n.getNode().equals(node))
			{
				return n;
			}
		}
		return null;
	}
	
	/**
	 * update method
	 *  updates the canvas using current parameters
	 *  
	 */
	public void update()
	{
		ArrayList<Node> nodeList = sim.getNodes();
		connections = sim.getConnections();
		messages = sim.getMessageList();
		ArrayList<NodeImageGUI> remove = new ArrayList<NodeImageGUI>();
		for(NodeImageGUI n: nodes)
		{
			if(!nodeList.contains(n.getNode()))
			{
				remove.add(n);
			}
		}
		nodes.removeAll(remove);
		repaint();
	}
	
	public void addNodeImage(NodeImageGUI n)
	{
		nodes.add(n);
	}
}
