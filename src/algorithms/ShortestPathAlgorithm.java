package algorithms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import backEnd.*;

/**
 * SHORTEST PATH ALGORITHM
 * 
 * Defined through breadth first search.
 * The shortest path algorithm will utilize
 * a queue nodes to find all neighbouring nodes
 * before continuing down the tree. 
 * 
 * @author Ryan
 *
 */

public class ShortestPathAlgorithm implements SimulationAlgorithm{
	private Simulation sim;
	private Queue<Node> adjNode;
	private ArrayList<Message> addedMessages;
	
	public ShortestPathAlgorithm(Simulation sim){
		this.sim = sim;
		this.adjNode = new LinkedList<Node>();
		this.addedMessages = new ArrayList<Message>();
	}
	
	@Override
	public void step() {
		//Remove the list of messages that have completed
		sim.getMessageList().removeAll(reachedDestination);
		reachedDestination.clear();
		
		//Loop through each message
		for(Message msg : sim.getListMessages()){
			//Check if the first node has already been added to the queue
			if(!addedMessages.contains(msg)){
				//Add the first node to the queue
				adjNode.add(msg.getSrc());
				addedMessages.add(msg);
			}
			//Clear the message's path to properly update the GUI
			msg.getPath().clear();
			//Dequeue and change reference
			Node refNode = adjNode.remove();
			refNode.addMessagesVisited(msg.getId());
			//For each connecting node
			for(Node connection : refNode.getConnections()){
				//Check if the connecting node has already been visited
				if(!connection.getMessagesVisited().contains(msg.getId())){
					//Add the node the message path
					msg.appendPath(connection);
					//Immediately update the node on the GUI
					sim.update();
					//Increment the number of jumps by 1
					msg.incCount();
					//Check if the neighbouring node is the destination
					if(connection.equals(msg.getDest())){
						msg.stop();
						//Add the message to the list to be removed
						reachedDestination.add(msg);
						sim.getStatusWindow().append("Destination reached. \n");
						sim.getStatusWindow().append("Number of jumps: " + msg.getCount() + "\n");
						sim.getMessageJumps().add(msg.getCount());
						//Clear the lists containing data
						addedMessages.remove(msg);
						adjNode.clear();				
						return;
					}
					//Else: Add the connection to the back of the queue
					else{
						adjNode.add(connection);
						connection.addMessagesVisited(msg.getId());
						sim.getStatusWindow().append("Adding " + connection.getName() + " to the queue.\n");
					}
				}
			}
		}
		sim.update();
	}
}
