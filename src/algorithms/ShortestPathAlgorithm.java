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
		for(Message msg : sim.getListMessages()){
			if(!addedMessages.contains(msg)){
				//Add the first node to the queue
				adjNode.add(msg.getSrc());
				addedMessages.add(msg);
			}
			//Dequeue and change reference
			Node refNode = adjNode.remove();
			refNode.addMessagesVisited(msg.getId());
			//For each connecting node
			for(Node connection : refNode.getConnections()){
				if(!connection.getMessagesVisited().contains(msg.getId())){
					msg.incCount();
					//Check if the neighbouring node is the destination
					if(connection.equals(msg.getDest())){
						reachedDestination.add(msg);
						sim.getStatusWindow().append("Destination reached. \n");
						sim.getStatusWindow().append("Number of jumps: " + msg.getCount() + "\n");
						sim.getMessageJumps().add(msg.getCount());
						sim.getListMessages().remove(msg);
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
