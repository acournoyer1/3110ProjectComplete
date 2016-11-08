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
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JTextArea;
import javax.swing.Timer;

import java.util.Random;

public class Simulation {
	private ArrayList<Node> listNodes;
	private ArrayList<Integer> messageJumps;
	private JTextArea statusWindow;
	private SimulationType type;
	private ArrayList<Message> listMessages;
	private ArrayList<Connection> connections;
	private ArrayList<SimulationListener> listeners;
	
	private boolean ignoreUpdate = false;
	private boolean randomMessages = true;
	private int simulationLength = 30;
	private int simulationRate = 3;
	
	
	public Simulation(JTextArea statusWindow){
		
		this.listNodes = new ArrayList<Node>();
		this.messageJumps = new ArrayList<Integer>();
		this.listMessages = new ArrayList<Message>();
		this.connections = new ArrayList<Connection>();
		this.listeners = new ArrayList<SimulationListener>();
		this.statusWindow = statusWindow;
		
		//Temporary setup: Type is initialized at random
		this.type = SimulationType.RANDOM;
	}
	
	public void addListener(SimulationListener s)
	{
		listeners.add(s);
	}
	
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
	
	public void setIgnore(boolean ignore)
	{
		ignoreUpdate = ignore;
	}
	
	public boolean getRandomMessage()
	{
		return this.randomMessages;
	}
	
	public void setRandomMessage(boolean b)
	{
		this.randomMessages = b;
		update();
	}
	
	public void setRate(int rate)
	{
		this.simulationRate = rate;
	}
	
	public void setLength(int length)
	{
		this.simulationLength = length;
	}
	
	public void setType(SimulationType type)
	{
		this.type = type;
	}
	
	/*
	 * Adds a message object that is created by the user to give
	 * reference for the simulation.
	 * 
	 * @param1 the message object that the user creates
	 */
	public void addMsg(Message msg){
		listMessages.add(msg);
		update();
	}
	
	/*
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
	
	/*
	 * Get the size of the message list.
	 * 
	 * @ret an integer representing the number of entries in the messages list
	 */
	public int getMessageListSize()
	{
		return listMessages.size();
	}
	
	public ArrayList<Message> getMessageList()
	{
		return listMessages;
	}
	
	/*
	 * Gets the number of completed message entries in the list.
	 *
	 * @ret an integer containing the size of the message jump list
	 */
	public int getMessageJumpSize()
	{
		return messageJumps.size();
	}
	
	/*
	 * Get the list of nodes that are in the simulation currently.
	 *
	 * @ret the array list containing the list of nodes.
	 */
	public ArrayList<Node> getNodes()
	{
		return listNodes;
	}
	
	/*
	 * Add a node to the simulation.
	 */
	public void addNode(Node n)
	{
		listNodes.add(n);
		update();
	}
	
	/*
	 * Remove a node from the simulation.
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
	
	/*
	 * Get a node object based off of the name property.
	 *
	 * @param1 the name of the node that is being requested
	 * @ret the node object that has the name specified by param1
	 */
	public Node getNodeByName(String s)
	{
		for(Node n: listNodes)
		{
			if(n.getName().equals(s)) return n;
		}
		return null;
	}
	
	/*
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
	
	/*
	 * Remove the connection between two nodes
	 * 
	 * @param1 the first node to remove the connection from
	 * @param2 the second node to remove the connection from
	 * @ret a boolean variable that describes if the connection is successfully removed
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
	
	/*
	 * Return the list of connections
	 * 
	 * @ret the ArrayList object containing all connections
	 */
	public ArrayList<Connection> getConnections()
	{
		return connections;
	}
	
