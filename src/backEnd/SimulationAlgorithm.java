package backEnd;

import java.util.ArrayList;

public interface SimulationAlgorithm {
	ArrayList<Message> reachedDestination = new ArrayList<Message>();
	
	public void step();
}
