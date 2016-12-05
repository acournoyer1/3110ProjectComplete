package userInterface;
/**
 * GUI class that builds all of the gui's properties
 * 
 * Author: Alex Cournoyer
 * Last Edited by: Adam Staples
 */
import java.awt.Font;
import java.awt.Point;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import backEnd.Message;
import backEnd.Node;
import backEnd.Simulation;
import backEnd.SimulationListener;
import algorithms.*;

@SuppressWarnings("serial")
public class GUI extends JFrame implements SimulationListener{
	
	private File file = null;
	
	private JMenuItem addNode;
	private JMenuItem addConnection;
	private JMenuItem addMessage;
	private JMenuItem createTest;
	private JMenuItem removeNode;
	private JMenuItem removeConnection;
	private JMenuItem viewNode;
	private JMenuItem viewAllNodes;
	private JMenuItem viewAverage;
	private JMenuItem simRate;
	private JMenuItem simLength;
	private JCheckBoxMenuItem randomType;
	private JCheckBoxMenuItem floodType;
	private JCheckBoxMenuItem shortestType;
	private JCheckBoxMenuItem dijkstrasType;
	private JMenuItem clearSim;
	private JMenuItem exportAsImage;
	private JMenuItem exportAsXML;
	private JMenuItem newSim;
	private JMenuItem open;
	private JMenuItem save;
	private JMenuItem saveAs;
	private JCheckBoxMenuItem randomMessages;
	private JTextField commandField;
	private JTextArea statusWindow;
	private JButton stepButton;
	private JButton runButton;
	private JSplitPane split;
	private JSplitPane commandSplit;
	private JSplitPane buttonSplit;
	private JSplitPane bottomSplit;
	private CommandParserGUI parser;
	private JScrollPane scrollPane;
	private Simulation sim;
	private DialogManagerGUI dialog;
	private GraphicsCanvasGUI canvas;
	
	private final Font BOLD_FONT = new Font("Dialog", Font.BOLD, 12);
	