	/*
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
	
	/*
	 * Iterates over a simulation process depending on the type of
	 * simulation selected
	 */
	public void step(){
		ArrayList<Message> reachedDestination = new ArrayList<Message>();
		switch(this.type){
		//User selected RANDOM step type.
		case RANDOM:
			for(Message msg: listMessages)
			{
				if(msg.reachedDestination())
				{
					reachedDestination.add(msg);
				}
			}
			for(Message msg: reachedDestination)
			{
				listMessages.remove(msg);
				messageJumps.add(msg.getCount());
				String s = "The average amount of jumps so far is: " + this.average() + "\n";
				statusWindow.append(s);
			}
			if(reachedDestination.size()>0)
			{
				statusWindow.append("----------------------------\n");
			}
			for(Message msg : this.listMessages){
				Random nextNode = new Random();
				Node refNode = msg.getPath().getLast();
				HashSet<Node> refPath = refNode.getConnections();

				//Generate a randomly selected node from the hash set
				int index = nextNode.nextInt(refPath.size());
				Iterator<Node> iter = refPath.iterator();
				for (int i = 0; i < index; i++) {
					iter.next();
				}
				//Set the current node reference to a random node that the node is connected to
				Node currentNode = iter.next();
				msg.appendPath(currentNode);
				msg.incCount();

				String s = "Message " + msg.getId() + " has moved to " + currentNode.getName();

				if(msg.reachedDestination())
				{
					s += ", and has reached its destination. \n";
				}
				else
				{
					s += ".\n";
				}
				statusWindow.append(s);
			}
			update();
			if(listMessages.size()==0) statusWindow.append("No activity this step.\n");
			break;

		//User selected FLOOD step type.
		case FLOOD:
			//Temporary list containing the children messages
			ArrayList<Message> tempList = new ArrayList<Message>();
			ArrayList<Integer> idList = new ArrayList<Integer>();
			ArrayList<Message> removeList = new ArrayList<Message>();
			for(Message msg: listMessages)
			{
				if(msg.reachedDestination())
				{
					idList.add(msg.getId());
				}
			}
			for(Message msg: listMessages)
			{
				if(idList.contains(msg.getId()))
				{
					removeList.add(msg);
				}
			}
			for(Message msg: removeList)
			{
				listMessages.remove(msg);
			}
			idList.clear();
			removeList.clear();
			
			for(Message msg : this.listMessages){
				msg.incCount();
				Node refNode = msg.getPath().getFirst();
				for(Node n : refNode.getConnections()){
					if(!n.getMessagesVisited().contains(msg.getId())){
						Message ChildMsg = new Message(msg.getId(), msg.getDest(), n, msg.getCount());
						tempList.add(ChildMsg);
						n.addMessagesVisited(ChildMsg.getId());
						ChildMsg.appendPath(n);
						String s = "Message " + ChildMsg.getId() + " sending child message to: " + n.getName();
						if(ChildMsg.reachedDestination()){
							s += " , and has reached its destination.\n";
							reachedDestination.add(ChildMsg);
						}
						else
							s+= ".\n";
						statusWindow.append(s);
					}
				}
				removeList.add(msg);
			}
			for(Message msg: removeList)
			{
				listMessages.remove(msg);
			}
			for(Message newMessages : tempList){
				listMessages.add(newMessages);
			}
			ArrayList<Message> indexList = new ArrayList<Message>();
			for(Message msg: reachedDestination)
			{	
				for(int i = 0; i < listMessages.size(); i++){
					if(listMessages.get(i).getId() == msg.getId())
						indexList.add(listMessages.get(i));
				}
				messageJumps.add(msg.getCount());
			}
			for(Message msg: listMessages)
			{
				if(msg.reachedDestination())
				{
					idList.add(msg.getId());
				}
			}
			for(Message msg: listMessages)
			{
				if(idList.contains(msg.getId()))
				{
					msg.stop();
				}
			}
			update();
			if(listMessages.size()==0) statusWindow.append("No activity this step.\n");
			break;

		default:
			System.out.println("No current type selected!");
			break;
		}
	}
	/*
	 * Run a created network, creating messages at a determined rate for a determined length of steps.
	 * Type of simulation determines the method of sending messages.
	 * Random or precreated messages will be used based on setting found in the GUI
	 *
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
		
		Timer t = new Timer(500, new ActionListener()//waits half a second between each step for easy visualization
		{
			int i = 0;
			public void actionPerformed(ActionEvent e)
			{
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
					else if(listMessages.size() != 0){//length of steps reached, finishing remaining messages
						statusWindow.append("----------------------------\n");
						step();
					}
					else//all messages reached destination
					{
						statusWindow.append("----------------------------\n");
						((Timer)e.getSource()).stop();
					}
				}
				else//Use pre created messages when running
				{
					if(listMessages.size() != 0){
						statusWindow.append("----------------------------\n");
						step();
					}
					else//all messages reached destination
					{
						statusWindow.append("----------------------------\n");
						((Timer)e.getSource()).stop();
					}
				}
				
				
			}
		});
		t.start();

	}
	
	/*
	 *  Builds a test simulation with pre-determined nodes and connections
	 * 
	 */
	public void buildTest()
	{
		this.setIgnore(true);
		this.clear();
		Node a = new Node("A");
		Node b = new Node("B");
		Node c = new Node("C");
		Node d = new Node("D");
		Node e = new Node("E");
		
		this.addNode(a);
		this.addNode(b);
		this.addNode(c);
		this.addNode(d);
		this.addNode(e);
		
		this.addConnection(a, b);
		this.addConnection(a, c);
		this.addConnection(a, e);
		this.addConnection(c, d);
		this.addConnection(d, b);
		this.addConnection(b, e);
		this.setIgnore(false);
		update();
	}
	
	/*
	 * Sums the list of number of jumps messages went through
	 * Finds the average number of jumps done between all messages
	 * 
	 * @ret integer containing average
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
