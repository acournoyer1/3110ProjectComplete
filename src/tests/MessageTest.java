package tests;
import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import backEnd.Message;
import backEnd.Node;

/**
 * 
 */

/**
 * @author Daman
 *
 */
public class MessageTest {

	private Message testMsg;
	private Message testMsg1;
	private Message testMsg2;
	
	private Node srcNode;
	private Node destNode;
	private Node testNode;
	
	private LinkedList<Node> testList;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		srcNode = new Node("Source");
		destNode = new Node("Destination");
		testNode = new Node("Test");
		testMsg = new Message(srcNode, destNode);
		testList = new LinkedList<Node>();
		
		testList.add(srcNode);
	}

	
	/**
	 * Test method for {@link Message#appendPath(Node)}.
	 */
	@Test
	public void testAppendPath() {
		testMsg.appendPath(testNode);
		testList.add(testNode);
		assertEquals("The next node in the path is testNode", testList, testMsg.getPath());;
	}

	/**
	 * Test method for {@link Message#getId()}.
	 */
	@Test
	public void testGetId() {
		
		assertEquals("TestMsg Id is 2", 2, testMsg.getId());
	}

	/**
	 * Test method for {@link Message#reset()}.
	 */
	@Test
	public void testReset() {
		Message.reset();
		Message newMsg = new Message(srcNode, destNode);
		assertEquals("newMsg Id is 1", 1, newMsg.getId());
		
	}

	/**
	 * Test method for {@link Message#getPath()}.
	 */
	@Test
	public void testGetPath() {
		testMsg.appendPath(testNode);
		testList.add(testNode);
		assertEquals("The nodepath has 2 Nodes", testList, testMsg.getPath());
	}

	/**
	 * Test method for {@link Message#getSrc()}.
	 */
	@Test
	public void testGetSrc() {
		assertEquals("The srcNode is Source",srcNode, testMsg.getSrc());
	}

	/**
	 * Test method for {@link Message#getDest()}.
	 */
	@Test
	public void testGetDest() {
		assertEquals("The destNode is Destination",destNode, testMsg.getDest());
	}

	/**
	 * Test method for {@link Message#getCount()}.
	 */
	@Test
	public void testGetCount() {
		assertEquals("Count is 0", 0, testMsg.getCount());
		
	}

	/**
	 * Test method for {@link Message#incCount()}.
	 */
	@Test
	public void testIncCount() {
		testMsg.incCount();
		assertEquals("Count is 1", 1, testMsg.getCount());
		
	}

}
