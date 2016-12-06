
package algorithms;

import java.util.*;

import backEnd.*;

/**
 * DIJKSTRA'S AlGORITHM
 * 
 * finds the shortest way through a weighted graph
 * As our graph isn't technically weighted, we can 
 * imagine the weights to be equivalent to each nodes
 * index in the main node list held in simulation
 * Using this the 'quickest' route through the network
 * can be found
 * 
 * @author Adam Staples
 *
 */

public class DijkstrasAlgorithm implements SimulationAlgorithm{

	private Simulation sim;
	private ArrayList<Node> unvisitedNodes;
	private ArrayList<Node> unchangedNodes;
	private ArrayList<Message> addedMessages;
	private Node nextNode;
	private Node currentNode;
	
	public DijkstrasAlgorithm(Simulation sim){
		this.sim = sim;
		unvisitedNodes = new ArrayList<Node>();
		unchangedNodes = new ArrayList<Node>();
		addedMessages = new ArrayList<Message>();
	}
	
	
	/*
	 * Step method to go through a the table using Dijkstra's algorithm
	 *
	 */
	@Override
	public void step() {

		sim.getMessageList().removeAll(reachedDestination);
		reachedDestination.clear();
		addedMessages = sim.getListMessages();//receive list of Messages
		unchangedNodes.clear();	
		for(Node n: sim.getNodes()){
			unchangedNodes.add(n);
		}

		for (Message msg: addedMessages){
			
			nextNode = null;													//Node representing the node we will jump to
			currentNode = msg.getPath().getLast();								//the current/starting node
			
			unvisitedNodes.clear();												//reset list of Nodes
			for(Node n: unchangedNodes){										//clone total list of nodes
				if(!msg.getPath().contains(n)){
					unvisitedNodes.add(n);
				}
			}
				
			
			int currentWeight= 0;												//weight of the current Node
			int tempWeight = 0;													//variable used to compare later in method
			int count = 0;
			for (Node n: currentNode.getConnections()){							//iterate over each connection to node
				if(unvisitedNodes.contains(n)){									//ensure comparing against a not visited node
					tempWeight = unchangedNodes.indexOf(n);						//get 'weight' of possible node
					if(count == 0 || (tempWeight < currentWeight)){				//if it is the first comparison OR if the jump is 'quicker'
						nextNode = n;											//if condition met this is the next Node to go to
						currentWeight = unchangedNodes.indexOf(n);
					}
				}
				count++;
			 }

			
			if (nextNode == null){												//if the nextNode is null then there is no node to jump to there.
				currentNode = unvisitedNodes.get(0);							//THEREFORE pick a new start point to jump to, in our case the 
																				//'quickest' node still unvisited
			}else{
				currentNode = nextNode;											//everything went as planned set the nextNode
			}
					
			msg.appendPath(currentNode);										//append to path so can be set as visited on next run through
			sim.getStatusWindow().append("Message " + msg.getId() + " sending child message to: " + currentNode.getName() + "\n");
			msg.incCount();														//inc the message counter
			sim.update();
			
			if(currentNode.equals(msg.getDest())){								//If has reached its destination, add it to the list and print message
				reachedDestination.add(msg);
				sim.getStatusWindow().append("Destination reached. \n");
			}
		}	
		//Remove the messages from the list when it is complete
		//Also total the jump counts and print to window
		for(Message msg: reachedDestination)
		{
			sim.getListMessages().remove(msg);
			sim.getMessageJumps().add(msg.getCount());
			String s = "The average amount of jumps so far is: " + sim.average() + "\n";
			sim.getStatusWindow().append(s);
		}
		//Draw a break line in the status window
		if(reachedDestination.size()>0)
		{
			sim.getStatusWindow().append("----------------------------\n");
		}
	}
	
	public void undo(){
		sim.getStatusWindow().append("Undo last step. \n");
		for (Message msg: sim.getListMessages()){
			LinkedList<Node> path = msg.getPath();
			Node current = path.getLast();
			if(path.size() > 1){
				path.removeLast();
				Node back = path.getLast();
				sim.getStatusWindow().append("Message " + msg.getId() + ": " + current.getName() + " -> " + back.getName()+"\n");
				sim.update();
				msg.decCount();
			}
		}
	}
		
	
}
