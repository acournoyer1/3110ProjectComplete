package backEnd;



/**
 * Simulation class designated to manage the step process of walking through the simulation.
 * Manages the ability to manipulate node connections.
 *
 * Created by: Ryan Ha
 * Last Edited by: Adam Staples, Ryan Ha
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JTextArea;
import javax.swing.Timer;

import algorithms.*;

import java.util.Random;

public class Simulation {
	private ArrayList<Node> listNodes;
	private ArrayList<Integer> messageJumps;
	private JTextArea statusWindow;
	private ArrayList<Message> listMessages;
	private ArrayList<Connection> connections;
	private ArrayList<SimulationListener> listeners;
	private SimulationAlgorithm simStep;
	
	private boolean ignoreUpdate = false;
	private boolean randomMessages = true;
	private int simulationLength = 30;
	private int simulationRate = 3;
	
	/**
	 * Constructor:
	 * Instantiates an instace of simulation that builds
	 * the GUI
	 *
	 * @param1 GUI status window. The JTextArea of the GUI
	 */
	public Simulation(JTextArea statusWindow){
		
		this.listNodes = new ArrayList<Node>();
		this.messageJumps = new ArrayList<Integer>();
		this.listMessages = new ArrayList<Message>();
		this.connections = new ArrayList<Connection>();
		this.listeners = new ArrayList<SimulationListener>();
		this.statusWindow = statusWindow;
		
		//Default Type is initialized at random
		simStep = new RandomAlgorithm(this);
	}
	
	/**
	 * getStatusWindow method:
	 * Method to get reference to the status window of the current simulation
	 * 
	 * @return a JTextArea object of the status window
	 */
	public JTextArea getStatusWindow(){
		return this.statusWindow;
	}
	
	/**
	 * getListMessages method:
	 * Returns the list of nodes in the current simulation
	 * 
	 * @return an arraylist containing all of the messages
	 */
	public ArrayList<Message> getListMessages(){
		return this.listMessages;
	}
	
	/**
	 * getMessageJumps method:
	 * returns a list of all the finished message jumps
	 * 
	 * @return a list containing the finished message jumps
	 */
	public ArrayList<Integer> getMessageJumps(){
		return this.messageJumps;
	}
	
	/**
	 * addListener method:
	 * Adds a listener to the listener list
	 * 
	 * @param1 SimulationListener object to add to the list
	 */
	public void addListener(SimulationListener s)
	{
		listeners.add(s);
	}
	
	/**
	 * update method:
	 * Notifies all listeners to update in the GUI
	 */
	public void update()
	{
		if(!ignoreUpdate)
		{
			for(SimulationListener s: listeners)
			{
				s.update();
			}
		}
	}
	
	/**
	 * setIgnore method:
	 * Changes the boolean value of the "ignore" state variable
	 * 
	 * @param1 TRUE/FALSE value desired to ignore updates
	 */
	public void setIgnore(boolean ignore)
	{
		ignoreUpdate = ignore;
	}
	
	/**
	 * getRandomMessage method:
	 * returns the value of the bollean value of RandomMessages
	 *
	 * @return the TRUE/FALSE value (boolean) of random messages
	 */
	public boolean getRandomMessage()
	{
		return this.randomMessages;
	}
	
	/**
	 * setRandomMessage method:
	 * sets the value of the "RandomMessage" variable
	 * also updates the listeners
	 *
	 * @param1 the boolean value to set the Random Message variable
	 */
	public void setRandomMessage(boolean b)
	{
		this.randomMessages = b;
		update();
	}
	
	/**
	 * setRate method:
	 * sets the rate at which the simulation generates messages
	 *
	 * @param1 the value of the rate to set the variable to
	 */
	public void setRate(int rate)
	{
		this.simulationRate = rate;
	}
	
	/**
	 * setLength method:
	 * Sets the length of the simulation duration during run
	 *
	 * @param1 the integer value to set the length to
	 */
	public void setLength(int length)
	{
		this.simulationLength = length;
	}
	
	/**
	 * setType method:
	 * Sets the type of simulation to be run
	 * 
	 * @param1 the SimulationType value to set the type of simulation to
	 */
	public void setType(SimulationAlgorithm algorithm)
	{
		this.simStep = algorithm;
	}
	
	/**
	 * Adds a message object that is created by the user to give
	 * reference for the simulation.
	 * 
	 * @param1 the message object that the user creates
	 */
	public void addMsg(Message msg){
		listMessages.add(msg);
		update();
	}
	
	/**
	 * Resets the simulation to default values. Empties the list of nodes,
	 * connections, and messages.
	 */
	public void clear()
	{
		listNodes.clear();
		messageJumps.clear();
		listMessages.clear();
		connections.clear();
		Message.reset();
		update();
	}
	
	/**
	 * Get the size of the message list.
	 * 
	 * @return an integer representing the number of entries in the messages list
	 */
	public int getMessageListSize()
	{
		return listMessages.size();
	}
	
	public ArrayList<Message> getMessageList()
	{
		return listMessages;
	}
	
	/**
	 * Gets the number of completed message entries in the list.
	 *
	 * @return an integer containing the size of the message jump list
	 */
	public int getMessageJumpSize()
	{
		return messageJumps.size();
	}
	
	/**
	 * Get the list of nodes that are in the simulation currently.
	 *
	 * @return the array list containing the list of nodes.
	 */
	public ArrayList<Node> getNodes()
	{
		return listNodes;
	}
	
	/**
	 * Add a node to the simulation.
	 * Adds a node to the list of nodes in the current simulation
	 * and updates the GUI
	 * 
	 * @param1 the node object to add to the simulation
	 */
	public void addNode(Node n)
	{
		listNodes.add(n);
		update();
	}
	
	/**
	 * Remove a node from the simulation and all of its associated
	 * connections
	 * 
	 * @param1 the node object to be removed
	 */
	public void removeNode(Node n)
	{
		listNodes.remove(n);
		for(Node node: listNodes)
		{
			node.disconnect(n);
		}
		LinkedList<Connection> toRemove = new LinkedList<Connection>();
		for(Connection c: connections)
		{
			if(c.contains(n))toRemove.add(c);
		}
		connections.removeAll(toRemove);
		update();
	}
	
	/**
	 * Get a node object based off of the name property.
	 *
	 * @param1 the name of the node that is being requested
	 * @return the node object that has the name specified by param1
	 */
	public Node getNodeByName(String s)
	{
		for(Node n: listNodes)
		{
			if(n.getName().equals(s)) return n;
		}
		return null;
	}
	
	/**
	 * Build a connection between two nodes
	 *
	 * @param1 the first node to add a connection to
	 * @param2 the second node to add a connection to
	 */
	public void addConnection(Node n1, Node n2)
	{
		if(n1.equals(n2)) return;
		n1.connect(n2);
		connections.add(new Connection(n1, n2));
		update();
	}
	
	/**
	 * Remove the connection between two nodes
	 * 
	 * @param1 the first node to remove the connection from
	 * @param2 the second node to remove the connection from
	 * @return a boolean variable that describes if the connection is successfully removed
	 */
	public boolean removeConnection(Node n1, Node n2)
	{
		Connection toRemove = null;
		boolean removed = false;
		for(Connection c: connections)
		{
			if(c.contains(n1) && c.contains(n2))
			{
				toRemove = c;
				removed = true;
			}
		}
		if(!(toRemove == null))
		{
			connections.remove(toRemove);
			n1.disconnect(n2);
		}
		update();
		return removed;
	}
	
	/**
	 * Return the list of connections
	 * 
	 * @return the ArrayList object containing all connections
	 */
	public ArrayList<Connection> getConnections()
	{
		return connections;
	}
	
	/**
	 * Remove a connection from the simulation. Also removes the connection from the two ndoes.
	 *
	 * @param1 the connection object to be removed
	 */
	public void removeConnection(Connection c)
	{
		connections.remove(c);
		c.getFirstNode().disconnect(c.getSecondNode());
		update();
	}
	
	/**
	 * Iterates over a simulation process depending on the type of
	 * simulation selected
	 */
	public void step(){
		this.simStep.step();
	}
	/**
	 * Run a created network, creating messages at a determined rate for a determined length of steps.
	 * Type of simulation determines the method of sending messages.
	 */
	public void run() throws InterruptedException{
		Random toFrom = new Random();
		statusWindow.append("\nSimulation Started\n");
		if(randomMessages)//if randomMessages will be created when running
		{
			messageJumps.clear();//clear all previously create messages and message 
			listMessages.clear();
			Message.reset();
		}
		//Create a timer to perform the run method on a delay
		Timer t = new Timer(500, new ActionListener()
		{
			int i = 0;
			public void actionPerformed(ActionEvent e)
			{
				//If the randomMessages boolean is set
				if(randomMessages)
				{
					int toIndex= 0;
					int fromIndex = 0;
					if(i < simulationLength)
					{
						statusWindow.append("----------------------------\n");
						if((i%simulationRate) == 0){//a message is created just before step 1 and will continue depending on the given rate
							toIndex = toFrom.nextInt(listNodes.size());
							while(toIndex == fromIndex){//ensures the node does not send a message to itself
								fromIndex = toFrom.nextInt(listNodes.size());
							}
							statusWindow.append("Message  " + ((i/simulationRate)+1) + ": " + listNodes.get(fromIndex) + " -> " + listNodes.get(toIndex) + " has been added.\n");
							Message msg = new Message(listNodes.get(fromIndex),listNodes.get(toIndex));
							
							listMessages.add(msg);
						}
						step();
						i++;
					}
					//If there are messages in the list
					else if(listMessages.size() != 0){
						statusWindow.append("----------------------------\n");
						step();
					}
					//If the message list is empty stop the timer
					else
					{
						statusWindow.append("----------------------------\n");
						((Timer)e.getSource()).stop();
					}
				}
				//If the random messages settings is false 
				else
				{
					//if the list is not empty
					if(listMessages.size() != 0){
						statusWindow.append("----------------------------\n");
						step();
					}
					//If the list is empty stop the timer
					else
					{
						statusWindow.append("----------------------------\n");
						((Timer)e.getSource()).stop();
					}
				}
				
				
			}
		});
		t.start();

	}
	
	/**
	 * Sums the list of number of jumps messages went through
	 * Finds the average number of jumps done between all messages
	 * 
	 * @return integer containing average
	 */
	public int average(){
		int tempSum = 0;
		//Calculate total number of jumps
		for(int numJumps : this.messageJumps){
			tempSum += numJumps;
		}
		//Return average (number of jumps/size of arraylist)
		return tempSum/this.messageJumps.size();
	}
}