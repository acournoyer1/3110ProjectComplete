package algorithms;

import backEnd.Message;

import java.util.ArrayList;

public interface SimulationAlgorithm {
	ArrayList<Message> reachedDestination = new ArrayList<Message>();
	
	public void step();
	public void undo();
}