	/**
	 *  GUI method
	 *  Instantiate all global GUI elements and builds the layout
	 * 
	 */
	public GUI()
	{	
		JMenuBar jMenuBar = new JMenuBar();
		
		addNode = new JMenuItem("Node");
		addConnection = new JMenuItem("Connection");
		addMessage = new JMenuItem("Message");
		createTest = new JMenuItem("Create Test Network");
		removeNode = new JMenuItem("Node");
		removeConnection = new JMenuItem("Connection");
		viewNode = new JMenuItem("Node");
		viewAllNodes = new JMenuItem("All Nodes");
		viewAverage = new JMenuItem("Average");
		statusWindow = new JTextArea();
		stepButton = new JButton("Step");
		runButton = new JButton("Run");
		
		randomType = new JCheckBoxMenuItem("Random", true);
		floodType = new JCheckBoxMenuItem("Flood");
		shortestType = new JCheckBoxMenuItem("Shortest Path");
		dijkstrasType = new JCheckBoxMenuItem("Dijkstra's Algorithm");
		
		ButtonGroup g = new ButtonGroup();
		g.add(randomType);
		g.add(floodType);
		g.add(shortestType);
		g.add(dijkstrasType);
		
		simRate = new JMenuItem("Set Rate");
		simLength = new JMenuItem("Set Length");
		clearSim = new JMenuItem("Clear Simulation");
		exportAsImage = new JMenuItem("Image");
		exportAsXML = new JMenuItem("XML");
		open = new JMenuItem("Open");
		save = new JMenuItem("Save");
		saveAs = new JMenuItem("Save As");
		newSim = new JMenuItem("New");
		randomMessages = new JCheckBoxMenuItem("Generate Random Messages", true);
		
		commandField = new JTextField("");
		commandField.setFont(BOLD_FONT);
		commandField.setEditable(true);
		commandSplit = new JSplitPane();
		bottomSplit = new JSplitPane();
		sim = new Simulation(statusWindow);
		sim.addListener(this);
		canvas = new GraphicsCanvasGUI(sim, statusWindow);
		dialog = new DialogManagerGUI(sim, statusWindow, canvas);
		parser = new CommandParserGUI(this);
		refresh();
		
		JMenu fileMenu = new JMenu("File");
		JMenu addMenu = new JMenu("Add");
		JMenu removeMenu = new JMenu("Remove");
		JMenu viewMenu = new JMenu("View");
		JMenu simulationMenu = new JMenu("Simulation");
		JMenu typeMenu = new JMenu("Set type");
		JMenu exportMenu = new JMenu("Export");
		jMenuBar.add(fileMenu);
		jMenuBar.add(addMenu);
		jMenuBar.add(removeMenu);
		jMenuBar.add(viewMenu);
		jMenuBar.add(simulationMenu);
		fileMenu.add(newSim);
		fileMenu.add(open);
		fileMenu.addSeparator();
		fileMenu.add(save);
		fileMenu.add(saveAs);
		fileMenu.addSeparator();
		fileMenu.add(exportMenu);
		fileMenu.addSeparator();
		fileMenu.add(clearSim);
		exportMenu.add(exportAsImage);
		exportMenu.add(exportAsXML);
		addMenu.add(addNode);
		addMenu.add(addConnection);
		addMenu.add(addMessage);
		addMenu.addSeparator();
		addMenu.add(createTest);
		removeMenu.add(removeNode);
		removeMenu.add(removeConnection);
		viewMenu.add(viewNode);
		viewMenu.add(viewAverage);
		simulationMenu.add(typeMenu);
		simulationMenu.add(randomMessages);
		simulationMenu.add(simRate);
		simulationMenu.add(simLength);
		typeMenu.add(randomType);
		typeMenu.add(floodType);
		typeMenu.add(shortestType);
		typeMenu.add(dijkstrasType);
		this.setJMenuBar(jMenuBar);
		
		buttonSplit = new JSplitPane();
		buttonSplit.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		buttonSplit.setDividerSize(1);
		buttonSplit.setLeftComponent(stepButton);
		buttonSplit.setRightComponent(runButton);
		
		scrollPane = new JScrollPane(statusWindow);
		scrollPane.setHorizontalScrollBar(new JScrollBar(JScrollBar.HORIZONTAL));
	    
	    commandSplit.setOrientation(JSplitPane.VERTICAL_SPLIT);
	    commandSplit.setTopComponent(commandField);
	    commandSplit.setBottomComponent(scrollPane);
	    commandSplit.setDividerSize(5);
	    commandSplit.setEnabled(false);
	    
	    bottomSplit.setOrientation(JSplitPane.VERTICAL_SPLIT);
	    bottomSplit.setTopComponent(canvas);
	    bottomSplit.setBottomComponent(buttonSplit);
	    bottomSplit.setDividerSize(1);
	    bottomSplit.setEnabled(false);
	    
	    split = new JSplitPane();
		split.setOrientation(JSplitPane.VERTICAL_SPLIT);
		split.setTopComponent(commandSplit);
		split.setBottomComponent(bottomSplit);
		split.setDividerSize(1);
		split.setEnabled(false);
	 
		statusWindow.setEditable(false);
		this.add(split);
		this.setSize(1000, 650);
		this.setResizable(false);
		commandSplit.setDividerLocation(30);
		split.setDividerLocation((int)(GUI.this.getHeight()*0.25));
		buttonSplit.setDividerLocation((int)(this.getWidth()*0.50));
		bottomSplit.setDividerLocation((int)(this.getHeight()*0.60));
		this.setTitle("Network Simulation");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setUpListeners();
		this.setVisible(true);
	}
	
