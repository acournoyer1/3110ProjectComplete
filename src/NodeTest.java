import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 */

/**
 * @author Daman
 *
 */
public class NodeTest {

	Node test = null;
	Node test1 = null;
	Node test2 = null;
	HashSet<Node> testHashSet = null;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		test = new Node("testNode");
		test1 = new Node("testNode1");
		test2 = new Node("testNode2");
		
		testHashSet = new HashSet<Node>();
		
		testHashSet.add(test1);
		test.connect(test1);
	
		
		
	}


	/**
	 * Test method for {@link Node#getName()}.
	 */
	@Test
	public void testGetName() {
		assertEquals("The Node should be named: testNode","testNode", test.getName());
	}

	/**
	 * Test method for {@link Node#setName(java.lang.String)}.
	 */
	@Test
	public void testSetName() {
		test.setName("changedNode");
		assertEquals("The Node should be named: changedNode", "changedNode", test.getName());
	}

	/**
	 * Test method for {@link Node#getConnections()}.
	 */
	@Test
	public void testGetConnections() {
	
		
		assertEquals("test should be connected to test1", testHashSet,test.getConnections());		
	}

	/**
	 * Test method for {@link Node#connect(Node)}.
	 */
	@Test
	public void testConnect() {
		test.connect(test2);
		testHashSet.add(test2);
		assertEquals("test should be connected to test2", testHashSet,test.getConnections());
	}

	/**
	 * Test method for {@link Node#disconnect(Node)}.
	 */
	@Test
	public void testDisconnect() {
		test.disconnect(test1);
		testHashSet.remove(test1);
		
		assertEquals("test should not be connected to test1", testHashSet, test.getConnections());
	}


}
