package tests;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import backEnd.Message;
import backEnd.Node;
import backEnd.Simulation;


/**
 * @author Daman
 *
 */
public class SimulationTest {

	private Message testMsg;
	
	private Node srcNode;
	private Node destNode;
	private Node node;
	
	private Simulation testSim;
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		node = new Node("test");
		srcNode = new Node("Source");
		destNode = new Node("Destination");
		
		testSim = new Simulation(null);
		testMsg = new Message(srcNode, destNode);
		
		
		testSim.addNode(node);
		testSim.addConnection(srcNode, destNode);
		testSim.addMsg(testMsg);
	}

	
	/**
	 * Test method for {@link Simulation#addMsg(Message)}.
	 */
	@Test
	public void testAddMsg() {
		
		
		assertEquals("adding testMsg to the list of messages", 1, testSim.getMessageListSize());
	}

	/**
	 * Test method for {@link Simulation#clear()}.
	 */
	@Test
	public void testClear() {
		testSim.clear();
		assertEquals("listNodes is cleared", 0, testSim.getNodes().size());
		assertEquals("listMessages is cleared", 0, testSim.getMessageListSize());
		assertEquals("messageJumps are 0", 0, testSim.getMessageJumpSize());
		assertEquals("There are no connections", 0 , testSim.getConnections().size());
		
	}

	/**
	 * Test method for {@link Simulation#getMessageListSize()}.
	 */
	@Test
	public void testGetMessageListSize() {
		assertEquals("MessageList size is 1", 1 , testSim.getMessageListSize());
	}

	/**
	 * Test method for {@link Simulation#getMessageJumpSize()}.
	 */
	@Test
	public void testGetMessageJumpSize() {
		assertEquals("MessageJump size is 0", 0 , testSim.getMessageJumpSize());
	}

	/**
	 * Test method for {@link Simulation#getNodes()}.
	 */
	@Test
	public void testGetNodes() {
		assertEquals("GetNodes size is 1", 1 , testSim.getNodes().size());
	}

	/**
	 * Test method for {@link Simulation#addNode(Node)}.
	 */
	@Test
	public void testAddNode() {
		testSim.addNode(srcNode);
		assertEquals("There should be 2 nodes", 2, testSim.getNodes().size());
	}

	/**
	 * Test method for {@link Simulation#removeNode(Node)}.
	 */
	@Test
	public void testRemoveNode() {
		
		testSim.removeNode(node);
		assertEquals("THere should be 0 nodes", 0 , testSim.getNodes().size());
	}

	/**
	 * Test method for {@link Simulation#getNodeByName(java.lang.String)}.
	 */
	@Test
	public void testGetNodeByName() {
		assertEquals("there should be a node called test", node, testSim.getNodeByName("test"));
		assertEquals("There is no node called source", null, testSim.getNodeByName("Source"));
	}

	/**
	 * Test method for {@link Simulation#addConnection(Node, Node)}.
	 */
	@Test
	public void testAddConnection() {
		
		assertEquals("There is a connection between srcNode and node", 1,testSim.getConnections().size());
	}

	/**
	 * Test method for {@link Simulation#removeConnection(Node, Node)}.
	 */
	@Test
	public void testRemoveConnectionNodeNode() {
		testSim.removeConnection(srcNode, destNode);
		assertEquals("There is no connection between source and destination nodes", 0, testSim.getConnections().size());
	}

	/**
	 * Test method for {@link Simulation#getConnections()}.
	 */
	@Test
	public void testGetConnections() {
		assertEquals("There should be 1 connection",1, testSim.getConnections().size());
	}

	/**
	 * Test method for {@link Simulation#removeConnection(Connection)}.
	 */
	@Test
	public void testRemoveConnectionConnection() {
		testSim.removeConnection(testSim.getConnections().get(0));
		assertEquals("remove the connection", 0 , testSim.getConnections().size());
	}

	
	
	

}