	/**
	 *  Add all global listeners in the method below 
	 *  i.e anything that is in the main GUI window
	 *  
	 */
	private void setUpListeners()
	{
		exportAsImage.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0){
				new ExportImageGUI(canvas);
			}
		});
		
		exportAsXML.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				File f = XMLFileChooser.exportXML();
				if(f != null)
				{
					exportXML(f);
					statusWindow.append("XML exported to " + f.getAbsolutePath() + ".\n");
				}
			}
		});
		
		open.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0){
				try {
					File f = XMLFileChooser.importXML();
					if(f != null)
					{
						importXML(f);
						statusWindow.setText(f.getAbsolutePath() + " opened.\n");
					}
				} catch (ParserConfigurationException | SAXException | IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		save.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0){
				if(file == null)
				{
					saveAs.doClick();
				}
				else
				{
					exportXML(file);
					statusWindow.append("File saved.\n");
				}				
			}
		});
		
		saveAs.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0){
				File f = XMLFileChooser.exportXML();
				if(f != null)
				{
					file = f;
					exportXML(file);
					statusWindow.append("File saved to " + file.getAbsolutePath() + ".\n");
				}
			}
		});
				
		addNode.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				dialog.open(DialogManagerGUI.ADD_NODE);
			}
		});
		
		addConnection.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				dialog.open(DialogManagerGUI.ADD_CONNECTION);
			}
		});
		
		addMessage.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				dialog.open(DialogManagerGUI.ADD_MESSAGE);
			}
		});
		removeNode.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				dialog.open(DialogManagerGUI.REMOVE_NODE);
			}
		});
		removeConnection.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				dialog.open(DialogManagerGUI.REMOVE_CONNECTION);
			}
		});
		viewNode.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				dialog.open(DialogManagerGUI.VIEW_NODE);
			}
		});
		commandField.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e) 
			{
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					parser.parse(commandField.getText());
					commandField.setText("");
				}
			}	
		});
		stepButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				canvas.clearSelection();
				sim.step();
			}
		});
		runButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				canvas.clearSelection();
				try {
					sim.run();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		createTest.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				buildTest();
				statusWindow.append("Test Network has been created.\n");
			}
		});
		viewAverage.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				statusWindow.append("The average number of jumps messages have taken is " + sim.average() + ".\n");
			}
		});
		clearSim.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				statusWindow.setText("");
				sim.clear();
				Message.reset();
				statusWindow.append("Simulation cleared.\n");
			}
		});
		viewAllNodes.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				for(Node n: sim.getNodes())
				{
					statusWindow.append(n.getDetails() + "\n");
				}
			}
		});
		randomType.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				sim.setType(new RandomAlgorithm(sim));
				statusWindow.append("Simulation Type set to Random.\n");
			}
		});
		floodType.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				sim.setType(new FloodAlgorithm(sim));
				statusWindow.append("Simulation Type set to Flood.\n");
			}
		});
		shortestType.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				sim.setType(new ShortestPathAlgorithm(sim));
				statusWindow.append("Simulation Type set to Shortest Path.\n");
			}
		});
		dijkstrasType.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				sim.setType(new DijkstrasAlgorithm(sim));
				statusWindow.append("Simulation Type set to Dijkstra's Algorithm.\n");
			}
		});
		randomMessages.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				if(randomMessages.isSelected())
				{
					sim.setRandomMessage(true);
					statusWindow.append("Random messages will be generated for the simulation.\n");
				}
				else
				{
					sim.setRandomMessage(false);
					statusWindow.append("Random messages will not be generated for the simulation.\n");
				}
			}
		});
		simRate.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				dialog.open(DialogManagerGUI.SET_RATE);
			}
		});
		simLength.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				dialog.open(DialogManagerGUI.SET_LENGTH);
			}
		});
	}
	
	/** 
	 *  update method
	 *  Call this class to refresh the GUI
	 * 
	 */
	public void update()
	{
		refresh();
	}
	
	/** 
	 *  refresh method
	 *  Call this class after appending to the statusWindow to re-evaluate.
	 *  This class will then enable and disable GUI elements depending on current parameters
	 * 
	 */
	private void refresh()
	{
		if(sim.getNodes().size() < 2)
		{
			addConnection.setEnabled(false);
		}
		else
		{
			addConnection.setEnabled(true);
		}
		if(sim.getNodes().size() == 0)
		{
			removeNode.setEnabled(false);
			viewNode.setEnabled(false);
		}
		else
		{
			removeNode.setEnabled(true);
			viewNode.setEnabled(true);
		}
		if(sim.getConnections().size() == 0)
		{
			removeConnection.setEnabled(false);
			addMessage.setEnabled(false);
		}
		else
		{
			removeConnection.setEnabled(true);
			if(!randomMessages.isSelected())addMessage.setEnabled(true);
		}
		if(randomMessages.isSelected())
		{
			stepButton.setEnabled(false);
			simRate.setEnabled(true);
			simLength.setEnabled(true);
			if(sim.getConnections().size() == 0)
			{
				runButton.setEnabled(false);
			}
			else
			{
				runButton.setEnabled(true);
			}
		}
		else
		{
			simRate.setEnabled(false);
			simLength.setEnabled(false);
			if(sim.getMessageListSize() == 0)
			{
				stepButton.setEnabled(false);
				runButton.setEnabled(false);
			}
			else
			{
				stepButton.setEnabled(true);
				runButton.setEnabled(true);
			}
		}
		if(sim.getMessageJumpSize() == 0)
		{
			viewAverage.setEnabled(false);
		}
		else
		{
			viewAverage.setEnabled(true);
		}
	}
	
	/*
	 *  Builds a test simulation with pre-determined nodes and connections
	 * 
	 */
	public void buildTest()
	{
		sim.setIgnore(true);
		sim.clear();
		Node a = new Node("A");
		canvas.addNodeImage(new NodeImageGUI(new Point(300, 90), a));
		Node b = new Node("B");
		canvas.addNodeImage(new NodeImageGUI(new Point(620, 100), b));
		Node c = new Node("C");
		canvas.addNodeImage(new NodeImageGUI(new Point(350, 270), c));
		Node d = new Node("D");
		canvas.addNodeImage(new NodeImageGUI(new Point(570, 300), d));
		Node e = new Node("E");
		canvas.addNodeImage(new NodeImageGUI(new Point(700, 200), e));
		
		sim.addNode(a);
		sim.addNode(b);
		sim.addNode(c);
		sim.addNode(d);
		sim.addNode(e);
		
		sim.addConnection(a, b);
		sim.addConnection(a, c);
		sim.addConnection(a, e);
		sim.addConnection(c, d);
		sim.addConnection(d, b);
		sim.addConnection(b, e);
		sim.setIgnore(false);
		sim.update();
	}
	
	public JTextArea getStatusWindow()
	{
		return statusWindow;
	}
	
	public DialogManagerGUI getDialogManager()
	{
		return dialog;
	}
	
	public GraphicsCanvasGUI getCanvas()
	{
		return canvas;
	}
	
	public void setCanvas(GraphicsCanvasGUI canvas)
	{
		this.canvas = canvas;
	}
	
	public Simulation getSimulation()
	{
		return sim;
	}
	
	public void setSimulation(Simulation sim)
	{
		this.sim = sim;
	}
	
	public String toXML()
	{
		return "<Network>\n" + sim.toXML() + canvas.toXML() + "</Network>\n";
	}
	
	public void exportXML(File f)
	{
		try {
			FileWriter fw = new FileWriter(f);
			fw.write(this.toXML());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void importXML(File f) throws ParserConfigurationException, SAXException, IOException
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder d = factory.newDocumentBuilder();
		Document doc = d.parse(f);
		doc.getDocumentElement().normalize();
		
		Simulation s = new Simulation(statusWindow);
		GraphicsCanvasGUI c = new GraphicsCanvasGUI(s, statusWindow);
		
		NodeList nodes = doc.getElementsByTagName("Node");
		for(int i = 0; i < nodes.getLength(); i++)
		{
			org.w3c.dom.Node n = nodes.item(i);
			if(n.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE)
			{
				Element element = (Element)n;
				String name = element.getElementsByTagName("name").item(0).getTextContent();
				s.addNode(new Node(name));
			}
		}
		NodeList connections = doc.getElementsByTagName("Connection");
		for(int i = 0; i < connections.getLength(); i++)
		{
			org.w3c.dom.Node n = connections.item(i);
			if(n.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE)
			{
				Element element = (Element)n;
				String firstNodeName = element.getElementsByTagName("firstNode").item(0).getTextContent();
				String secondNodeName = element.getElementsByTagName("secondNode").item(0).getTextContent();
				Node firstNode = s.getNodeByName(firstNodeName);
				Node secondNode = s.getNodeByName(secondNodeName);
				if(firstNode != null && secondNode != null)
				{
					s.addConnection(firstNode, secondNode);
				}
			}
		}
		NodeList images = doc.getElementsByTagName("NodeImage");
		for(int i = 0; i < images.getLength(); i++)
		{
			org.w3c.dom.Node n = images.item(i);
			if(n.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE)
			{
				Element element = (Element)n;
				int x = Integer.parseInt(element.getElementsByTagName("x").item(0).getTextContent());
				int y = Integer.parseInt(element.getElementsByTagName("y").item(0).getTextContent());
				String name = element.getElementsByTagName("name").item(0).getTextContent();
				Node node = s.getNodeByName(name);
				if(node != null)
				{
					c.addNodeImage(new NodeImageGUI(new Point(x,y), node));
				}
			}
		}
		s.addListener(this);
		sim = s;
		canvas = c;
		dialog = new DialogManagerGUI(sim, statusWindow, canvas);
		parser = new CommandParserGUI(this);
		bottomSplit.setTopComponent(c);
		bottomSplit.setDividerLocation((int)(this.getHeight()*0.60));
	}
	
	public static void main(String args[])
	{
		new GUI();
	}
}
