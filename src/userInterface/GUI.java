package userInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import algorithms.DijkstrasAlgorithm;
import algorithms.FloodAlgorithm;
import algorithms.RandomAlgorithm;
import algorithms.ShortestPathAlgorithm;
import backEnd.Message;
import backEnd.Node;
import backEnd.Simulation;
import backEnd.SimulationListener;

@SuppressWarnings("serial")
public class GUI extends JFrame implements SimulationListener{
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
	private JMenuItem undo;
	private JMenuItem clearSim;
	private JMenuItem exportAsImage;
	private JMenuItem exportAsXML;
	private JMenuItem newTab;
	private JMenuItem open;
	private JMenuItem save;
	private JMenuItem saveAs;
	private JMenuItem closeTab;
	private JCheckBoxMenuItem randomMessages;
	private JTabbedPane tabbedPane;
	
	private JTextArea statusWindow;
	private DialogManagerGUI dialog;
	private GraphicsCanvasGUI canvas;
	private Simulation sim;
	private GuiTab currentTab;
	
	
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
		newTab = new JMenuItem("New");
		undo = new JMenuItem("Undo");
		closeTab = new JMenuItem("Close Tab");
		randomMessages = new JCheckBoxMenuItem("Generate Random Messages", true);
		tabbedPane = new JTabbedPane();
		
		GuiTab tab = new GuiTab();
		currentTab = tab;
		refresh();
		
		tab.getSimulation().addListener(this);
		tabbedPane.addTab(tab.getFileName(), tab);
		
		JMenu fileMenu = new JMenu("File");
		JMenu addMenu = new JMenu("Add");
		JMenu removeMenu = new JMenu("Remove");
		JMenu viewMenu = new JMenu("View");
		JMenu simulationMenu = new JMenu("Simulation");
		JMenu typeMenu = new JMenu("Set type");
		JMenu exportMenu = new JMenu("Export");
		jMenuBar.add(fileMenu);
		jMenuBar.add(simulationMenu);
		jMenuBar.add(addMenu);
		jMenuBar.add(removeMenu);
		jMenuBar.add(viewMenu);
		fileMenu.add(newTab);
		fileMenu.add(open);
		fileMenu.addSeparator();
		fileMenu.add(save);
		fileMenu.add(saveAs);
		fileMenu.addSeparator();
		fileMenu.add(exportMenu);
		fileMenu.addSeparator();
		fileMenu.add(closeTab);
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
		
		this.add(tabbedPane);
		
		this.setSize(1000, 800);
		currentTab.refresh();
		this.setResizable(false);
		
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setUpListeners();
		this.setVisible(true);
	}
	
	private void setUpListeners()
	{
		tabbedPane.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e) {
				currentTab = (GuiTab)tabbedPane.getSelectedComponent();
				if(currentTab != null)currentTab.refresh();
				refresh();
			}
		});
		
		undo.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0){
				sim.undo();
			}
		});
		
		newTab.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0){
				GuiTab temp = new GuiTab();
				tabbedPane.addTab(temp.getFileName(), temp);
				tabbedPane.setSelectedComponent(temp);
			}
		});
		
		closeTab.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0){
				tabbedPane.removeTabAt(tabbedPane.getSelectedIndex());
			}
		});
		
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
					currentTab.exportXML(f);
					statusWindow.append("XML exported to " + f.getAbsolutePath() + ".\n");
				}
			}
		});
		
		open.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0){
				GuiTab temp = new GuiTab();
				temp.open();
				tabbedPane.addTab(temp.getFileName(), temp);
				tabbedPane.setSelectedComponent(temp);
			}
		});
		
		save.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0){
				currentTab.saveFile();
				tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), currentTab.getFileName());
			}
		});
		
		saveAs.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0){
				currentTab.saveAsFile();
				tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), currentTab.getFileName());
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
		createTest.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				currentTab.buildTest();
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
		if(currentTab == null) return;
		statusWindow = currentTab.getStatusWindow();
		sim = currentTab.getSimulation();
		dialog = currentTab.getDialogManager();
		canvas = currentTab.getCanvas();
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
			simRate.setEnabled(true);
			simLength.setEnabled(true);
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
	
	public static void main(String args[])
	{
		new GUI();
	}
}
