/**
 * GUI class that builds all of the gui's properties
 * 
 * Author: Alex Cournoyer
 * Last Edited by: Daman Singh
 */
import java.awt.Font;
import java.awt.event.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class GUI extends JFrame{
	
	private JMenuItem addNode;
	private JMenuItem addConnection;
	private JMenuItem addMessage;
	private JMenuItem createTest;
	private JMenuItem removeNode;
	private JMenuItem removeConnection;
	private JMenuItem viewNode;
	private JMenuItem viewAllNodes;
	private JMenuItem viewAverage;
	private JMenuItem randomType;
	private JMenuItem clearSim;
	private JCheckBoxMenuItem viewCommand;
	private JTextField commandField;
	private JTextArea statusWindow;
	private JButton stepButton;
	private JSplitPane split;
	private JSplitPane commandSplit;
	private CommandParser parser;
	private JScrollPane scrollPane;
	private Simulation sim;
	
	private final Font BOLD_FONT = new Font("Dialog", Font.BOLD, 12);
	
	/*
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
		randomType = new JMenuItem("Random");
		clearSim = new JMenuItem("Clear Simulation");
		viewCommand = new JCheckBoxMenuItem("Command Line", false);
		viewCommand.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_DOWN_MASK));
		commandField = new JTextField();
		commandSplit = new JSplitPane();
		parser = new CommandParser();
		sim = new Simulation(statusWindow);
		
		JMenu fileMenu = new JMenu("File");
		JMenu addMenu = new JMenu("Add");
		JMenu removeMenu = new JMenu("Remove");
		JMenu viewMenu = new JMenu("View");
		JMenu simulationMenu = new JMenu("Simulation");
		JMenu typeMenu = new JMenu("Set type");
		JMenu viewToolbars = new JMenu("Toolbars");
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
		viewMenu.add(viewToolbars);
		viewToolbars.add(viewCommand);
		simulationMenu.add(typeMenu);
		typeMenu.add(randomType);
		this.setJMenuBar(jMenuBar);
		
		scrollPane = new JScrollPane(statusWindow);
		scrollPane.setHorizontalScrollBar(new JScrollBar(JScrollBar.HORIZONTAL));
		
		split = new JSplitPane();
		split.setOrientation(JSplitPane.VERTICAL_SPLIT);
		split.setTopComponent(scrollPane);
		split.setBottomComponent(stepButton);
		split.setDividerSize(1);
	    split.setEnabled(false);
	    
	    commandSplit.setOrientation(JSplitPane.VERTICAL_SPLIT);
	    commandSplit.setTopComponent(commandField);
	    commandSplit.setDividerSize(5);
	    commandSplit.setEnabled(false);
	    
		stepButton.setEnabled(false);
		removeNode.setEnabled(false);
		removeConnection.setEnabled(false);
		viewNode.setEnabled(false);
		viewAverage.setEnabled(false);
		statusWindow.setEditable(false);
		commandField.setEditable(false);
		refresh();
		this.add(split);
		this.setSize(600, 400);
		split.setDividerLocation((int)(this.getHeight()*0.75));
		this.setTitle("Network Simulation");
		this.setLocationRelativeTo(null);
		setUpListeners();
		this.setVisible(true);
	}
	
	/*
	 *  Add all global listeners in the method below 
	 *  i.e anything that is in the main GUI window
	 */
	private void setUpListeners()
	{
		addNode.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				new AddNode();
			}
		});
		
		addConnection.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				new AddConnection();
			}
		});
		
		addMessage.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				new AddMessage();
			}
		});
		removeNode.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				new RemoveNode();
			}
		});
		removeConnection.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				new RemoveConnection();
			}
		});
		viewNode.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				new ViewNode();
			}
		});
		viewCommand.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				if(viewCommand.isSelected())
				{
					commandSplit.setBottomComponent(scrollPane);
					split.setTopComponent(commandSplit);
					commandSplit.setDividerLocation(30);
					split.setDividerLocation((int)(GUI.this.getHeight()*0.75));
					commandField.setFont(BOLD_FONT);
					commandField.setText("");
					commandField.setEditable(true);
					commandField.requestFocusInWindow();
				}
				else
				{
					commandField.setEditable(false);
					split.setTopComponent(scrollPane);
					split.setDividerLocation((int)(GUI.this.getHeight()*0.75));
				}
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
				sim.step();
				refresh();
			}
		});
		createTest.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				buildTest();
				refresh();
				statusWindow.setText("");
				Message.reset();
				statusWindow.append("Test Network has been created.\n");
				//try {
					//sim.run(3, 30);
				//} catch (InterruptedException e) {
				//	e.printStackTrace();
				//}
				
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
				refresh();
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
	}
	
	/*
	 *  Builds a test simulation with pre-determined nodes and connections
	 * 
	 */
	private void buildTest()
	{
		sim.clear();
		Node a = new Node("A");
		Node b = new Node("B");
		Node c = new Node("C");
		Node d = new Node("D");
		Node e = new Node("E");
		
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
	}
	
	/*
	 *  Call this class after appending to the statusWindow to re-evaluate.
	 *  This class will then enable and disable GUI elements depending on current parameters
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
		}
		else
		{
			removeNode.setEnabled(true);
		}
		if(sim.getConnections().size() == 0)
		{
			removeConnection.setEnabled(false);
			addMessage.setEnabled(false);
		}
		else
		{
			removeConnection.setEnabled(true);
			addMessage.setEnabled(true);
		}
		if(sim.getNodes().size() == 0)
		{
			viewNode.setEnabled(false);
		}
		else
		{
			viewNode.setEnabled(true);
		}
		if(sim.getMessageListSize() == 0)
		{
			stepButton.setEnabled(false);
		}
		else
		{
			stepButton.setEnabled(true);
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
	 *  This method takes input from the built in command line 
	 *  and parses it to take appropriate action
	 */
	private class CommandParser
	{
		public void parse(String s)
		{
			String[] words = s.split(" ");
			if (words.length == 0){}
			else if(words.length == 1)
			{
				if(words[0].equalsIgnoreCase("help"))
				{
					statusWindow.append("The commands in this program are:\n"
							+ "\tHELP\n"
							+ "\t\tShows all available commands.\n"
							+ "\tADD NODE\n"
							+ "\t\tOpens the window to add nodes.\n"
							+ "\tADD NODE name\n"
							+ "\t\tAdds a new node with the provided name.\n"
							+ "\tADD CONNECTION\n"
							+ "\t\tOpens the window to add connections.\n"
							+ "\tADD CONNECTION node1 node2\n"
							+ "\t\tAdds a connection between the two named nodes.\n"
							+ "\tADD MESSAGE\n"
							+ "\t\tOpens the window to add messages.\n"
							+ "\tADD MESSAGE source destination\n"
							+ "\t\tCreates a new message that will travel between the source and destination nodes.\n"
							+ "\tREMOVE NODE\n"
							+ "\t\tOpens the window to remove nodes.\n"
							+ "\tREMOVE NODE node\n"
							+ "\t\tRemoves the named node and all its connections\n"
							+ "\tREMOVE CONNECTION\n"
							+ "\t\tOpens the window to remove connections.\n"
							+ "\tREMOVE CONNECTION node1 node2\n"
							+ "\t\tRemoves the connection between the two named nodes.\n"
							+ "\tVIEW NODE\n"
							+ "\t\tDisplays all information about a node.\n"
							+ "\tVIEW ALL\n"
							+ "\t\tDisplay information about all nodes.\n"
							+ "\tCLEAR\n"
							+ "\t\tClears the simulation."
							+ "\tAVERAGE\n"
							+ "\t\tDisplays the average number of jumps a message had to take before reaching it's destination.\n"
							+ "*** All commands are not case sensitive ***\n");
				}
				else if(words[0].equalsIgnoreCase("average"))
				{
					statusWindow.append("The average number of jumps messages have taken is " + sim.average() + ".\n");
				}
				else if(words[0].equalsIgnoreCase("test"))
				{
					buildTest();
					refresh();
					statusWindow.append("The test network has been built.\n");
				}
				else if(words[0].equalsIgnoreCase("clear"))
				{
					sim.clear();
					refresh();
					statusWindow.setText("");
					statusWindow.append("Simulation cleared.\n");
				}
				else
				{
					statusWindow.append("Invalid comand, for all commands type 'help'.\n");
				}
			}
			else if(words[0].equalsIgnoreCase("add"))
			{
				if(words[1].equalsIgnoreCase("node"))
				{
					if(words.length == 2)
					{
						new AddNode();
					}
					else if(words.length != 3)
					{
						statusWindow.append("The add node command must be followed by either no words or 1 word, the name of the node.\n");
					}
					else
					{
						Node n = sim.getNodeByName(words[2]);
						if(n == null)
						{
							sim.addNode(new Node(words[2]));
							statusWindow.append("Node " + words[2] + " has been added.\n");
							refresh();
						}
						else
						{
							statusWindow.append("A node with this name already exists.\n");
						}
					}
				}
				else if(words[1].equalsIgnoreCase("connection"))
				{
					if(words.length == 2)
					{
						new AddConnection();
					}
					else if(words.length != 4)
					{
						statusWindow.append("The add connection command must be followed by either no words or 2 words, the names of the nodes the connection connects.\n");
					}
					else
					{
						Node n1 = sim.getNodeByName(words[2]);
						Node n2 = sim.getNodeByName(words[3]);
						if(n1 == null || n2 == null)
						{
							if(n1 == null) statusWindow.append("The first node named does not exist.\n");
							if(n2 == null) statusWindow.append("The second node named does not exist.\n");
						}
						else
						{
							sim.addConnection(n1, n2);
							refresh();
							statusWindow.append("Connection " + words[2] + " < - > " + words[3] + " has been added.\n");
						}
					}
				}
				else if(words[1].equalsIgnoreCase("message"))
				{
					if(words.length == 2)
					{
						new AddMessage();
					}
					else if(words.length != 4)
					{
						statusWindow.append("The add message command must be followed by either no words or 2 words, the names of the source and destination nodes.\n");
					}
					else
					{
						Node n1 = sim.getNodeByName(words[2]);
						Node n2 = sim.getNodeByName(words[3]);
						if(n1 == null || n2 == null)
						{
							if(n1 == null) statusWindow.append("The first node named does not exist.\n");
							if(n2 == null) statusWindow.append("The second node named does not exist.\n");
						}
						else
						{
							Message msg = new Message(n1, n2);
							statusWindow.append("Message " + msg.getId() + " : " + words[2] + " - > " + words[3] + " has been added.\n");
							sim.addMsg(msg);
							refresh();
						}
					}
				}
				else
				{
					statusWindow.append("Invalid comand, for all commands type 'help'.\n");
				}
			}
			else if(words[0].equalsIgnoreCase("remove"))
			{
				if(words[1].equalsIgnoreCase("node"))
				{
					if(words.length == 2)
					{
						new RemoveNode();
					}
					else if(words.length != 3)
					{
						statusWindow.append("The remove node command must be followed by either no words or 1 word, the name of the node.\n");
					}
					else
					{
						Node n = sim.getNodeByName(words[2]);
						if(n == null) statusWindow.append("The node named does not exist.\n");
						else
						{
							sim.removeNode(n);
							refresh();
							statusWindow.append("Node " + words[2] + " has been removed.\n");
						}
					}
				}
				else if(words[1].equalsIgnoreCase("connection"))
				{
					if(words.length == 2)
					{
						new RemoveConnection();
					}
					else if(words.length != 4)
					{
						statusWindow.append("The add connection command must be followed by either no words or 2 words, the names of the nodes the connection connects.\n");
					}
					else
					{
						Node n1 = sim.getNodeByName(words[2]);
						Node n2 = sim.getNodeByName(words[3]);
						if(n1 == null || n2 == null)
						{
							if(n1 == null) statusWindow.append("The first node named does not exist.\n");
							if(n2 == null) statusWindow.append("The second node named does not exist.\n");
						}
						else
						{
							if(sim.removeConnection(n1, n2))
							{
								statusWindow.append("Connection " + words[2] + " < - > " + words[3] + " has been removed.\n");
							}
							else
							{
								statusWindow.append("The specified connection does not exist.\n");
							}
							refresh();
						}
					}
				}
				else
				{
					statusWindow.append("Invalid comand, for all commands type 'help'.\n");
				}
			}
			else if(words[0].equalsIgnoreCase("view"))
			{
				if(words[1].equalsIgnoreCase("node"))
				{
					if(words.length == 2)
					{
						new ViewNode();
					}
					else if(words.length != 3)
					{
						statusWindow.append("The view node command must be followed by either no words or 1 word, the name of the node.\n");
					}
					else
					{
						Node n = sim.getNodeByName(words[2]);
						if(n == null) statusWindow.append("The node named does not exist.\n");
						else
						{
							statusWindow.append(n.getDetails() + "\n");
						}
					}
				}
				else if(words[1].equalsIgnoreCase("all"))
				{
					if(words.length != 2)
					{
						statusWindow.append("The view all command should not be followed by more parameters.\n");
					}
					else
					{
						for(Node n: sim.getNodes())
						{
							statusWindow.append(n.getDetails() + "\n");
						}
					}
				}
			}
		}
	}
	
	/*
	 *  Nested class that builds the GUI for the addNode button
	 *  and sets up listeners for addNode GUI layout
	 */
	private class AddNode extends JFrame
	{
		private JTextField nameField;
		private JButton addButton;
		private JButton cancelButton;
		
		public AddNode()
		{
			JLabel label = new JLabel("Name: ");
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			JSplitPane split = new JSplitPane();
			JSplitPane top = new JSplitPane();
			JPanel bottom = new JPanel();
			nameField = new JTextField();
			addButton = new JButton("Add");
			cancelButton = new JButton("Cancel");
			bottom.add(addButton);
			bottom.add(cancelButton);
			split.setOrientation(JSplitPane.VERTICAL_SPLIT);
			split.setTopComponent(top);
			split.setBottomComponent(bottom);
			split.setEnabled(false);
			split.setDividerSize(1);
			top.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
			top.setLeftComponent(label);
			top.setRightComponent(nameField);
			top.setEnabled(false);
			top.setDividerSize(1);
			this.add(split);
			this.setSize(200,100);
			this.setResizable(false);
			this.setTitle("Add Node");
			split.setDividerLocation((int)(this.getHeight()*0.35));
			top.setDividerLocation((int)(this.getWidth()*0.35));
			this.setLocationRelativeTo(null);
			setUpListeners();
			addButton.setEnabled(false);
			this.setVisible(true);
		}
		
		private void setUpListeners()
		{
			cancelButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					dispose();	
				}			
			});
			addButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					Node n = sim.getNodeByName(nameField.getText());
					if(n == null)
					{
						sim.addNode(new Node(nameField.getText()));
						statusWindow.append("Node " + nameField.getText() + " has been added.\n");
					}
					else
					{
						statusWindow.append("A node with this name already exists.\n");
					}
					refresh();
					dispose();
				}		
			});
			nameField.addKeyListener(new KeyAdapter()
			{
				public void keyPressed(KeyEvent e)
				{
					if(!(e.getKeyCode() == KeyEvent.VK_ENTER))
					{
						addButton.setEnabled(true);
					}
					else if(e.getKeyCode() == KeyEvent.VK_ENTER)
					{
						addButton.doClick();
					}
				}
			});
		}
	}
	
	/*
	 *  Nested class that builds the GUI for the addConnection button
	 *  and sets up listeners for addConnection GUI layout
	 */
	private class AddConnection extends JFrame
	{
		private JComboBox<Node> firstNode;
		private JComboBox<Node> secondNode;
		private JButton addButton;
		private JButton cancelButton;
		
		public AddConnection()
		{
			firstNode = new JComboBox<Node>(sim.getNodes().toArray(new Node[sim.getNodes().size()]));
			secondNode = new JComboBox<Node>(sim.getNodes().toArray(new Node[sim.getNodes().size()]));
			addButton = new JButton("Add");
			cancelButton = new JButton("Cancel");
			
			this.setTitle("Add Connection");
			JPanel top = new JPanel();
			top.add(firstNode);
			top.add(secondNode);
			JPanel bottom = new JPanel();
			bottom.add(addButton);
			bottom.add(cancelButton);
			JSplitPane split = new JSplitPane();
			split.setOrientation(JSplitPane.VERTICAL_SPLIT);
			split.setTopComponent(top);
			split.setBottomComponent(bottom);
			split.setDividerSize(1);
			this.setSize(200, 100);
			this.setResizable(false);
			split.setDividerLocation((int)(this.getHeight()*0.35));
			this.add(split);
			this.setLocationRelativeTo(null);
			setUpListeners();
			this.setVisible(true);
		}
		
		public void setUpListeners()
		{
			cancelButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					dispose();
				}
			});
			addButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					sim.addConnection((Node)firstNode.getSelectedItem(), (Node)secondNode.getSelectedItem());
					statusWindow.append("Connection " + ((Node)firstNode.getSelectedItem()).getName() + " < - > " + ((Node)secondNode.getSelectedItem()).getName() + " has been added.\n");
					refresh();
					dispose();
				}		
			});
		}
	}
	
	/*
	 *  Nested class that builds the GUI for the addMessage button
	 *  and sets up listeners for addMessage GUI layout
	 */
	private class AddMessage extends JFrame
	{
		private JComboBox<Node> source;
		private JComboBox<Node> destination;
		private JButton addButton;
		private JButton cancelButton;
		
		public AddMessage()
		{
			source = new JComboBox<Node>(sim.getNodes().toArray(new Node[sim.getNodes().size()]));
			destination = new JComboBox<Node>(sim.getNodes().toArray(new Node[sim.getNodes().size()]));
			addButton = new JButton("Add");
			cancelButton = new JButton("Cancel");
			
			this.setTitle("Add Connection");
			JPanel top = new JPanel();
			top.add(new JLabel("Source: "));
			top.add(source);
			top.add(new JLabel("Destination: "));
			top.add(destination);
			JPanel bottom = new JPanel();
			bottom.add(addButton);
			bottom.add(cancelButton);
			JSplitPane split = new JSplitPane();
			split.setOrientation(JSplitPane.VERTICAL_SPLIT);
			split.setTopComponent(top);
			split.setBottomComponent(bottom);
			split.setDividerSize(1);
			this.setSize(300, 100);
			this.setResizable(false);
			split.setDividerLocation((int)(this.getHeight()*0.35));
			this.add(split);
			this.setLocationRelativeTo(null);
			setUpListeners();
			this.setVisible(true);
		}
		
		public void setUpListeners()
		{
			cancelButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					dispose();
				}
			});
			addButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{	
					Message msg = new Message((Node)source.getSelectedItem(), (Node)destination.getSelectedItem());
					sim.addMsg(msg);
					statusWindow.append("Message " + msg.getId() + ": " + ((Node)source.getSelectedItem()).getName() + " - > " + ((Node)destination.getSelectedItem()).getName() + " has been added.\n");
					refresh();
					dispose();
				}		
			});
			//Possibly implement enter shortcut
		}
	}
	
	/*
	 *  Nested class that builds the GUI for the removeNode button
	 *  and sets up listeners for removeNode GUI layout
	 */
	private class RemoveNode extends JFrame
	{
		private JComboBox<Node> node;
		private JButton removeButton;
		private JButton cancelButton;
		
		public RemoveNode()
		{
			node = new JComboBox<Node>(sim.getNodes().toArray(new Node[sim.getNodes().size()]));
			removeButton = new JButton("Remove");
			cancelButton = new JButton("Cancel");
			
			this.setTitle("Remove Connection");
			JPanel top = new JPanel();
			top.add(node);
			JPanel bottom = new JPanel();
			bottom.add(removeButton);
			bottom.add(cancelButton);
			JSplitPane split = new JSplitPane();
			split.setOrientation(JSplitPane.VERTICAL_SPLIT);
			split.setTopComponent(top);
			split.setBottomComponent(bottom);
			split.setDividerSize(1);
			this.setSize(200, 100);
			this.setResizable(false);
			split.setDividerLocation((int)(this.getHeight()*0.35));
			this.add(split);
			this.setLocationRelativeTo(null);
			setUpListeners();
			this.setVisible(true);
		}
		
		public void setUpListeners()
		{
			cancelButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					dispose();
				}
			});
			removeButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					sim.removeNode((Node)node.getSelectedItem());
					statusWindow.append("Node " + ((Node)node.getSelectedItem()).getName() + " has been removed.\n");
					refresh();
					dispose();
				}		
			});
		}
	}
	
	/*
	 *  Nested class that builds the GUI for the removeConnection button
	 *  and sets up listeners for removeConnection GUI layout
	 */
	private class RemoveConnection extends JFrame
	{
		private JComboBox<Connection> connection;
		private JButton removeButton;
		private JButton cancelButton;
		
		public RemoveConnection()
		{
			connection = new JComboBox<Connection>(sim.getConnections().toArray(new Connection[sim.getConnections().size()]));
			removeButton = new JButton("Remove");
			cancelButton = new JButton("Cancel");
			
			this.setTitle("Remove Connection");
			JPanel top = new JPanel();
			top.add(connection);
			JPanel bottom = new JPanel();
			bottom.add(removeButton);
			bottom.add(cancelButton);
			JSplitPane split = new JSplitPane();
			split.setOrientation(JSplitPane.VERTICAL_SPLIT);
			split.setTopComponent(top);
			split.setBottomComponent(bottom);
			split.setDividerSize(1);
			this.setSize(200, 100);
			this.setResizable(false);
			split.setDividerLocation((int)(this.getHeight()*0.35));
			this.add(split);
			this.setLocationRelativeTo(null);
			setUpListeners();
			this.setVisible(true);
		}
		
		public void setUpListeners()
		{
			cancelButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					dispose();
				}
			});
			removeButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					sim.removeConnection((Connection)connection.getSelectedItem());
					statusWindow.append("Connection " + ((Connection)connection.getSelectedItem()).toString() + " has been removed\n.");
					dispose();
				}		
			});
		}
	}
	
	/*
	 *  Nested class that builds the GUI for the viewNode button
	 *  and sets up listeners for viewNode GUI layout
	 */
	private class ViewNode extends JFrame
	{
		private JComboBox<Node> node;
		private JButton viewButton;
		private JButton cancelButton;
		
		public ViewNode()
		{
			node = new JComboBox<Node>(sim.getNodes().toArray(new Node[sim.getNodes().size()]));
			viewButton = new JButton("View");
			cancelButton = new JButton("Cancel");
			
			this.setTitle("View Node");
			JPanel top = new JPanel();
			top.add(node);
			JPanel bottom = new JPanel();
			bottom.add(viewButton);
			bottom.add(cancelButton);
			JSplitPane split = new JSplitPane();
			split.setOrientation(JSplitPane.VERTICAL_SPLIT);
			split.setTopComponent(top);
			split.setBottomComponent(bottom);
			split.setDividerSize(1);
			this.setSize(200, 100);
			this.setResizable(false);
			split.setDividerLocation((int)(this.getHeight()*0.35));
			this.add(split);
			this.setLocationRelativeTo(null);
			setUpListeners();
			this.setVisible(true);
		}
		
		public void setUpListeners()
		{
			cancelButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					dispose();
				}
			});
			viewButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					statusWindow.append(((Node)node.getSelectedItem()).getDetails() + "\n");
					dispose();
				}		
			});
			//Possibly implement enter shortcut
		}
	}
	
	/*
	 *  Builds a new GUI 
	 * 
	 */
	public static void main(String args[])
	{
		new GUI();
	}
}
