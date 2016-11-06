/**
 * Simulation class designated to manage the step process of walking through the simulation.
 * Manages the ability to manipulate node connections.
 *
 * Created by: Ryan Ha
 * Last Edited by: Adam Staples
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JTextArea;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Simulation {
	private ArrayList<Node> listNodes;
	private ArrayList<Integer> messageJumps;
	private JTextArea statusWindow;
	private SimulationType type;
	private ArrayList<Message> listMessages;
	private ArrayList<Connection> connections;
	
	public Simulation(JTextArea statusWindow){
		
		this.listNodes = new ArrayList<Node>();
		this.messageJumps = new ArrayList<Integer>();
		this.listMessages = new ArrayList<Message>();
		this.connections = new ArrayList<Connection>();
		this.statusWindow = statusWindow;
		
		//Temporary setup: Type is initialized at random
		this.type = SimulationType.RANDOM;
	}
	
	/*
	 * Adds a message object that is created by the user to give
	 * reference for the simulation.
	 * 
	 * @param1 the message object that the user creates
	 */
	public void addMsg(Message msg){
		listMessages.add(msg);
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
	}
	
	/*
	 * Iterates over a simulation process depending on the type of
	 * simulation selected
	 */
	public void step(){
		switch(this.type){
		//User selected RANDOM step type.
		case RANDOM:
			ArrayList<Message> reachedDestination = new ArrayList<Message>();
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

				if(msg.getDest().equals(currentNode))
				{
					reachedDestination.add(msg);
					s += ", and has reached its destination. \n";
				}
				else
				{
					s += ".\n";
				}
				statusWindow.append(s);
			}
			for(Message msg: reachedDestination)
			{
				listMessages.remove(msg);
				messageJumps.add(msg.getCount());
				String s = "The average amount of jumps so far is: " + this.average() + "\n";
				statusWindow.append(s);
			}
			break;

		//User selected FLOOD step type.
		case FLOOD:
			//Temporary list containing the children messages
			ArrayList<Message> tempList = new ArrayList<Message>();
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
						if(ChildMsg.getDest().equals(n)){
							s += " , and has reached its destination.\n";
							reachedDestination.add(ChildMsg);
						}
						else
							s+= ".\n";
						statusWindow.append(s);
					}
				}
			}
			for(Message newMessages : tempList){
				listMessages.add(newMessages);
			}
			for(Message msg: reachedDestination)
			{
				messageJumps.add(msg.getCount());
			}
			break;

		default:
			System.out.println("No current type selected!");
			break;
		}
	}
	/*
	 * Run a created network, creating messages at a determined rate for a determined length of steps.
	 * Type of simulation determines the method of sending messages.
	 *
	 * @param1 the rate at which messages will be created
	 * @param2 the number of total steps that will be taken before the method stops creating messages
	 */
	public void run(int rate, int length) throws InterruptedException{
		int toIndex= 0;
		int fromIndex = 0;
		messageJumps.clear();//clear all previously create messages and message 
		listMessages.clear();
		Random toFrom = new Random();
		
		for (int i =0; i<length;i++){
			
			if((i%rate) == 0){//a message is created just before step 1 and will continue depending on the given rate
				toIndex = toFrom.nextInt(listNodes.size());
				while(toIndex == fromIndex){//ensures the node does not send a message to itself
					fromIndex = toFrom.nextInt(listNodes.size());
				}
				statusWindow.append("Message  " + ((i/rate)+1) + ": " + listNodes.get(fromIndex) + " -> " + listNodes.get(toIndex) + " has been added.\n");
				Message msg = new Message(listNodes.get(fromIndex),listNodes.get(toIndex));
				listMessages.add(msg);
			}
			TimeUnit.MILLISECONDS.sleep(20);
			step();
		}
		//after final message is created continue stepping until all messages reach destination
		while(messageJumps.size() != (length/rate)){
			TimeUnit.MILLISECONDS.sleep(20);
			step();
		}
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
