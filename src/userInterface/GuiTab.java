package userInterface;
import java.awt.BorderLayout;
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

import backEnd.Node;
import backEnd.Simulation;
import backEnd.SimulationListener;

@SuppressWarnings("serial")
public class GuiTab extends JPanel implements SimulationListener{
	
	private File file = null;
	
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
	public GuiTab()
	{	
		this.setLayout(new BorderLayout());
		statusWindow = new JTextArea();
		stepButton = new JButton("Step");
		runButton = new JButton("Run");
		
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
		commandSplit.setDividerLocation(30);
		split.setDividerLocation(250);
		buttonSplit.setDividerLocation(500);
		bottomSplit.setDividerLocation(425);
		this.add(split);
		setUpListeners();
	}
	
	/**
	 *  Add all global listeners in the method below 
	 *  i.e anything that is in the main GUI window
	 *  
	 */
	private void setUpListeners()
	{
		
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

	public void refresh()
	{
		commandSplit.setDividerLocation(30);
		split.setDividerLocation(250);
		buttonSplit.setDividerLocation(500);
		bottomSplit.setDividerLocation(425);
		if(sim.getRandomMessage())
		{
			if(sim.getConnections().size() == 0)
			{
				runButton.setEnabled(false);
			}
			else
			{
				runButton.setEnabled(true);
			}
			stepButton.setEnabled(false);
		}
		else
		{
			if(sim.getMessageListSize() > 1)
			{
				runButton.setEnabled(true);
				runButton.setEnabled(true);
			}
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
		refresh();
	}
	
	public void saveFile()
	{
		if(file == null)
		{
			saveAsFile();
		}
		else
		{
			exportXML(file);
			statusWindow.append("File saved.\n");
		}
	}
	
	public void saveAsFile()
	{
		File f = XMLFileChooser.exportXML();
		if(f != null)
		{
			file = f;
			exportXML(file);
			statusWindow.append("File saved to " + file.getAbsolutePath() + ".\n");
		}
	}
	
	public void open()
	{
		try {
			File f = XMLFileChooser.importXML();
			if(f != null)
			{
				file = f;
				this.importXML(file);
				statusWindow.setText(f.getAbsolutePath() + " opened.\n");
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getFileName()
	{
		if(file == null) return "Untitled";
		else return file.getName();
	}
	
	public static void main(String args[])
	{
		new GUI();
	}
}
