package userInterface;
import javax.swing.JTextArea;

import backEnd.Message;
import backEnd.Node;
import backEnd.Simulation;

public class CommandParser {
	
	private JTextArea statusWindow;
	private DialogManager dialog;
	private Simulation sim;
	
	public CommandParser(JTextArea statusWindow, DialogManager dialog, Simulation sim)
	{
		this.statusWindow = statusWindow;
		this.dialog = dialog;
		this.sim = sim;
	}
	
	public void parse(String s)
	{
		String[] words = s.split(" ");
		if (words.length == 0){}
		else if(words.length == 1)
		{
			if(words[0].equalsIgnoreCase("help"))
			{
				statusWindow.append("The commands in this program are:\n"
						+ "\tHELP\n"
						+ "\t\tShows all available commands.\n"
						+ "\tADD NODE\n"
						+ "\t\tOpens the window to add nodes.\n"
						+ "\tADD NODE name\n"
						+ "\t\tAdds a new node with the provided name.\n"
						+ "\tADD CONNECTION\n"
						+ "\t\tOpens the window to add connections.\n"
						+ "\tADD CONNECTION node1 node2\n"
						+ "\t\tAdds a connection between the two named nodes.\n"
						+ "\tADD MESSAGE\n"
						+ "\t\tOpens the window to add messages.\n"
						+ "\tADD MESSAGE source destination\n"
						+ "\t\tCreates a new message that will travel between the source and destination nodes.\n"
						+ "\tREMOVE NODE\n"
						+ "\t\tOpens the window to remove nodes.\n"
						+ "\tREMOVE NODE node\n"
						+ "\t\tRemoves the named node and all its connections\n"
						+ "\tREMOVE CONNECTION\n"
						+ "\t\tOpens the window to remove connections.\n"
						+ "\tREMOVE CONNECTION node1 node2\n"
						+ "\t\tRemoves the connection between the two named nodes.\n"
						+ "\tVIEW NODE\n"
						+ "\t\tDisplays all information about a node.\n"
						+ "\tVIEW ALL\n"
						+ "\t\tDisplay information about all nodes.\n"
						+ "\tCLEAR\n"
						+ "\t\tClears the simulation."
						+ "\tAVERAGE\n"
						+ "\t\tDisplays the average number of jumps a message had to take before reaching it's destination.\n"
						+ "*** All commands are not case sensitive ***\n");
			}
			else if(words[0].equalsIgnoreCase("average"))
			{
				statusWindow.append("The average number of jumps messages have taken is " + sim.average() + ".\n");
			}
			else if(words[0].equalsIgnoreCase("test"))
			{
				sim.buildTest();
				statusWindow.append("The test network has been built.\n");
			}
			else if(words[0].equalsIgnoreCase("clear"))
			{
				sim.clear();
				statusWindow.append("Simulation cleared.\n");
			}
			else if(words[0].equals("")){}
			else
			{
				statusWindow.append("Invalid comand, for all commands type 'help'.\n");
			}
		}
		else if(words[0].equalsIgnoreCase("add"))
		{
			if(words[1].equalsIgnoreCase("node"))
			{
				if(words.length == 2)
				{
					dialog.open("Add Node");
				}
				else if(words.length != 3)
				{
					statusWindow.append("The add node command must be followed by either no words or 1 word, the name of the node.\n");
				}
				else
				{
					Node n = sim.getNodeByName(words[2]);
					if(n == null)
					{
						sim.addNode(new Node(words[2]));
						statusWindow.append("Node " + words[2] + " has been added.\n");
					}
					else
					{
						statusWindow.append("A node with this name already exists.\n");
					}
				}
			}
			else if(words[1].equalsIgnoreCase("connection"))
			{
				if(words.length == 2)
				{
					dialog.open("Add Connection");
				}
				else if(words.length != 4)
				{
					statusWindow.append("The add connection command must be followed by either no words or 2 words, the names of the nodes the connection connects.\n");
				}
				else
				{
					Node n1 = sim.getNodeByName(words[2]);
					Node n2 = sim.getNodeByName(words[3]);
					if(n1 == null || n2 == null)
					{
						if(n1 == null) statusWindow.append("The first node named does not exist.\n");
						if(n2 == null) statusWindow.append("The second node named does not exist.\n");
					}
					else
					{
						sim.addConnection(n1, n2);
						statusWindow.append("Connection " + words[2] + " < - > " + words[3] + " has been added.\n");
					}
				}
			}
			else if(words[1].equalsIgnoreCase("message"))
			{
				if(words.length == 2)
				{
					dialog.open("Add Message");
				}
				else if(words.length != 4)
				{
					statusWindow.append("The add message command must be followed by either no words or 2 words, the names of the source and destination nodes.\n");
				}
				else
				{
					Node n1 = sim.getNodeByName(words[2]);
					Node n2 = sim.getNodeByName(words[3]);
					if(n1 == null || n2 == null)
					{
						if(n1 == null) statusWindow.append("The first node named does not exist.\n");
						if(n2 == null) statusWindow.append("The second node named does not exist.\n");
					}
					else
					{
						Message msg = new Message(n1, n2);
						statusWindow.append("Message " + msg.getId() + " : " + words[2] + " - > " + words[3] + " has been added.\n");
						sim.addMsg(msg);
					}
				}
			}
			else
			{
				statusWindow.append("Invalid comand, for all commands type 'help'.\n");
			}
		}
		else if(words[0].equalsIgnoreCase("remove"))
		{
			if(words[1].equalsIgnoreCase("node"))
			{
				if(words.length == 2)
				{
					dialog.open("Remove Node");
				}
				else if(words.length != 3)
				{
					statusWindow.append("The remove node command must be followed by either no words or 1 word, the name of the node.\n");
				}
				else
				{
					Node n = sim.getNodeByName(words[2]);
					if(n == null) statusWindow.append("The node named does not exist.\n");
					else
					{
						sim.removeNode(n);
						statusWindow.append("Node " + words[2] + " has been removed.\n");
					}
				}
			}
			else if(words[1].equalsIgnoreCase("connection"))
			{
				if(words.length == 2)
				{
					dialog.open("Remove Connection");
				}
				else if(words.length != 4)
				{
					statusWindow.append("The add connection command must be followed by either no words or 2 words, the names of the nodes the connection connects.\n");
				}
				else
				{
					Node n1 = sim.getNodeByName(words[2]);
					Node n2 = sim.getNodeByName(words[3]);
					if(n1 == null || n2 == null)
					{
						if(n1 == null) statusWindow.append("The first node named does not exist.\n");
						if(n2 == null) statusWindow.append("The second node named does not exist.\n");
					}
					else
					{
						if(sim.removeConnection(n1, n2))
						{
							statusWindow.append("Connection " + words[2] + " < - > " + words[3] + " has been removed.\n");
						}
						else
						{
							statusWindow.append("The specified connection does not exist.\n");
						}
					}
				}
			}
			else
			{
				statusWindow.append("Invalid comand, for all commands type 'help'.\n");
			}
		}
		else if(words[0].equalsIgnoreCase("view"))
		{
			if(words[1].equalsIgnoreCase("node"))
			{
				if(words.length == 2)
				{
					dialog.open("View Node");
				}
				else if(words.length != 3)
				{
					statusWindow.append("The view node command must be followed by either no words or 1 word, the name of the node.\n");
				}
				else
				{
					Node n = sim.getNodeByName(words[2]);
					if(n == null) statusWindow.append("The node named does not exist.\n");
					else
					{
						statusWindow.append(n.getDetails() + "\n");
					}
				}
			}
			else if(words[1].equalsIgnoreCase("all"))
			{
				if(words.length != 2)
				{
					statusWindow.append("The view all command should not be followed by more parameters.\n");
				}
				else
				{
					for(Node n: sim.getNodes())
					{
						statusWindow.append(n.getDetails() + "\n");
					}
				}
			}
		}
	}
}
