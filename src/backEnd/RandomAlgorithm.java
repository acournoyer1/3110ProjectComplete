package backEnd;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class RandomAlgorithm implements SimulationAlgorithm{
	Simulation sim;
	
	public RandomAlgorithm(Simulation sim){
		this.sim = sim;
	}
	
	@Override
	public void step() {
		//Add messages that have finished to the list
		for(Message msg: sim.getListMessages())
		{
			if(msg.reachedDestination())
			{
				reachedDestination.add(msg);
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
		//Iterate through every message in the simulation
		for(Message msg : sim.getListMessages()){
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
			//Check if the value has reached its destination & print to console
			if(msg.reachedDestination())
			{
				s += ", and has reached its destination. \n";
			}
			//Else keep iterating with new lines
			else
			{
				s += ".\n";
			}
			//Print to window
			sim.getStatusWindow().append(s);
		}
		sim.update();
		if(sim.getListMessages().size()==0) sim.getStatusWindow().append("No activity this step.\n");
		
	}
}
