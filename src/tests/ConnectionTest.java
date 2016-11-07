package tests;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import backEnd.Connection;
import backEnd.Node;

/**
 * 
 */

/**
 * @author Daman
 *
 */
public class ConnectionTest {

	private Connection testConnect;
	private Connection testConnect1;
	private Connection testConnect2;
	private Node srcNode; 
	private Node destNode;
	private Node testNode;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		testNode = new Node("test");
		srcNode = new Node("Source");
		destNode = new Node("Destination");
		testConnect = new Connection(srcNode, destNode);
		testConnect1 = new Connection(srcNode, destNode);
		testConnect2 = new Connection(srcNode , testNode);
	}

	
	/**
	 * Test method for {@link Connection#getFirstNode()}.
	 */
	@Test
	public void testGetFirstNode() {
		assertEquals("The first node is Source", srcNode, testConnect.getFirstNode());
	}

	/**
	 * Test method for {@link Connection#getSecondNode()}.
	 */
	@Test
	public void testGetSecondNode() {
		assertEquals("The second node is Destination", destNode, testConnect.getSecondNode());
	}


	/**
	 * Test method for {@link Connection#contains(Node)}.
	 */
	@Test
	public void testContains() {
		assertTrue("The connection contains source",testConnect.contains(srcNode));
		assertFalse("The connection does not contain testNode ", testConnect.contains(testNode));
	}

	/**
	 * Test method for {@link Connection#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		assertTrue("TestConnect has the same connections as TestConnect1", testConnect.equals(testConnect1));
		assertFalse("TestConnect does not have the same connections as TestConnect2", testConnect.equals(testConnect2));
	}

}
