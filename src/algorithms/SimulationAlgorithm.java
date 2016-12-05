package algorithms;

import backEnd.Message;

import java.util.ArrayList;

public interface SimulationAlgorithm {
	ArrayList<Message> reachedDestination = new ArrayList<Message>();
	
	/**
	 * Step method: 
	 * 
	 * Go through the table using a algorithm
	 *
	 */
	public void step();
}