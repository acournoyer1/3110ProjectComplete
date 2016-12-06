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
 * @author Ryan and Adam
 *
 */

public class ShortestPathAlgorithm implements SimulationAlgorithm{
	private Simulation sim;
	private Queue<Node> adjNode;
	private ArrayList<Message> addedMessages;
	private ArrayList<Node> addedNodes;
	private Node refNode;
	
	public ShortestPathAlgorithm(Simulation sim){
		this.sim = sim;
		this.adjNode = new LinkedList<Node>();
		this.addedMessages = new ArrayList<Message>();
		this.addedNodes = new ArrayList<Node>();
	}
	
	public void removeMessage(Message m){
		
	}
	
	@Override
	public void step() {
		//Remove the list of messages that have completed
		sim.getMessageList().removeAll(reachedDestination);
		reachedDestination.clear();
		addedNodes.clear();

		//Loop through each message
		for(Message msg : sim.getListMessages()){

			//Check if the first node has already been added to the queue
			if(!addedMessages.contains(msg)){
				//Add the first node to the queue
				adjNode.add(msg.getSrc());
				addedMessages.add(msg);
				refNode = adjNode.remove();
			}else{
				refNode = msg.getPath().getLast();
			}
			//Add adjacent connections to the queue
			for (Node connection : refNode.getConnections()){
				if(!connection.getMessagesVisited().contains(msg.getId())){
					adjNode.add(connection);
					sim.getStatusWindow().append("Adding Node " + connection.getName() + " to queue.\n");
					addedNodes.add(connection);
					connection.addMessagesVisited(msg.getId());
				}
			}
			
			//Clear the path of the message
			//msg.getPath().clear();
			//Dequeue
			refNode = adjNode.remove();
			//Increment number of jumps
			msg.incCount();
			//Put the changed node on the path of the message
			msg.appendPath(refNode);
			
			//Check if this node is the destination node
			if(refNode.equals(msg.getDest())){
				msg.stop();
				sim.update();
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
		}
		sim.update();
	}
	
	public void undo(){
		sim.getStatusWindow().append("Undo last step. \n");
		for (Message msg: sim.getListMessages()){
			LinkedList<Node> path = msg.getPath();
			Node current = path.getLast();
			if(path.size() > 1){
				path.getLast().removeMessagesVisited(msg.getId());
				for(Node n: addedNodes){
					path.remove(n);
				}
				path.removeLast();
				Node back = path.getLast();
				sim.getStatusWindow().append("Message " + msg.getId() + ": " + current.getName() + " -> " + back.getName()+"\n");
				sim.update();
				msg.decCount();
			}
		}
	}
	
}
