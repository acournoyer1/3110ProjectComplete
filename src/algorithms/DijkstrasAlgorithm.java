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
		this.unvisitedNodes = new ArrayList<Node>();
		this.unchangedNodes = new ArrayList<Node>();
		unchangedNodes = sim.getNodes();
		for(Node n: sim.getNodes()){
			unchangedNodes.add(n);
		}
		this.addedMessages = new ArrayList<Message>();
	}
	
	
	/*
	 * Step method to go through a the table using Dijkstra's algorithm
	 *
	 */
	@Override
	public void step() {
		addedMessages = sim.getListMessages();//receive list of Messages
		
		for (Message msg: addedMessages){
			
			int currentWeight=0;												//weight of the current Node
			int tempWeight = 0;													//variable used to compare later in method
			nextNode = null;													//Node representing the node we ill jump to
			currentNode = msg.getPath().getLast();								//the current/starting node
			
			unvisitedNodes.clear();												//reset list of Nodes
			for(Node n: unchangedNodes){										//clone total list of nodes
				unvisitedNodes.add(n);
			}
			for(Node visited: msg.getPath()){									//remove all nodes that have been visited by msg
				unvisitedNodes.remove(visited);
			}
			
			for (Node n: currentNode.getConnections()){							//iterate over each connection to node
				if(unvisitedNodes.contains(n)){									//ensure comparing against a not visited node
					tempWeight = (unchangedNodes.indexOf(n) +1 );				//get 'weight' of possible node
					if((tempWeight < currentWeight) || (currentWeight == 0)){	//if it is the first comparison OR if the jump is 'quicker'
						nextNode = n;											//if condition met this is the next Node to go to
					}
				}
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
			
			if(currentNode.equals(msg.getDest())){								//If has reached its destination, add it to the list and print message
				reachedDestination.add(msg);
				sim.getStatusWindow().append("Destination reached. \n");
				sim.getStatusWindow().append("Number of jumps: " + msg.getCount() + "\n");
				sim.getMessageJumps().add(msg.getCount());
				sim.getListMessages().remove(msg);
				return;
			}
		}	
	}
}
