package userInterface;
/**
 * GUI class that builds all of the gui's properties
 * 
 * Author: Alex Cournoyer
 * Last Edited by: Adam Staples
 */
import java.awt.Font;
import java.awt.event.*;

import javax.swing.*;

import backEnd.Message;
import backEnd.Node;
import backEnd.Simulation;
import backEnd.SimulationListener;
import algorithms.*;

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
	private JMenuItem clearSim;
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
	 * GUI method
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
		parser = new CommandParserGUI(statusWindow, dialog, sim, canvas);
		refresh();
		
		JMenu fileMenu = new JMenu("File");
		JMenu addMenu = new JMenu("Add");
		JMenu removeMenu = new JMenu("Remove");
		JMenu viewMenu = new JMenu("View");
		JMenu simulationMenu = new JMenu("Simulation");
		JMenu typeMenu = new JMenu("Set type");
		jMenuBar.add(fileMenu);
		jMenuBar.add(addMenu);
		jMenuBar.add(removeMenu);
		jMenuBar.add(viewMenu);
		jMenuBar.add(simulationMenu);
		fileMenu.add(clearSim);
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
				sim.buildTest();
				statusWindow.setText("");
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
	 * refresh method
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
	
	
	public static void main(String args[])
	{
		new GUI();
	}
}
