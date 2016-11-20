package backEnd;

import java.util.ArrayList;
import java.util.HashSet;

public class FloodAlgorithm implements SimulationAlgorithm{
	Simulation sim;
	
	public FloodAlgorithm(Simulation sim){
		this.sim = sim;
	}
	
	@Override
	public void step() {
		//Temporary list containing the children messages
		ArrayList<Message> childMsgs = new ArrayList<Message>();
		ArrayList<Integer> idList = new ArrayList<Integer>();
		ArrayList<Message> removeList = new ArrayList<Message>();
		ArrayList<Message> messagesUnvisited = new ArrayList<Message>();
		//Get the ID of the parent & child message and store it if it is complete
		for(Message msg: sim.getListMessages())
		{
			if(msg.reachedDestination())
			{
				idList.add(msg.getId());
			}
		}
		idList.clear();
		removeList.clear();	
		
		//Loop to send messages to adjacent nodes
		for(Message msg : sim.getListMessages()){
			msg.incCount();
			Node refNode = msg.getPath().getFirst();
			//Check all adjacent nodes
			for(Node n : refNode.getConnections()){
				//Check if the adjacent node has already been visited by a child message
				if(!n.getMessagesVisited().contains(msg.getId())){
					//Create a message with the same parentID, the same destination, and the parent count 
					Message ChildMsg = new Message(msg.getId(), msg.getDest(), n, msg.getCount());
					//Append the created child to a temporary list to add to the list of messages
					childMsgs.add(ChildMsg);
					//Notify the node that it has been visited
					n.addMessagesVisited(ChildMsg.getId());
					//Put the current node in the message path
					ChildMsg.appendPath(n);
					String s = "Message " + ChildMsg.getId() + " sending child message to: " + n.getName();
					//If a child message has reached its destination, add it to the list
					if(ChildMsg.reachedDestination()){
						s += " , and has reached its destination.\n";
						reachedDestination.add(ChildMsg);
					}
					else{
						s+= ".\n";
					}
					sim.getStatusWindow().append(s);
				}
			}
			msg.incUnvisitedCount();
			if(refNode.getConnections().size() == msg.getUnvisitedCount()){
				messagesUnvisited.add(msg);
			}
		}
		for(Message m : messagesUnvisited){
			sim.getMessageList().remove(m);
		}
		//Remove the messages from the list
		for(Message msg: removeList)
		{
			sim.getListMessages().remove(msg);
		}
		//Add all the child messages to the message list
		for(Message newMessages : childMsgs){
			sim.getListMessages().add(newMessages);
		}
		ArrayList<Message> indexList = new ArrayList<Message>();
		for(Message msg: reachedDestination)
		{	
			//Save the message to a list if it shares the same id as a completed message
			for(int i = 0; i < sim.getListMessages().size(); i++){
				if(sim.getListMessages().get(i).getId() == msg.getId())
					indexList.add(sim.getListMessages().get(i));
			}
			//Add the jump count to the messageList
			sim.getMessageJumps().add(msg.getCount());
		}
		//Add ID to list of completed messages
		for(Message msg: sim.getListMessages())
		{
			if(msg.reachedDestination())
			{
				idList.add(msg.getId());
			}
		}
		//If list contains the message, stop the message from spreading
		for(Message msg: sim.getListMessages())
		{
			if(idList.contains(msg.getId()))
			{
				msg.stop();
			}
		}
		
		sim.update();
		if(sim.getListMessages().size()==0) sim.getStatusWindow().append("No activity this step.\n");
	}

}
